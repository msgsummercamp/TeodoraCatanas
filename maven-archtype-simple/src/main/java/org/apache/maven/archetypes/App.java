package org.apache.maven.archetypes;

public class App {
    // This is a simple Java application that prints the value of the "env" system property
    public static void main(String[] args) {
        System.out.println("Environment: "+System.getProperty("env"));
    }
}
