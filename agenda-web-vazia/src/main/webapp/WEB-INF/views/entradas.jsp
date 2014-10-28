<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="template-header.jsp" >
    <jsp:param name="title" value="Entradas :: Agenda Pessoal" />
    <jsp:param name="active" value="entradas" />
</jsp:include>

	<!-- CONTEÚDO -->
    <div class="container-fluid">
    
      <div class="row-fluid">
      	<div class="col-lg-3 col-md-12">
      	
	      	<!-- Filtro de pesquisa -->
	        <form id="form-filtro" class="well" role="form">
		      <div class="row">
		        <div class="col-lg-12 col-md-3 col-sm-12">
		        	<div class="form-group">
				      <label class="control-label col-sm-2 col-md-2 col-lg-12">De</label>
				      <div class="col-sm-4 col-md-10 col-lg-12">
				        <input type="date" class="form-control input-sm" id="filtro-de" name="filtro-de">
 				      </div>
 				     </div>
				    </div>
		        <div class="col-lg-12 col-md-3 col-sm-12">
		        	<div class="form-group">
				      <label class="control-label col-sm-2 col-md-2 col-lg-12">Até</label>
				      <div class="col-sm-4 col-md-10 col-lg-12">
				      	<input type="date" class="form-control input-sm" id="filtro-ate" name="filtro-ate">
				      </div>
				    </div>
				</div>
		        <div class="col-lg-12 col-md-4 col-sm-12">
		        	<div class="form-group">
			 	        <label class="col-sm-2 col-md-3 col-lg-12">Descrição</label>
			 	        <div class="col-sm-10 col-md-9 col-lg-12">
					      <input type="text" class="form-control input-sm" id=filtro-descricao placeholder="Pesquisar descrição" name="filtro-descricao">
					    </div>
				    </div>
				</div>
		        <div class="col-lg-12 col-md-2 col-sm-12">
		        	<div class="form-group">
						<div class="btn-group btn-group-sm col-lg-12">
						  <button type="submit" id="filtrar" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
						  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
						    <span class="caret"></span>
						    <span class="sr-only">Opções</span>
						  </button>
						  <ul class="dropdown-menu" role="menu">
						    <li><a class="text-center" href="#">Limpar filtros</a></li>
						  </ul>
						</div>
					</div>
				</div>
		      </div>
			</form>
		</div>

		<div class="col-lg-9 col-md-12">	
	      	<h2>Entradas da agenda <a href="<c:url value="/entrada?new"/>" class="btn btn-success pull-right">Nova Entrada <span class="glyphicon glyphicon-plus"></span></a></h2>
	      	
	        <!-- <table class="table table-striped table-hover table-bordered table-condensed"> -->
	        <table class="table table-hover table-bordered ">
	          <thead>
	            <tr>
	              <th class="text-center coluna-codigo">#</th> <!-- Código -->
	              <th class="text-left coluna-data"><a href="#data">Data 
					  <span class="glyphicon glyphicon-sort-by-attributes"></span>
				  </a></th> 
	              <th class="text-left coluna-descricao"><a href="#desc">Descrição
					  <span class="glyphicon glyphicon-sort-by-attributes-alt"></span> 
	              </a></th>
			   	  <th class="text-right coluna-acao"></th>
	            </tr>
	          </thead>
	          <tbody>
	          	<c:forEach items="${entradas}" var="entrada">
	            <tr class="${entrada.prioridade.code == 'I' ? 'danger' : '' }${entrada.prioridade.code == 'A' ? 'warning' : '' }${entrada.prioridade.code == 'P' ? 'success' : '' }">
	              <td class="text-center">${entrada.id}</td>
	              <td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${entrada.horario}" /></td>
	              <td>${entrada.descricao}</td>
	              <td class="text-right">
			        <a href="<c:url value="/entrada/${entrada.id}"/>" title="Editar Entrada" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-pencil"></span></a>
	   				<a href="#" title="Excluir Entrada" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span></a>
	              </td>
	            </tr>
	            </c:forEach>
	          </tbody>
	        </table>
	     </div>    
	  </div>

    </div> <!-- /container -->
    
<jsp:include page="template-footer.jsp" />