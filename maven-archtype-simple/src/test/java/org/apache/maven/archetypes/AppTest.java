package org.apache.maven.archetypes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testMainPrintsEnv(){
        String expectedEnv = "test-env";
        System.setProperty( "env", expectedEnv );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut( new PrintStream( outContent ) );

        App.main( new String[]{} );
        System.setOut( originalOut );

        String output = outContent.toString().trim();
        assertTrue( "Output should contain environment: " + expectedEnv, output.contains( "Environment: " + expectedEnv ) );
    }
}
