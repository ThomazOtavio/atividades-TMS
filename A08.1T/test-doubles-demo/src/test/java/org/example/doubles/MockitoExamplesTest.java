package org.example.doubles;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class MockitoExamplesTest {

    @Test
    void verifyBehavior_simpleList() {
        List<String> mockedList = mock(List.class);
        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    void stubbingWithMatchers() {
        LinkedList<String> mocked = mock(LinkedList.class);
        when(mocked.get(anyInt())).thenReturn("element");

        assertEquals("element", mocked.get(999));
        verify(mocked).get(anyInt());
    }

    @Test
    void spiesOnRealObjects_doReturnForSafeStubbing() {
        LinkedList<String> real = new LinkedList<>();
        List<String> spyList = spy(real);

        doReturn(100).when(spyList).size(); // evita execução real do método ao stubbar

        spyList.add("one");
        assertEquals("one", spyList.get(0));
        assertEquals(100, spyList.size());

        verify(spyList).add("one");
    }
}
