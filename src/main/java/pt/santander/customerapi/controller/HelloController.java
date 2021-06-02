package pt.santander.customerapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String helloWorld(@RequestParam String name, @RequestParam String apelido){
        return String.format("Hello World!: %s %s", name, apelido);
    }
}
