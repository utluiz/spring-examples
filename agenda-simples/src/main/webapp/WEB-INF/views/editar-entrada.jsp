<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="template-header.jsp" >
    <jsp:param name="title" value="Editar Entrada :: Agenda Pessoal" />
    <jsp:param name="active" value="entrada" />
</jsp:include>

	<!-- CONTEÚDO -->
    <div class="container-fluid">
    
      <form class="form-horizontal" id="form-editar-entrada" action="<c:url value="/entradas" />" method="post">
		  <fieldset>
		    <legend>Entrada</legend>
		    <div class="form-group">
		      <label for="codigo" class="col-lg-2 control-label">Código</label>
		      <div class="col-lg-2">
		        <input type="text" class="form-control" name="codigo" id="codigo" value="1">
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="inputPassword" class="col-lg-2 control-label">Data</label>
		      <div class="col-lg-2">
		        <input type="date" class="form-control" name="data" id="data" placeholder="Data">
		      </div>
		      <div class="col-lg-2">
		        <input type="time" class="form-control" name="hora" id="hora" placeholder="Hora">
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="textArea" class="col-lg-2 control-label">Descrição</label>
		      <div class="col-lg-10">
		        <textarea class="form-control" rows="3" id="textArea"></textarea>
		        <span class="help-block">O que você vai ou precisa fazer?</span>
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="select" class="col-lg-2 control-label">Prioridade</label>
		      <div class="col-lg-2">
		        <select class="form-control" id="select">
		          <option>Passatempo</option>
		          <option>Nada de mais</option>
		          <option>Precisa Atenção</option>
		          <option>Importantíssimo</option>
		        </select>
		      </div>
		    </div>
		    <div class="form-group">
		      <div class="col-lg-10 col-lg-offset-2">
		        <button type="submit" class="btn btn-primary">Confirmar</button>
		        <a href="<c:url value="/entradas" />" class="btn btn-default">Voltar</a>
		      </div>
		    </div>
		  </fieldset>
		</form>
      
      <hr>

    </div> <!-- /container -->
    
<jsp:include page="template-footer.jsp" />
