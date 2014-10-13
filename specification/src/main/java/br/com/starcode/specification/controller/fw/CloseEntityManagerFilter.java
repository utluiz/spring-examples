package br.com.autbank.abutils.agendavisitas.webapp.fw;


import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import br.com.autbank.abutils.agendavisitas.utils.JPA;

/**
 * Fecha os entity managers da thread ao final da requisição, se houver
 */
public class CloseEntityManagerFilter implements Filter {

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	try {
        
    		chain.doFilter(request, response);
    		
    	} finally {

    		//filtra urls com extensão 
    		String path = ((HttpServletRequest) request).getRequestURI();
    		String resource = path.substring(path.lastIndexOf('/'));
    		if (resource.indexOf(".") <= 0) {
    			
	    		EntityManager em = JPA.em(false);
	    		if (em != null && em.isOpen()) {
	    			
	    			em.close();
	    			
	    		}
	    		
    		}
        
    	}

    }

    public void init(FilterConfig filterConfig) throws ServletException {


    }

}
