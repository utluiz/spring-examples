<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Agenda pessoal é uma agenda online para organizar sua vida.">
	<meta name="keywords" content="agenda, organização pessoal, produtividade">
	<meta name="author" content="[Seu nome aqui]">
	
	<title>${param.title}</title>
	
	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/" />bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="<c:url value="/" />bootstrap/css/bootswatch.min.css" rel="stylesheet">
	<link href="<c:url value="/" />css/agenda.css" rel="stylesheet">
	<link rel="shortcut icon" type="image/png" href="<c:url value="/" />favicon.png"/>
	
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body>

	<!-- BARRA DE MENU E LOGIN -->
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Navegação</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<c:url value="/"/>">Agenda Pessoal</a>
        </div>
        <div class="navbar-collapse collapse">
          <!-- LOGIN -->
          <c:if test="${usuario == null}">
          <form id="login-form" class="navbar-form navbar-right" role="form" method="post" action="<c:url value="/login" />">
          	<c:if test="${not empty erroLogin}">
            <p class="navbar-text bg-danger error-message">${erroLogin}</p>
            </c:if>
            <div class="form-group">
              <input type="text" name="nome-usuario" placeholder="Nome de usuário" class="form-control">
            </div>
            <div class="form-group">
              <input type="password" name="senha" placeholder="Password" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">Entrar <span class="glyphicon glyphicon-log-in"></span></button>
          </form>
          </c:if>
          <!-- MENU -->
          <c:if test="${usuario != null}">
       	  <ul class="nav navbar-nav">
            <li class="${ param.active == 'entradas' ? 'active' : ''}"><a href="<c:url value="/entradas" />">Entradas</a></li>
            <li class="${ param.active == 'sobre' ? 'active' : ''}"><a href="<c:url value="/sobre" />">Sobre</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
          	<li><p class="navbar-text">Bom dia, ${usuario.nome}!</p></li>
            <li><a href="<c:url value="/logout" />">Sair <span class="glyphicon glyphicon-log-out"></span></a></li>
          </ul>
          </c:if>
        </div>
      </div>
    </div>

	
	<!-- MENSAGENS -->
    <div class="container-fluid">
    
      <c:if test="${not empty param.erro or not empty erro}">
	  <div class="alert alert-danger fade in text-center" role="alert">
	    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Fechar</span></button>
	    <strong>${param.erro}${erro}</strong>
	  </div>
      </c:if>
      <c:if test="${not empty param.msg or not empty msg}">
      <div class="alert alert-success fade in text-center" role="alert">
	    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Fechar</span></button>
	    <strong>${param.msg}${msg}</strong>
	  </div>
      </c:if>
      
    </div>
