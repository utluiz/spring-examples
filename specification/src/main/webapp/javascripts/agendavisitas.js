/**
* agendavisitas.js
* Copyright 2012 Autbank
*/
$(document).ready(function() {

	//DATE PICKER ---------------------------------
	
	$('#de,#ate,#data').datepicker(
		$.datepicker.regional['pt-BR']
	);
	
	if ($().fileupload) {
		
		$('#fileupload').fileupload();
		
		$('#fileupload').fileupload({
			dataType: 'json',
	        sequentialUploads: true,
	        autoUpload: true,
	        uploadTable: $('#files'),
        	uploadTemplate: function (o) {
                var rows = $();
                $.each(o.files, function (index, file) {
                    var row = $(
                    	'<tr class="template-upload fade">' +
                        '<td class="name"></td>' +
                        '<td class="size" style="text-align: right"></td>' +
                        (file.error ? 
                        		'<td class="error" colspan="3"></td>' :
                                '<td colspan="3"><span><div class="progress" style="margin: 0"><div class="bar" style="width:0%;"></div></div></span></td>'
                        ) + 
                        '<td class="cancel direita"><button type="reset" class="btn btn-warning cancel" title="Parar o upload">' +
                        	'<i class="icon-ban-circle icon-white"></i></button></td></tr>');
                    row.find('.name').text(file.name);
                    row.find('.size').text('Enviando ' + o.formatFileSize(file.size));
                    if (file.error) {
                        row.find('.error').text(locale.fileupload.errors[file.error] || file.error);
                    }
                    rows = rows.add(row);
                });
                return rows;
            },
            downloadTemplate: function (o) {
                var rows = $();
                $.each(o.files, function (index, file) {
                    var row = $('<tr class="template-download fade">' +
                        (file.error ? 
                        	'<td class="name"></td>' +
                            '<td class="size"></td>' +
                            '<td class="error" colspan="3"></td>' :
                            	//'<td><div class="progress progress-animated" style="display: block"><div class="bar" style="width:0%;"></div></div></td>' +
                            '<td class="name"><a target="_blank"></a></td>' +
                        	'<td><input type="text" class="description hideinput" maxlength="150"/></td>' +
                        	'<td><div class="loginanalista"></div></td>' +
                        	'<td class="date" style="text-align: center"></td>' +
                            '<td class="size" style="text-align: right"></td>'
                        ) + 
                        '<td class="delete direita"><button title="Excluir o anexo" type="button" class="btn btn-danger delete" data-confirm="Quer realmente excluir o anexo \'' + file.name + '\'?">' +
	                    	'<i class="icon-trash icon-white"></i></button></td></tr>');
                    row.find('.size').text(o.formatFileSize(file.size));
                    if (file.error) {
                        row.find('.name').text(file.name);
                        row.find('.error').text(locale.fileupload.errors[file.error] || file.error);
                    } else {
                        row.find('.name a').text(file.name);
                        var desc = row.find('.description'); 
                        desc
                        	.attr('value', file.description)
                        	.data('update_url', file.update_url)
                        	.data('valor-original', file.description)
                        	.data('valor-alterado', file.description)
                        	.blur(function(e) {
                        		desc.addClass('hideinput');
                        		desc.data('valor-alterado', desc.attr('value'));
                        		desc.attr('value', desc.data('valor-original'));
                        	})
                        	.focus(function(e) {
                        		desc.attr('value', desc.data('valor-alterado'));
                        		desc.removeClass('hideinput');
                        		$(this).select();
                        	})
                        	.keypress(function(e) {
                        		if (e.which == 13) {
                        			desc.attr('disabled', true);
                        			$.ajax({
                        				url: desc.data('update_url'), 
                        				type: 'POST',
                        				dataType: 'json',
                        				data: JSON.stringify( { "descricao": desc.val(	) } ),
                        				contentType: 'application/json',
                        				success: function (result) {
	                                    	window.result = result;
	                                        if (result && result.descricao) {
	                                        	desc.data('valor-original', result.descricao);
	                                        	desc.data('valor-alterado', result.descricao);
	                                        } else {
	                                        	alert("Erro no ajax ao tentar atualizar a descrição!");
	                                        }
	                                        desc.attr('value', desc.data('valor-original'));
	                                        desc.removeAttr('disabled');
	                                        desc.blur();
                        				},
                        				error: function(jqXHR, textStatus, errorThrown) {
                        					alert("Erro no ajax ao tentar atualizar a descrição: '" + errorThrown + "'!");
                        					desc.removeAttr('disabled');
                        				}
                        			});
                        		} else if (e.which == 27) {
                        			desc.attr('value', data('valor-original'));
                        		}
                        	})
                        	.popover({
                        		placement: 'right',
                        		trigger: 'focus',
                        		title: 'Atualizar descrição do anexo',
                        		html : true,
                        		content: 'Edite a descrição e pressione <strong>ENTER</strong> para confirmar a alteração ou <strong>ESC</strong> para cancelar.'
                        	});;
                        
                        row.find('.date').text(file.date);
                        row.find('.loginanalista').text(file.analista).attr('title', "Enviado por<br/> " + file.nomeAnalista).tooltip({html:true});
                        if (file.thumbnail_url) {
                            row.find('.preview').append('<a><img></a>')
                                .find('img').prop('src', file.thumbnail_url);
                            row.find('a').prop('rel', 'gallery');
                        }
                        row.find('a').prop('href', file.url);
                        row.find('.delete button')
                            .attr('data-type', file.delete_type)
                            .attr('data-url', file.delete_url);
                    }
                    rows = rows.add(row);
                });
                return rows;
            }

	    });
		
		$('#fileupload').bind('fileuploaddestroyed', function (e, data) {
			atualizaTituloAnexos();
		});
		$('#fileupload').bind('fileuploadalways', function (e, data) {
			atualizaTituloAnexos();
		});
		
		// Load existing files:
        $('#fileupload').each(function () {
            var that = this;
            $.ajax({
				url: $(this).data('list'), 
				type: 'GET',
				dataType: 'json',
				contentType: 'application/json',
				context: $('#fileupload')[0],
				success: function (result) {
					window.result = result;
					if (result && result.files && result.files.length) {
	                    $(that).fileupload('option', 'done')
	                        .call(
	                        		that, 
	                        		{ isDefaultPrevented: function() {return false;} },
	                        		{result: result}
	                        	);
	                    atualizaTituloAnexos();
	                }
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert("Erro no ajax ao tentar listar os anexos: '" + errorThrown + "'!");
				}
			});
        });
        
	}
	
	//POPOVERS ---------------------------------
	
	$('.mostra-popover').popover({ trigger: 'hover' });
	
	//HINTS ---------------------------------
	
	$('.mostra-tooltip').tooltip();
	
	//AJUDA DIGITAÇÃO ---------------------------------
	
	$('#periodo').typeahead({
		source: ['Manhã', 'Tarde', 'Período Integral', 'Noite'],
		sorter: null
	});
	
});

/*function confirmaExcluxaoAnexo(link) {

	var l = $(link);
	$('#confirmaExcluirAnexo .nome-anexo-exclusao').text(l.data('cliente'));
	$('#confirmaExcluirAnexo .tamanho-anexo-exclusao').text(l.data('analista'));
	$('#confirmaExcluirAnexo .data-anexo-exclusao').text(l.data('data'));
	$('#confirmaExcluirAnexo .analista-anexo-exclusao').text(l.data('data'));
	$('#confirmaExcluirAnexo .sim').attr('href', l.data('target'));
	$('#confirmaExcluirAnexo').modal('show');
	$('#confirmaExcluirAnexo .nao').focus();
	
}*/

function atualizaTituloAnexos() {
	var titulo = $('#files thead');
	if ($('#files tbody tr').length > 0) {
		titulo.fadeIn();
	} else {
		titulo.fadeOut();
	}
}

function confirmaExcluxaoEntrada(link) {
	
	var l = $(link);
	$('#confirmaExcluirEntrada .codigo-exclusao').text(l.data('codigo'));
	$('#confirmaExcluirEntrada .nome-cliente-exclusao').text(l.data('cliente'));
	$('#confirmaExcluirEntrada .nome-analista-exclusao').text(l.data('analista'));
	$('#confirmaExcluirEntrada .data-entrada-exclusao').text(l.data('data'));
	$('#confirmaExcluirEntrada .sim').attr('href', l.data('target'));
	$('#confirmaExcluirEntrada').modal('show');
	$('#confirmaExcluirEntrada .nao').focus();
	
}