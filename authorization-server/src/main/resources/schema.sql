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
