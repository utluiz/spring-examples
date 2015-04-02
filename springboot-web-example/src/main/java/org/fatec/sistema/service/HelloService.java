package org.fatec.sistema.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

	public String generateMessage() {
		return "Olá, Spring Boot!";
	}
	
}
