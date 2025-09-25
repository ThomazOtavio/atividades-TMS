package org.example.doubles;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EasyMockDocumentServiceTest {

    @Test
    void happyPath_expectationSatisfied() {
        DocumentDAO dao = EasyMock.mock(DocumentDAO.class);
        DocumentService service = new DocumentService(dao);

        // record
        dao.addDocument("New Doc");
        EasyMock.expectLastCall().once();

        // replay
        EasyMock.replay(dao);

        // act
        service.addNew("New Doc");

        // verify
        EasyMock.verify(dao);
    }

    @Test
    void unexpectedCall_afterReplay_shouldFail() {
        DocumentDAO dao = EasyMock.mock(DocumentDAO.class);
        DocumentService service = new DocumentService(dao);

        // replay (sem expectativas registradas)
        EasyMock.replay(dao);

        // act + assert: chamada inesperada deve causar AssertionError
        AssertionError err = assertThrows(AssertionError.class, () -> service.remove("Ghost"));
        assertTrue(err.getMessage().contains("Unexpected method call"));
    }

    @Test
    void expectationNotMet_onVerify_shouldFail() {
        DocumentDAO dao = EasyMock.mock(DocumentDAO.class);
        DocumentService service = new DocumentService(dao);

        // record: expectativa que não será satisfeita
        dao.addDocument("WillNotBeCalled");
        EasyMock.expectLastCall().once();

        // replay
        EasyMock.replay(dao);

        // act: não chamamos service.addNew(...)

        // verify -> deve lançar AssertionError indicando expected != actual
        AssertionError err = assertThrows(AssertionError.class, () -> EasyMock.verify(dao));
        assertTrue(err.getMessage().contains("expected"));
    }
}
