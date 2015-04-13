package org.fatec.sistema.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DinoService {

	public String sayHello() {
		return "Querida, cheguei!";
	}
	
	public List<String> listFamily() {
		List<String> family = new ArrayList<String>();
		family.add("Fino");
		family.add("Fran");
		family.add("Baby");
		family.add("Bob");
		family.add("Charlene");
		return family;
	}
	
}
