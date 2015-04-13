package org.fatec.sistema.controller;

import java.util.List;

import org.fatec.sistema.service.DinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
	
	@Autowired DinoService dinoService;

    @RequestMapping("/")
    public ModelAndView page() {
    	ModelAndView mv = new ModelAndView("page");
    	mv.addObject("msg", dinoService.sayHello());
    	return mv;
    }
    
    @RequestMapping("/ver-familia")
    public ModelAndView other() {
    	List<String> familyList = dinoService.listFamily();
    	return new ModelAndView("listar-familia")
    		.addObject("family", familyList);
    }

}