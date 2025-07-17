package org.apache.maven.archetypes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class AppTest extends TestCase {
    //Test case for the App class
    public AppTest(String testName)

    {
        super(testName);
    }

    // Test suite method to group tests in this class
    public static Test suite()
    {
        return new TestSuite(AppTest.class);
    }

    // Test method to verify that the main method of App prints the expected environment variable
    public void testMainPrintsEnv() {
        String expectedEnv = "test-env";
        System.setProperty("env", expectedEnv);

        // Capture the output of the main method
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Call the main method of the App class
        App.main(new String[]{});
        System.setOut(originalOut);

        // Verify that the output contains the expected environment variable
        String output = outContent.toString().trim();
        assertTrue("Output should contain environment: " + expectedEnv, output.contains("Environment: "+expectedEnv));
    }
}
