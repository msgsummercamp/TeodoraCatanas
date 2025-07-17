package com.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
    // Test case for the App class
    public AppTest(String testName) {super(testName);}

    // This method is used to create a test suite
    public static Test suite() {return new TestSuite(AppTest.class);}

    public void testApp() {assertTrue(true);}
}
