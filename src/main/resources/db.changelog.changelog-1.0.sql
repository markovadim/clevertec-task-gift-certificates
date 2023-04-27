--liquibase formatted sql

--changeset liquibase-demo-service:create tables tags, certificates, tag_certificate, users, orders, order_certificate
create table tags
(
    id   bigserial primary key,
    name varchar(50) not null unique
);
create table if not exists certificates
(
    id             bigserial primary key,
    name           varchar(50)      not null unique,
    description    varchar(255),
    price          double precision not null,
    duration       int8,
    create_date     timestamp        not null,
    last_update_date timestamp
);

create table if not exists tag_certificate
(
    id            bigserial,
    tag_id         bigint,
    certificate_id bigint,
    constraint fk_tag
        foreign key (tag_id) references tags (id),
    constraint fk_certificate
        foreign key (certificate_id) references certificates (id)
);

create table if not exists users
(
    id    bigserial primary key,
    name  varchar(50),
    email varchar(50) not null unique
);

create table if not exists orders
(
    id         bigserial primary key,
    number     bigint           not null unique,
    user_id    bigint,
    order_price double precision not null,
    buy_date    timestamp        not null,
    constraint fk_user foreign key (user_id) references users (id)
);

create table if not exists order_certificate
(
    id             bigserial,
    order_id       bigint,
    certificate_id bigint,
    constraint fk_order
        foreign key (order_id) references orders (id),
    constraint fk_certificate
        foreign key (certificate_id) references certificates (id)
);