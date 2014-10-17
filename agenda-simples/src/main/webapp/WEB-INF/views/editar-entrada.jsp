<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="template-header.jsp" >
    <jsp:param name="title" value="Editar Entrada :: Agenda Pessoal" />
    <jsp:param name="active" value="entrada" />
</jsp:include>

	<!-- CONTEÚDO -->
    <div class="container-fluid">
    
      <form class="form-horizontal" id="form-editar-entrada" action="<c:url value="/entrada${ empty entrada.id ? '' : '/'.concat(entrada.id) }" />" method="post">
		  <fieldset>
		    <legend>Entrada</legend>
		    <c:if test="${not empty entrada.id}">
		    <div class="form-group">
		      <label for="codigo" class="col-lg-2 control-label">Código</label>
		      <div class="col-lg-2">
		        <input type="text" class="form-control disabled" name="codigo" id="codigo" value="${entrada.id}" readonly="readonly">
		      </div>
		    </div>
		    </c:if>
		    <div class="form-group">
		      <label for="inputPassword" class="col-lg-2 control-label">Data</label>
		      <div class="col-lg-2">
		        <input type="date" class="form-control" name="horario" id="data" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entrada.horario}" />" placeholder="Data">
		      </div>
		      <div class="col-lg-2">
		        <input type="time" class="form-control" name="hora" id="hora" value="<fmt:formatDate pattern="hh:mm" value="${entrada.horario}" />" placeholder="Hora">
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="textArea" class="col-lg-2 control-label">Descrição</label>
		      <div class="col-lg-10">
		        <textarea class="form-control" rows="3" name="descricao" id="descricao">${entrada.descricao}</textarea>
		        <span class="help-block">O que você vai ou precisa fazer?</span>
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="select" class="col-lg-2 control-label">Prioridade</label>
		      <div class="col-lg-2">
		        <select class="form-control" id="prioridade" name="prioridade">
		          <option value="Passatempo"${entrada.prioridade.code == 'P' ? ' selected' : '' }>Passatempo</option>
		          <option value="NadaDeMais"${entrada.prioridade.code == 'N' ? ' selected' : '' }>Nada de mais</option>
		          <option value="PrecisaAtencao"${entrada.prioridade.code == 'A' ? ' selected' : '' }>Precisa Atenção</option>
		          <option value="Importantissimo"${entrada.prioridade.code == 'I' ? ' selected' : '' }>Importantíssimo</option>
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
