TRUNCATE TABLE entrada, usuario RESTART IDENTITY CASCADE;

insert into usuario (nome_usuario, senha, nome) values ('luiz', '123', 'Luiz Ricardo');

insert into entrada (horario, descricao, prioridade, id_usuario) values ('2014-10-14 08:00:00', 'Curso Spring MVC', 'I', 1);
insert into entrada (horario, descricao, prioridade, id_usuario) values ('2014-10-14 13:00:00', 'Churrasco', 'P', 1);
insert into entrada (horario, descricao, prioridade, id_usuario) values ('2014-10-21 08:00:00', 'Contitnuação Curso Spring MVC', 'I', 1);