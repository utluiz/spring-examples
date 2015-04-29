$(function() {
	$('#botao-limpar').click(function() {
		$('#de,#ate,#descricao').val('');
		$('#botao-filtrar').click();
	});
	
	$('.link-excluir').click(function(e) {
		$('#modal .botao\\-remover').attr('href', this.href);
		$('#modal .id\\-entrada').text($(this).data('id'));
		$('#modal .horario\\-entrada').text($(this).data('horario'));
		$('#modal').modal();
		e.preventDefault();
	});
});