package org.bagirov;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verifyNoInteractions;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockListTest {
    private List mockedList3 = mock(List.class); // Keep this for a mock with no interactions

    @Test
    public void testList() {
        List mockedList1 = mock(List.class);
        List mockedList2 = mock(List.class);

        mockedList1.add("Called first");
        mockedList2.add("Called second");

        InOrder inOrder = inOrder(mockedList1, mockedList2);

        inOrder.verify(mockedList1).add("Called first");
        inOrder.verify(mockedList2).add("Called second");
    }

    @Test
    public void testVerifyList() {
        List mockedList = mock(List.class);
        // Using mock object
        mockedList.add("One");
        mockedList.add("Two");
        mockedList.add("Two");

        // Verification
        verify(mockedList).add("One");
        verify(mockedList, times(2)).add("Two");
        verify(mockedList, never()).add("Three");
        verify(mockedList, atLeast(1)).add(anyString());
    }

    @Test
    public void testWhenReturnList() {
        List mockedList = mock(List.class);
        when(mockedList.get(anyInt())).thenReturn("Four");
        when(mockedList.get(5)).thenReturn("Five");
        doReturn("Six").when(mockedList).get(100);

        assertEquals("Four", mockedList.get(500));
        assertEquals("Five", mockedList.get(5));
        assertEquals("Six", mockedList.get(100));
    }

    @Test(expected = NullPointerException.class)
    public void testShouldThrowNullPointerException() {
        List mockedList = mock(List.class);
        doThrow(NullPointerException.class).when(mockedList).clear();
        mockedList.clear();
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldThrowIllegalStateException() {
        List mockedList = mock(List.class);
        when(mockedList.get(anyInt())).thenThrow(new IllegalStateException());
        mockedList.get(0);
    }

    @After
    public void after() {
        // Only checking mockedList3 as it's the only mock not used in tests
        verifyNoInteractions(mockedList3);
    }
}