package kr.wes.talbum;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TestMyTest1 {

    @BeforeClass
    public static void oneTimeSetUp() {
        System.out.println("@BeforeClass - oneTimeSetUp");
    }

    @AfterClass
    public static void oneTimeTearDown() {
        System.out.println("@AfterClass - oneTimeTearDown");
    }

    @Before
    public void setUp() {
        System.out.println("mytest1 setUp");
    }

    @Test
    public void testAddtion() {
        assertTrue(2 == 2);
    }

    @Test
    public void testAddtion2() {
        assertTrue(2 == 2);
    }

    @After
    public void tearDown() {
        System.out.println("mytest1 tearDown");
    }

}