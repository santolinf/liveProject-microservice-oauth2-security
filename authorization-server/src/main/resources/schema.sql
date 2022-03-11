-- use a SERIAL or BIGSERIAL data type, which creates a sequence behind the scenes and increments/uses it at insert time
-- and if you want to reference it from another table, use integer or bigint

create table if not exists users (
    id bigserial not null,
    username varchar(255) not null unique,
    password varchar(255) not null,
    primary key (id)
);

create table if not exists authority (
    id bigserial not null,
    name varchar(255) not null,
    user_id bigint not null,
    primary key (id)
);

alter table authority drop constraint if exists FKauthorityusers;
alter table authority add constraint FKauthorityusers foreign key (user_id) references users (id);

create table if not exists client (
    id bigserial not null,
    client_id varchar(255) not null unique,
    secret varchar(255) not null,
    scope varchar(255),
    redirect_uri varchar(255),
    primary key (id)
);

create table if not exists grant_type (
    id bigserial not null,
    grant_type varchar(255),
    client_id bigint not null,
    primary key (id)
);

alter table grant_type drop constraint if exists FKgranttypeclient;
alter table grant_type add constraint FKgranttypeclient foreign key (client_id) references client (id);
