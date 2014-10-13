<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="author" content="Luiz Ricardo - Autbank">
        <meta name="description" content="Sistema de agendamento de visitas a clientes da Autbank">
        <meta http-equiv="X-UA-Compatible" content="IE=9">
    	
        <title>${titulo} :: Agenda de Visitas ao Cliente :: Autbank</title>
        
        <link rel="stylesheet" media="screen" href="${path}/bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" media="screen" href="${path}/css/smoothness/jquery-ui.min.css"/>
<#if tipoPagina == 2>
        <link rel="stylesheet" media="screen" href="${path}/css/jquery.fileupload.css"/>
        <link rel="stylesheet" media="screen" href="${path}/css/jquery.fileupload-ui.css"/>
</#if>
        <link rel="stylesheet" media="screen" href="${path}/css/main.css"/>
        <link rel="shortcut icon" type="image/png" href="${path}/img/favicon.png"/>
    </head>
    <body>
    
    	<div class="navbar navbar-fixed-top">
	   		<div class="navbar-inner">
		        <div class="container">
		            <a href="${path}/" class="brand">Agenda de Visitas ao Cliente</a>
		            <ul class="nav">
		            	<li class="<#if tipoPagina == 1>active </#if>mostra-popover" id="menu-entradas" data-placement="bottom" title="Entradas da Agenda" data-content="Exibe a lista de entradas da agenda de visitas."><a href="${path}/entradas/"><i class="icon-list-alt"></i> Entradas</a></li>
		            	<#if analista.login??>
		            	<li class="<#if tipoPagina == 2>active </#if>mostra-popover" id="menu-adicionar" data-placement="bottom" title="Nova Entrada" data-content="Permite adicionar uma nova entrada na agenda de visitas."><a href="${path}/entrada/incluir/"><i class="icon-plus"></i> Adicionar</a></li>
		            	</#if>
		            	<li class="divider-vertical"></li>
		            	<li class="<#if tipoPagina == 3>active </#if>mostra-popover" id="menu-sobre" data-placement="bottom" title="Sobre" data-content="Exibe informações diversas sobre o sistema."><a href="${path}/sobre/"><i class="icon-info-sign"></i> Sobre</a></li>
		            </ul>
		           	<#if analista.login??>
		    		<a id="botao-deslogar" class="btn btn-mini pull-right mostra-popover" href="${path}/login/sair/" data-placement="bottom" title="Deslogar" data-content="Clique para desfazer o login."><img src="${path}/img/exit.png"/> Sair</a>
		    		<p id="bem-vindo" class="pull-right">${cumprimento}, <span title="${analista.nome}" class="mostra-tooltip" data-placement="bottom"><strong>${analista.nomeSimplificado}</strong></span></p>
		    		<#else>
				    <form id="loginform" method="post" action="${path}/login/entrar/" class="form-inline navbar-form pull-right">
				    	<label for="login">Login</label>
				    	<input type="text" name="login" class="input-small"/>
				    	<label for="senha">Senha</label>
				    	<input type="password" name="senha" class="input-small"/>
				        <button type="submit" class="btn btn-small mostra-popover" data-placement="bottom" title="Autenticação" data-content="Digite os seus dados de acesso e clique em Entrar para efetuar o login."><img src="${path}/img/login.png"/> Entrar</button>
				    </form>
		    		</#if>
		        </div>
	    	</div>
	    </div>
	    
    	<div class="container">
    	
    		<!-- Mensagens -->
	    	<#if errorMessage??>
			<div class="alert alert-error">
			  <a class="close" data-dismiss="alert" href="#">×</a>
			  <h4 class="alert-heading">${(errorMessage.titulo == '')?string("Ops! Erro na Validação...", errorMessage.titulo)}</h4>
			  <#if errorMessage.descricao??>
			  <span style="white-space: pre-wrap;">${errorMessage.descricao}</span>
			  </#if>
			</div>
	    	</#if>
	    	<#if alertMessage??>
			<div class="alert alert-block">
			  <a class="close" data-dismiss="alert" href="#">×</a>
			  <h4 class="alert-heading">${alertMessage.titulo}</h4>
			  <#if alertMessage.descricao??>
			  <span style="white-space: pre-wrap;">${alertMessage.descricao}</span>
			  </#if>
			</div>
	    	</#if>
	    	<#if infoMessage??>
			<div class="alert alert-info">
			  <a class="close" data-dismiss="alert" href="#">×</a>
			  <h4 class="alert-heading">${(infoMessage.titulo == '')?string("Cuidado!", infoMessage.titulo)}</h4>
			  <#if infoMessage.descricao??>
			  <span style="white-space: pre-wrap;">${infoMessage.descricao}</span>
			  </#if>
			</div>
	    	</#if>
	    	<#if successMessage??>
			<div class="alert alert-success">
			  <a class="close" data-dismiss="alert" href="#">×</a>
			  <h4 class="alert-heading">${(successMessage.titulo == '')?string("Pronto!", successMessage.titulo)}</h4>
			  <#if successMessage.descricao??>
			  <span style="white-space: pre-wrap;">${successMessage.descricao}</span>
			  </#if>
			</div>
	    	</#if>
    	
	    	<div id="header" class="page-header">
	    		<h1>${titulo}</h1>
	    	</div>
		    
	    	<section id="conteudo">