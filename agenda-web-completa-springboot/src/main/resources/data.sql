--insert usuario
merge into usuario (nome_usuario, senha, nome) key (nome_usuario) 
	values ('luiz', '123', 'Luiz Ricardo');

--insert example values
merge into entrada (horario, descricao, prioridade, id_usuario) key (horario) 
	values ('2014-10-14 08:00:00', 'Curso Spring MVC', 'I', 1);
merge into entrada (horario, descricao, prioridade, id_usuario) key (horario) 
	values ('2014-10-14 13:00:00', 'Churrasco', 'P', 1);
merge into entrada (horario, descricao, prioridade, id_usuario) key (horario) 
	values ('2014-10-14 14:00:00', 'Nada 1', 'N', 1);
merge into entrada (horario, descricao, prioridade, id_usuario) key (horario) 
	values ('2014-10-14 15:00:00', 'Nada 2', 'N', 1);
merge into entrada (horario, descricao, prioridade, id_usuario) key (horario) 
	values ('2014-10-21 08:00:00', 'Contitnuação Curso Spring MVC', 'A', 1);