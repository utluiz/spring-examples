<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="template-header.jsp" >
    <jsp:param name="title" value="Agenda Pessoal" />
</jsp:include>

    <!-- JUMBOTRON: MENSAGEM MARCANTE -->
    <div class="jumbotron">
      <div class="container-fluid">
        <h1>Bem-vindo à sua Agenda!</h1>
        <p>Essa agenda vai revolucionar sua organização pessoal.</p>
        <p><a class="btn btn-primary btn-lg" role="button" href="<c:url value="/sobre"/>">Saiba mais &raquo;</a></p>
      </div>
    </div>

	<!-- VANTEGENS -->
    <div class="container-fluid">
      <!-- Example row of columns -->
      <div class="row">
        <div class="col-md-4">
          <h2>Fácil</h2>
          <p>Simples de usar. </p>
        </div>
        <div class="col-md-4">
          <h2>Moderna</h2>
          <p>Não precisa de caneta e papel. </p>
       </div>
        <div class="col-md-4">
          <h2>Acessível</h2>
          <p>Não esqueça mais sua agenda, basta ter acesso à internet de qualquer dispositivo.</p>
        </div>
      </div>

    </div> <!-- /container -->
    
<jsp:include page="template-footer.jsp" />
