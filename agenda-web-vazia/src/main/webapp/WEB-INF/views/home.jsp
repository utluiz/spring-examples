<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>

<p>Bem-vindo(a)!<p>

<!-- gerar um link no contexto da aplicação -->
<c:url value="/sobre" var="urlSobre" />

<!-- coloca o link armazenado na 
	 variável urlSobre -->
<a href="${urlSobre}">Sobre</a>

<form action="<c:url value="/cadastrar" />"
	  method="post">
	  
	Cadastre seu e-mail:
	<input type="text" name="mail" />
	<button>Cadastrar</button>
	
</form>

<c:if test="${sucesso == 1}">

	<p>E-mail <strong>${email}</strong> 
		cadastrado com sucesso!</p>
		
</c:if>




</body>
</html>






