package org.bagirov;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class MyIteratorTest {
    @Test
    public void shouldIterateThroughElementsCorrectly() {
        Person[] individuals = {
                new Person("Миша", 25),
                new Person("Коля", 30),
                new Person("Дима", 35)
        };

        MyIterator<Person> customIterator = new MyIterator<>(individuals);

        assertTrue(customIterator.hasNext());
        assertEquals("Миша", customIterator.next().getName());

        assertTrue(customIterator.hasNext());
        assertEquals("Коля", customIterator.next().getName());

        assertTrue(customIterator.hasNext());
        assertEquals("Дима", customIterator.next().getName());

        assertFalse(customIterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionWhenEmpty() {
        Person[] emptyArray = {};
        MyIterator<Person> emptyIterator = new MyIterator<>(emptyArray);

        emptyIterator.next();
    }

}
