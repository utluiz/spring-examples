<#assign titulo='Informações Sobre o Sistema'/>
<#assign tipoPagina=3/>

<#include 'header.ftl'/>

<h2>Agenda de Visitas ao Cliente</h2>
<p>Este sistema foi desenvolvido para auxiliar gerenciamento e consulta das visitas realizadas por analistas de suporte e desenvolvimento aos clientes da <strong>Autbank</strong>.</p>
<ul>
	<li><strong>Número da Versão</strong>: ${versao}</li>
	<li><strong>Data de Geração</strong>: ${data}</li>
	<li><strong>Ambiente</strong>: ${ambiente}</li>
</ul>
<hr/>
<h2>Funcionalidades</h2>
<ul>
	<li><strong>Entradas da Agenda</strong>: permite o gerenciamento de entradas na agenda de visitas pelo analista ou por um supervisor;</li>
	<li><strong>Anexos</strong>: permite o gerenciamento de anexos para cada entrada da agenda;</li>
	<li><strong>Filtros</strong>: facilita a localização de entradas através de vários filtros;</li>
	<li><strong>Relatórios</strong>: facilitam o gerenciamento e controle dos dados exportando a lista de entradas e o log de operações efetuadas pelos usuários para os formatos Excel e Adobe PDF.</li>
</ul>
<hr/>
<h2>Requisitos atendidos</h2>
<ul>
	<li><strong>Auditoria</strong>: registra todas as operações efetuadas nas entradas e anexos;</li>
	<li><strong>Autenticação</strong>: login de usuários utiliza a base do sistema RA (Registro de Atendimento);</li>
	<li><strong>Autorização</strong>: somente usuários supervisores (no RA) podem cadastrar e editar entradas de outros usuários, os demais somente podem cadastrar e editar entradas em seu próprio nome;</li>
	<li><strong>Segurança</strong>: todas as ações verificam se o usuário está devidamente autenticado;</li>
	<li><strong>Usabilidade</strong>: utilização de padrões web e itens visuais adequados e representativos;</li>
	<li><strong>Compatibilidade e Multiplataforma</strong>: Internet Explorer do 6 ao 9, Google Chrome e Firefox.</li>
</ul>

<hr/>
<h2>Tecnologia</h2>
<p>Foram utilizadas as mais recentes tecnologias disponíveis no desenvolvimento web, incluindo forte uso do protocolo REST, HTML 5, CSS 3, javascript e conceitos de Web 2.0.</p>
<p>O sistema do lado do servidor foi desenvolvido com os frameworks:</p>
<ul>
	<li><strong>JPA (Java Persistence API)</strong> [<a href="http://www.hibernate.org" target="_blank">Hibernate</a>]: persistência de dados com mapeamento objeto-relacional;</li>
	<li><strong>JAX-RS (Java API for RESTful Web Services)</strong> [<a href="http://jersey.java.net/" target="_blank">Jersey</a>]: protocolo REST para comunicação cliente-servidor/</li>
	<li><a href="http://http://jasperforge.org/projects/jasperreports/" target="_blank">Jasper Reports</a>: geração de relatórios.</li>
</ul>
<p>Além disso, na camada visual foram utilizadas as seguintes bibliotecas:</p>
<ul>
	<li>
		<span><a href="http://www.jquery.com" target="_blank">jQuery</a>: biblioteca javascript multibrowser que facita e padroniza o uso de scripts nos diversos navegadorews;</span>
		<ul>
			<li><a href="http://www.bluimp.net" target="_blank">jQuery File Upload Plugin</a>: plugin de upload de arquivos;</li>
			<li><a href="jqueryui.com" target="_blank">jQuery UI</a>: plugin visual para jQuery, utilizado principalmente pelo File Upload Plugin.</li>
		</ul>
	</li>
	<li><a href="http://twitter.github.com/bootstrap/" target="_blank">Bootstrap</a>: biblioteca CSS e javascript disponibilizada pelos desenvolvedores do Twitter que fornece componentes visuais e comportamentos padronizados para sistemas web.</li>
</ul>
<p>O projeto utiliza <a href="http://maven.apache.org/" target="_blank">Maven</a> para gerenciar as dependências e controlar o ciclo de vida (compilação, geração de WAR, testes, ...).</p>
<p>O código fonte pode ser encontrado no repositório CVS do <strong>autklinux1</strong> em <code>/cvsroot/sistemas/JSFG/abutils/abutils-agendavisitas</code>.</p>
<hr/>
<h2>Dúvidas, problemas e sugestões</h2>
<p>Fale com <a href="mailto:luizricardo@linux.autbank">luizricardo@linux.autbank</a>.</p>
<hr/>

<#include 'footer.ftl'/>