package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Value("${welcome.message}")
    private final String welcomeMessage;

    // @Autowired is automatically (behind the scenes added)
    // Error: Could not autowire. No beans of 'String' type found.
    // This is a runtime error, although, IntelliJ detects it before runtime.
    public WelcomeController(String welcomeMessage)
    {
        this.welcomeMessage = welcomeMessage;
    }

    @GetMapping("/")
    public String sayHello() {
        return this.welcomeMessage;
    }
}
