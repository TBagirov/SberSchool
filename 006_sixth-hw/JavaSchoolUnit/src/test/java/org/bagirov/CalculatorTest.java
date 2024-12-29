package org.bagirov;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@Category({SlowTests.class, FastTests.class})
@RunWith(Parameterized.class)
public class CalculatorTest {

    @Parameter
    public int a;

    @Parameter(1)
    public int b;

    @Parameter(2)
    public int expected;

    private Calculator calculator = new Calculator();

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{{5, 2, 7}, {1, 1, 2}, {2, 1, 3}});
    }

    @Test
    @Ignore("Тест не dostoin")
    public void testAdd() throws Exception {
        assertEquals(expected, calculator.add(a, b));
    }

}