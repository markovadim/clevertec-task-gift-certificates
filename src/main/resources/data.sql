insert into tags (name)
values ('tag_1'),
       ('tag_2'),
       ('happy'),
       ('women');

insert into certificates (name, description, price, duration, create_date)
VALUES ('certificate_1', 'From data.sql', 33.1, 13, '2001-12-03'),
       ('certificate_2', 'From data.sql (part2)', 43.1, 23, '2021-12-04'),
       ('certificate_3', 'From data.sql (part3)', 11.4, 13, '2022-11-07'),
       ('certificate_4', 'From data.sql (part4)', 23.8, 5, '2023-10-05'),
       ('certificate_5', 'From data.sql (part5)', 21.2, 14, '2022-11-01');


insert into tag_certificate (tag_id, certificate_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (1, 2),
       (4, 2);