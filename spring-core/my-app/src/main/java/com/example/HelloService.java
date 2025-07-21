package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    // Using @Qualifier to specify which bean to inject
    private final GreetingService greetingService;

    @Autowired
    public HelloService(@Qualifier("japaneseGreetingService") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void sayHello(){greetingService.greet();}
}
