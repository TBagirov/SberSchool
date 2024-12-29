package org.bagirov;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpyListTest {

    @Spy
    private List<String> spyOnList = new ArrayList<>();

    @Spy
    private HashSet<String> spyOnSet = new HashSet<>();

    @Test
    public void testListVerify() {
        spyOnList.add("One");
        verify(spyOnList).add("One");
        verifyNoMoreInteractions(spyOnList);
    }

    @Test
    public void testListWhen() {
        when(spyOnList.contains("One")).thenReturn(false);

        spyOnList.add("One");

        assertEquals("One", spyOnList.get(0));
        assertFalse(spyOnList.contains("One"));
    }
}