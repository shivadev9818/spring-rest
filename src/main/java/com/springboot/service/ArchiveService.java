package com.springboot.service;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

import com.springboot.dao.IDocumentDao;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by SG******* on 10/9/2017.
 *
 */

@Service("archiveService")
public class ArchiveService implements IArchiveService, Serializable {

    private static final long serialVersionUID = 8119784722798361327L;
    
    @Autowired
    private IDocumentDao DocumentDao;

    /**
     * Saves a document in the archive.
     * @see IArchiveService#save(Document)
     */
    @Override
    public DocumentMetadata save(Document document) {
        getDocumentDao().insert(document); 
        return document.getMetadata();
    }
    
    /**
     * Finds document in the archive
     * @see IArchiveService#findDocuments(java.lang.String, java.util.Date)
     */
    @Override
    public List<DocumentMetadata> findDocuments(String personName, Date date) {
        return getDocumentDao().findByPersonNameDate(personName, date);
    }
    
    /**
     * Returns the document file from the archive
     * @see IArchiveService#getDocumentFile(java.lang.String)
     */
    @Override
    public byte[] getDocumentFile(String id) {
        Document document = getDocumentDao().load(id);
        if(document!=null) {
            return document.getFileData();
        } else {
            return null;
        }
    }


    public IDocumentDao getDocumentDao() {
        return DocumentDao;
    }

    public void setDocumentDao(IDocumentDao documentDao) {
        DocumentDao = documentDao;
    }


}
