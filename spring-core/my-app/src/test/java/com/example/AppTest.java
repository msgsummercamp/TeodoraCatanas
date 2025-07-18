package com.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest extends TestCase {
    public AppTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(AppTest.class);}

    public void testEnglishGreetingService() {
        GreetingService englishGreeting = () -> System.out.println("Hello!");
        HelloService helloService = new HelloService(englishGreeting);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        helloService.sayHello();

        System.setOut(originalOut);
        String output = outContent.toString().trim();
        assertEquals("Hello!", output);
    }

    public void testJapaneseGreetingService() {
        GreetingService japaneseGreeting = () -> System.out.println("Konnichiwa!");
        HelloService helloService = new HelloService(japaneseGreeting);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        helloService.sayHello();

        System.setOut(originalOut);
        String output = outContent.toString().trim();
        assertEquals("Konnichiwa!", output);
    }
}
