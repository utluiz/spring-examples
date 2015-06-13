package org.fatec.sistema.controller;

import java.util.List;

import org.fatec.sistema.model.Entidade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FormController {

	@RequestMapping(value="/form", method=RequestMethod.GET)
    public ModelAndView other() {
    	return new ModelAndView("form");
    }
	
	@RequestMapping(value="/post-form", method=RequestMethod.POST)
	@ResponseBody
    public String page(
    		String[] itens, 
    		@RequestParam("itens") String[] itensNomeDiferente, 
    		@RequestParam("itens") List<String> itensLista,
    		Entidade entidade) {
		System.out.println("--- Array");
		for (String string : itens) {
			System.out.println(string);
		}
		System.out.println("--- Array outro nome");
		for (String string : itensNomeDiferente) {
			System.out.println(string);
		}
		System.out.println("--- Lista");
		for (String string : itensLista) {
			System.out.println(string);
		}
		
		System.out.println("--- Array Itens da Entidade");
		for (String string : entidade.getItens()) {
			System.out.println(string);
		}
    	return "Olá";
    }
	
	@RequestMapping(value="/post-form-entidade", method=RequestMethod.POST)
	@ResponseBody
    public String page2(Entidade entidade) {
		System.out.println("--- Array Itens da Entidade");
		for (String string : entidade.getItens()) {
			System.out.println(string);
		}
		System.out.println("--- Lista da Entidade");
		for (String string : entidade.getItensLista()) {
			System.out.println(string);
		}
		System.out.println("--- Array Itens da SubEntidade");
		for (String string : entidade.getOutraEntidade().getItens()) {
			System.out.println(string);
		}
		System.out.println("--- Lista da SubEntidade");
		for (String string : entidade.getOutraEntidade().getItensLista()) {
			System.out.println(string);
		}
    	return "Olá";
    }
}
