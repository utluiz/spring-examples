create table usuario (
	id int auto_increment primary key, 
	nome_usuario varchar(70) unique not null,
	senha varchar(20) not null,
	nome varchar(70) not null,
	ultimo_acesso datetime
);

create table entrada (
	id int auto_increment primary key,
	horario datetime not null,
	descricao varchar(250) not null,
	prioridade char(1) not null,
	id_usuario int not null,
	constraint fk_entrada_usuario foreign key (id_usuario) references usuario(id)
);

insert into usuario (nome_usuario, senha, nome) values ('luiz', '123', 'Luiz Ricardo');
insert into entrada (horario, descricao, prioridade, id_usuario) values ('2014-10-14 08:00:00', 'Curso Spring MVC', 'I', 1);
insert into entrada (horario, descricao, prioridade, id_usuario) values ('2014-10-14 13:00:00', 'Churrasco', 'P', 1);