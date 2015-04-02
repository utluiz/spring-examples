package org.fatec.sistema.controller;

import org.fatec.sistema.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
	
	@Autowired HelloService helloService;

    @RequestMapping("/")
    public String hello() {
    	return helloService.generateMessage();
    }

}