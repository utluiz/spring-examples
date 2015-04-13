package br.com.starcode.agenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class IntegrantesDao {

	public List<String> listarIntegrantes() {
		List<String> integrantes =
				new ArrayList<String>();
		integrantes.add("João");
		integrantes.add("José");
		integrantes.add("Maria");
		integrantes.add("Fulano");
		return null;
	}
	
}
