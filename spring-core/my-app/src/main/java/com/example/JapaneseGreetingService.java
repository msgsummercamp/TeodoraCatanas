package com.example;

import org.springframework.stereotype.Service;

@Service("japaneseGreetingService")
public class JapaneseGreetingService implements GreetingService {
    public void greet(){
        System.out.println("Konnichiwa!");
    }
}
