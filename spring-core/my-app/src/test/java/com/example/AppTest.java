package com.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest extends TestCase {
    // Test case for the App class
    public AppTest(String testName) {super(testName);}

    // This method is used to create a test suite
    public static Test suite() {return new TestSuite(AppTest.class);}

    // Test to verify that the main method of App prints the message in english
    public void testEnglishGreetingService() {
        GreetingService englishGreeting = () -> System.out.println("Hello!");
        HelloService helloService = new HelloService(englishGreeting);

        // Capture the output of the sayHello method
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        helloService.sayHello();

        // Restore the original System.out
        System.setOut(originalOut);
        String output = outContent.toString().trim();
        assertEquals("Hello!", output);
    }

    // Test to verify that the main method of App prints the message in japanese
    public void testJapaneseGreetingService() {
        GreetingService japaneseGreeting = () -> System.out.println("Konnichiwa!");
        HelloService helloService = new HelloService(japaneseGreeting);

        // Capture the output of the sayHello method
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        helloService.sayHello();

        // Restore the original System.out
        System.setOut(originalOut);
        String output = outContent.toString().trim();
        assertEquals("Konnichiwa!", output);
    }
}
