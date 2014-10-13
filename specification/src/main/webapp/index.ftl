<#assign titulo='Entradas da Agenda'/>
<#assign tipoPagina=1/>

<#include 'header.ftl'/>

<#macro link ordenarPor=''>
${path}/entradas/?<#t>
ordenarPor=${ordenarPor?url}&<#t>
ordem=${(ordenarPor == "")?string(ordem.ordem, (ordenarPor != ordem.ordenarPor)?string(ordem.ordem, ordem.inverso()))?url}<#t>
</#macro>
<#macro ordemCSS ordenarPor>
<#if ordem.ordenarPor == ordenarPor> ${ordem.ordem} <#else> "" </#if><#t>
</#macro>

<div id="filtro">
	<form action="${path}/entradas/" method="GET" class="form-inline well ">
        <label for="de">De</label>
        <input type="text" id="de" name="de" size="10" value="${filtro.dataInicial}" class="input-small" style="width: 75px;">
        <label for="ate">At&eacute;</label>
        <input type="text" id="ate" name="ate" size="10" value="${filtro.dataFinal}" class="input-small" style="width: 75px;">
        <label for="analista">Analista</label>
        <select id="analista" name="analista" class="input-large">
        	<option value="">-- Todos --</option>
        	<#list analistas as analistaAtual>
        		<option value="${analistaAtual.login}"<#if filtro.analista?? && analistaAtual.login == filtro.analista> selected</#if>>${analistaAtual.nome}</option>
        	</#list>
        </select>
        <label for="cliente">Cliente</label>
        <select id="cliente" name="cliente" class="input-large">
        	<option value="">-- Todos --</option>
        	<#list clientes as clienteAtual>
        		<option value="${clienteAtual.codigo}"<#if filtro.cliente?? && clienteAtual.codigo == filtro.cliente> selected</#if>>${clienteAtual.nome}</option>
        	</#list>
        </select>
        <button type="submit" id="filtrar" class="btn mostra-popover" data-placement="bottom" title="Filtro de Entradas" data-content="Permite selecionar as entradas exibidas por período, analista e cliente. As opções são armazenadas nos cookies do navegador para os futuros acessos."><img src="${path}/img/filtro.png"/> Filtrar</button>
        <button type="submit" id="limparfiltro" name="limparfiltro" class="btn mostra-popover" data-placement="bottom" title="Limpar Filtro de Entradas" data-content="Remove todos os filtros atualmente selecionados."><img src="${path}/img/limpar_filtro.png"/></button>
    </form>
</div>

<table id="tabela_entradas" class="table table-bordered table-striped table-condensed">
   	<thead>
   		<tr>
	   		<th class="centro <@ordemCSS "id"/>"><a href="<@link "id"/>">Código</a></th>
	   		<th class="<@ordemCSS "data"/>"><a href="<@link "data"/>">Data</a></th>
	   		<th class="<@ordemCSS "analista"/>"><a href="<@link "analista"/>">Analista</a></th>
	   		<th class="<@ordemCSS "cliente"/>"><a href="<@link "cliente"/>">Cliente</a></th>
	   		<th class="<@ordemCSS "periodo"/>"><a href="<@link "periodo"/>">Per&iacute;odo</a></th>
	   		<th class="<@ordemCSS "assunto"/>"><a href="<@link "assunto"/>">Assunto</a></th>
	   		<th class="centro">Anexos</th>
	   		<#if analista.login??>
	   		<th class="centro">Ações</th>
	   		</#if>
   		<tr>
   	</thead>
   	<tbody>
   		<#list entradas as entrada>
   		<tr>
   			<td class="centro">${entrada.id}</td>
   			<td>${entrada.data?date}</td>
   			<td><span title="${entrada.analista.nome}" class="mostra-tooltip">${entrada.analista.nomeResumido}</span></td>
   			<td><span title="${entrada.cliente.nome}" class="mostra-tooltip">${entrada.cliente.nomeResumido}</span></td>
   			<td><div class="conteudo_grande_menor">${entrada.periodo}</div></td>
   			<td><div class="conteudo_grande">${entrada.assunto}</div></td>
   			<td>
   				<div class="centro">
   				<#if !entrada.anexos?has_content>
   				<span class="badge mostra-tooltip" title="A entrada não possui anexos.">0</span>
   				<#else>
   				<div class="btn-group centro" style="float: none;" title="A entrada possui ${entrada.anexos?size} anexo(s)">
   					<a class="btn .btn-small dropdown-toggle" style="float: none;" data-toggle="dropdown" href="#">
   						${entrada.anexos?size}
   						<span class="caret"></span>
   					</a>
   					<ul class="dropdown-menu pull-right esquerda">
   						<#list entrada.anexos as anexo>
   						<li><a href="${path}/anexo/${anexo.id}/" title="${anexo.descricao}" target="_blank"><img src="${path}/img/document_down.png"/> ${anexo.nomeArquivo}</a></li>
   						</#list>
   					</ul>
   				</div>
				</#if>
				</div>
   			</td>
   			<#if analista.login??>
   			<td class="centro" style="width: 75px;">
   				<#if analista.isLider() || entrada.analista.login == analista.login>
   				
   				<div class="btn-toolbar" style="padding: 0; margin: 0; line-height: 10px;">
  					<div class="btn-group" style="padding: 0; margin: 0;">
   						<a href="${path}/entrada/${entrada.id}/editar/" title="Editar Entrada" class="btn btn-small"><i class="icon-pencil"></i></a>
		   				<a href="#" data-target="${path}/entrada/${entrada.id}/apagar/" title="Excluir Entrada" class="btn btn-small" data-codigo="${entrada.id}" data-data="${entrada.data?date}" data-analista="${entrada.analista.nomeResumido}" data-cliente="${entrada.cliente.nomeResumido}" onclick="confirmaExcluxaoEntrada(this); return false;"><i class="icon-remove"></i></a>
   					</div>
				</div>
   				</#if>
   			</td>
   			</#if>
   		</tr>
   		</#list>
   	</tbody>
</table>

<div id="base-tabela">
	<div id="botao-relatorio" class="btn-group mostra-popover" data-placement="left" title="Emitir Relatório de Entradas" data-content="Gera um relatório com as entradas utilizando o mesmo filtro aplicado na tela. Utilize o susbmenu para escolher o formato ou clique no botão para obter um PDF.">
		<a href="${path}/relatorio/entradas/pdf/" class="btn btn-success">Relatório de Entradas</a>
		<button class="btn btn-success dropdown-toggle" data-toggle="dropdown">
			<span class="caret"></span>
		</button>
		<ul class="dropdown-menu">
			<li><a href="${path}/relatorio/entradas/pdf/"><img src="${path}/img/document-pdf-text.png"/> Adobe PDF</a></li>
			<li><a href="${path}/relatorio/entradas/xls/"><img src="${path}/img/document-excel-table.png"/> Planilha do Excel</a></li>
		</ul>
	</div>
	<div id="botao-relatorio-log" class="btn-group mostra-popover" data-placement="left" title="Emitir Relatório de Log de Operações" data-content="Gera um relatório de log das operações executadas no sistema utilizando o mesmo filtro aplicado na tela (analista e data). Utilize o susbmenu para escolher o formato ou clique no botão para obter um PDF.">
		<a href="${path}/relatorio/log/pdf/" class="btn btn-success">Log de Operações</a>
		<button class="btn btn-success dropdown-toggle" data-toggle="dropdown">
			<span class="caret"></span>
		</button>
		<ul class="dropdown-menu">
			<li><a href="${path}/relatorio/log/pdf/"><img src="${path}/img/document-pdf-text.png"/> Adobe PDF</a></li>
			<li><a href="${path}/relatorio/log/xls/"><img src="${path}/img/document-excel-table.png"/> Planilha do Excel</a></li>
		</ul>
	</div>
	<span id="resumo-tabela" class="label label-info">${totalEntradas}</span>
</div>

<div class="modal hide" id="confirmaExcluirEntrada">
  <div class="modal-header">
    <a class="close" data-dismiss="modal">×</a>
    <h3>Exclusão da Entrada</h3>
  </div>
  <div class="modal-body">
    <p>Deseja excluir a entrada <strong><span class="codigo-exclusao"></span></strong> do analista 
    	<strong><span class="nome-analista-exclusao"></span></strong> em <strong><span class="data-entrada-exclusao"></span></strong> 
    	no cliente <strong><span class="nome-cliente-exclusao"></span></strong>?</p>
    <p><em>Todos os anexos serão apagados.</em></p>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn btn-primary sim">Sim</a>
    <a href="#" class="btn nao" data-dismiss="modal">Não</a>
  </div>
</div>

<#include 'footer.ftl'/>