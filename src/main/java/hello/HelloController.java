package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Value("${hello.greeting}")
    private String greeting;

    @Autowired
    private NumberFactsService numberFacts;

    @GetMapping
    public String sayHello() {
        return String.format("%s %s", greeting, numberFacts.randomNumberFact());
    }
}