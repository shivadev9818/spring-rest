package com.springboot.service;

import java.util.Date;
import java.util.List;

/**
 * Created by SG******* on 10/9/2017.
 *
 */

public interface IArchiveService {
    
    /**
     * Saves a document in the archive.
     * 
     * @param document A document
     * @return DocumentMetadata The meta data of the saved document
     */
    DocumentMetadata save(Document document);
    
    /**
     * Finds document in the archive matching the given parameter.
     * A list of document meta data which does not include the file data.
     * Use getDocumentFile and the id from the meta data to get the file.
     * Returns an empty list if no document was found.
     */
    List<DocumentMetadata> findDocuments(String personName, Date date);
    
    
    /**
     * Returns the document file from the archive with the given id.
     * Returns null if no document was found.
     */
    byte[] getDocumentFile(String id);
}
