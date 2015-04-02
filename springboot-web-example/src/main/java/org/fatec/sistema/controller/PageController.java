package org.fatec.sistema.controller;

import org.fatec.sistema.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
	
	@Autowired HelloService helloService;

    @RequestMapping("/page")
    public ModelAndView page() {
    	ModelAndView mv = new ModelAndView("page");
    	mv.addObject("msg", helloService.generateMessage());
    	return mv;
    }
    
    @RequestMapping("/other")
    public ModelAndView other() {
    	return new ModelAndView("page")
    		.addObject("msg", "other message!");
    }

}