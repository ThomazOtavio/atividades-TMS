package org.example.doubles;

// SUT simples para ser usado nos testes de EasyMock
public class DocumentService {
    private final DocumentDAO dao;

    public DocumentService(DocumentDAO dao) {
        this.dao = dao;
    }

    public void addNew(String title) {
        dao.addDocument(title);
    }

    public void remove(String title) {
        dao.removeDocument(title);
    }
}
