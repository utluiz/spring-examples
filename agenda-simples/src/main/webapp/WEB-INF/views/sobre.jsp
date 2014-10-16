<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="template-header.jsp" >
    <jsp:param name="title" value="Sobre :: Agenda Pessoal" />
    <jsp:param name="active" value="sobre" />
</jsp:include>

	<!-- CONTEÚDO -->
    <div class="container-fluid">
       	<!-- Main jumbotron for a primary marketing message or call to action -->
   		<h1>Ajuda</h1>
   		<p>Basta clicar nas coisas, a interface é intuitiva!</p>
        <hr>
   		<h1>Sobre</h1>
   		<p>[dados sobre quem fez a agenda]</p>
    </div> <!-- /container -->
    
<jsp:include page="template-footer.jsp" />