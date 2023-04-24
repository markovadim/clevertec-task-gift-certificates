--liquibase formatted sql

--changeset liquibase-demo-service:insert_2 in users, orders

insert into users (name, email)
values ('vadim', 'vadim@gmail.com'),
       ('maxim', 'maxim@gmail.com'),
       ('oleg', 'oleg@gmail.com');

insert into orders (number, user_id, order_price, buy_date)
values (321, 1, 13.33, now()),
       (32, 1, 41.12, now());

insert into order_certificate (order_id, certificate_id)
values (1, 1),
       (2, 2);