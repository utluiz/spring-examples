create table if not exists usuario (
	id bigserial primary key, 
	nome_usuario varchar(70) unique not null,
	senha varchar(20) not null,
	nome varchar(70) not null,
	ultimo_acesso timestamp 
);

create table if not exists entrada (
	id bigserial primary key,
	horario timestamp not null,
	descricao varchar(250) not null,
	prioridade char(1) not null,
	id_usuario int not null,
	constraint fk_entrada_usuario foreign key (id_usuario) references usuario(id)
);
