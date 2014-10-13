<#if id??>
	<#assign titulo="Alteração de Entrada"/>
<#else>
	<#assign titulo="Nova Entrada"/>
</#if>
<#assign tipoPagina=2/>

<#include 'header.ftl'/>

<form class="form-horizontal" action="${path}/entrada/<#if id??>${id}/editar/<#else>incluir/</#if>" method="post">
    <fieldset>
    	<#if transacao??>
        <input type="hidden" name="transacao" value="${transacao}"/>
        </#if>
        <#if !analista.isLider()>
		<input type="hidden" name="analista.login" value="${analista.login}"/>
		</#if>
		
		<#if id??>
		<div class="control-group">
		    <label for="idlabel" class="control-label">Código</label>
		    <div class="controls">
		    	<input type="text" readonly="true" id="idlabel" value="${id}" class="input-small"/>
		    </div>
		</div>
        
        </#if>
        
        <div class="control-group<#if validacao.erros['data']??> error</#if>">
		    <label for="data" class="control-label">Data</label>
		    <div class="controls">
		        <input type="text" id="data" name="data"<#if entrada.data??> value="${entrada.data?date}"</#if> class="input-small" maxlength="10"/>
		        <#if validacao.erros['data']??>
		        <span class="help-block">${validacao.erros['data'].mensagem}</span> 
		        </#if>
		    </div>
		</div>
		
        <div class="control-group<#if validacao.erros['analista.login']??> error</#if>">
	        <label for="analista.login" class="control-label">Analista</label>
        	<div class="controls">
		        <select id="analista.login" name="analista.login" class="input-xxlarge"<#if entrada.analista??> value="${entrada.analista.login}"</#if><#if !analista.isLider()> disabled="true"</#if>>
		        	<option value="">-- Selecione um analista --</option>
		        	<#list analistas as analistaAtual>
		        	<option value="${analistaAtual.login}"<#if entrada.analista?? && entrada.analista.login?? && analistaAtual.login == entrada.analista.login> selected</#if>>${analistaAtual.nome}</option>
		        	</#list>
		        </select>
		        <#if validacao.erros['analista.login']??>
		        <span class="help-block">${validacao.erros['analista.login'].mensagem}</span> 
		        </#if>
		    </div>
		</div>
    
    	<div class="control-group<#if validacao.erros['cliente.codigo']??> error</#if>">
	        <label for="cliente.codigo" class="control-label">Cliente</label>
    		<div class="controls">
		        <select id="cliente.codigo" name="cliente.codigo" class="input-xxlarge">
		        	<option value="">-- Selecione o cliente --</option>
		        	<#list clientes as clienteAtual>
		        		<option value="${clienteAtual.codigo}"<#if entrada.cliente?? && entrada.cliente.codigo?? && clienteAtual.codigo == entrada.cliente.codigo> selected</#if>>${clienteAtual.nome}</option>
		        	</#list>
		        </select>
		        <#if validacao.erros['cliente.codigo']??>
		        <span class="help-block">${validacao.erros['cliente.codigo'].mensagem}</span> 
		        </#if>
		    </div>
		</div>
		
		<div class="control-group<#if validacao.erros['periodo']??> error</#if>">
		    <label for="periodo" class="control-label">Período</label>
		    <div class="controls">
		        <input type="text" name="periodo" id="periodo" value="${entrada.periodo}" class="input-xxlarge" data-validate="typeahead" maxlength="100"/>
		        <#if validacao.erros['periodo']??>
		        <span class="help-block">${validacao.erros['periodo'].mensagem}</span> 
		        </#if>
		    </div>
		</div>
		
		<div class="control-group<#if validacao.erros['assunto']??> error</#if>">
		    <label for="assunto" class="control-label">Assunto</label>
		    <div class="controls">
		    	<textarea id="assunto" name="assunto" class="input-xxlarge">${entrada.assunto}</textarea>
		        <#if validacao.erros['assunto']??>
		        <span class="help-block">${validacao.erros['assunto'].mensagem}</span> 
		        </#if>
		    </div>
		</div>
    	
        <div class="controls">
        	<button title="Confirmar <#if id??>as alterações na entrada atual<#else>a inclusão da entrada atual</#if>" type="submit" class="btn btn-primary"><img src="${path}/img/check.png")"/> Confirmar</button>
        	<a title="Voltar para a lista de entradas e cancelar <#if id??>as alterações na entrada atual<#else>a inclusão da entrada atual</#if>" id="voltar_entrada" class="btn" href="${path}/entradas/"><img src="${path}/img/cancelar.png")"/> Voltar</a>
        </div>
	</fieldset>
	
</form>

<div id="anexos" class="well">
	<h4 style="float: left">Anexos *</h4>
    <form id="fileupload" method="POST" enctype="multipart/form-data" accept-charset="utf-8" 
<#if id??>
    	action="${path}/anexo/enviar/${id}/" data-list="${path}/anexo/listar/${id}/" multiple data-url="${path}/anexo/enviar/${id}/"
<#else>
		action="${path}/anexo/tmp/enviar/${transacao}/" data-list="${path}/anexo/tmp/listar/${transacao}/" multiple data-url="${path}/anexo/tmp/enviar/${transacao}/"
</#if>>
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div style="float:right">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button" title="Clique e selecione os arquivos para anexar">
                    <i class="icon-plus icon-white"></i>
                    <span>Enviar arquivos...</span>
                    <input type="file" name="files[]" multiple/>
                </span>
            </div>
            <div class="span5">
                <!-- The global progress bar -->
                <div class="progress progress-success progress-striped active fade">
                    <div class="bar" style="width: 0%;"></div>
                </div>
            </div>
            <div style="padding-left: 10px; float: left"><div id="fileupload-progress" ></div></div>
        </div>
        <!-- The loading indicator is shown during image processing -->
        <div class="fileupload-loading"></div>
        <br>
        <!-- The table listing the files available for upload/download -->
        <table id="files" class="table table-striped table-condensed">
        	<thead style="display: none">
        		<tr>
        			<th>Arquivo</th>
        			<th style="width: 70px"><span title="Clique na descrição do anexo para editá-la" class="mostra-tooltip">Descrição <i class="icon-edit"></i></span></th>
        			<th>Usuário</th>
        			<th style="text-align: center">Data de Envio</th>
        			<th style="text-align: right">Tamanho</th>
        			<th>&nbsp;</th>
        		</tr>
        	</thead>
        	<tbody class="files"></tbody>
        </table>
    </form>
    <div><em>* Inclusões, alterações e exclusões de anexos serão efetivadas imediatamente, independente da confirmação dos dados da entrada.</em></div>
</div>
<#--
<div class="modal hide" id="confirmaExcluirAnexo">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Exclusão do Anexo</h3>
  </div>
  <div class="modal-body">
    <p>Deseja excluir o anexo <strong><span class="nome-anexo-exclusao"></span> (<span class="tamanho-anexo-exclusao"></span>)</strong> 
    	enviado em <strong><span class="data-anexo-exclusao"></span></strong> por <strong><span class="analista-anexo-exclusao"></span></strong>?</p>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn btn-primary sim">Sim</a>
    <a href="#" class="btn nao" data-dismiss="modal">Não</a>
  </div>
</div>
-->
<#include 'footer.ftl'/>