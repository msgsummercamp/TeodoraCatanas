package com.example;


import org.springframework.stereotype.Component;

@Component("japaneseGreetingService")
public class JapaneseGreetingService implements GreetingService {
    public void greet(){
        System.out.println("Konnichiwa!");
    }
}
