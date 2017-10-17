package com.springboot.rest;

import org.apache.log4j.Logger;
import org.springframework.http.*;
import com.springboot.service.Document;
import org.springframework.web.bind.annotation.*;
import com.springboot.service.IArchiveService;
import com.springboot.service.DocumentMetadata;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.stereotype.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by SG******* on 10/9/2017.
 *
 */

@Controller
@RequestMapping(value = "/archive")
public class ControllerClass {

    private static final Logger LOG = Logger.getLogger(ControllerClass.class);

    @Autowired
    IArchiveService archiveService;

    /**
     * Adds a document to the archive.
     *
     * Url: /archive/upload?file={file}&person={person}&date={date} [POST]
     *
     * @param file A file posted in a multipart request
     * @param person The name of the uploading person
     * @param date The date of the document
     * @return The meta data of the added document
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    DocumentMetadata handleFileUpload(
            @RequestParam(value="file", required=true) MultipartFile file ,
            @RequestParam(value="person", required=true) String person,
            @RequestParam(value="date", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {

        try {
            Document document = new Document(file.getBytes(), file.getOriginalFilename(), date, person );
            getArchiveService().save(document);
            return document.getMetadata();
        } catch (RuntimeException e) {
            LOG.error("Error while uploading.", e);
            throw e;
        } catch (Exception e) {
            LOG.error("Error while uploading.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds document in the archive. Returns a list of document meta data
     * which does not include the file data. Use getDocument to get the file.
     * Returns an empty list if no document was found.
     *
     * Url: /archive/documents?person={person}&date={date} [GET]
     *
     * @param person The name of the uploading person
     * @param date The date of the document
     * @return A list of document meta data
     */
    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public HttpEntity<List<DocumentMetadata>> findDocument(
            @RequestParam(value="person", required=false) String person,
            @RequestParam(value="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<List<DocumentMetadata>>(getArchiveService().findDocuments(person,date), httpHeaders, HttpStatus.OK);
    }

    /**
     * Returns the document file from the archive with the given UUID.
     *
     * Url: /archive/document/{id} [GET]
     *
     * @param id The UUID of a document
     * @return The document file
     */
    @RequestMapping(value = "/document/{id}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getDocument(@PathVariable String id) {
        // send it back to the client
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(getArchiveService().getDocumentFile(id), httpHeaders, HttpStatus.OK);
    }

    public IArchiveService getArchiveService() {
        return archiveService;
    }

    public void setArchiveService(IArchiveService archiveService) {
        this.archiveService = archiveService;
    }

}
