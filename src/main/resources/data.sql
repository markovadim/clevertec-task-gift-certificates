insert into tags (name)
values ('tag_1'),
       ('tag_2'),
       ('happy'),
       ('women');

insert into certificates (name, description, price, createDate)
VALUES ('certificate_1', 'From data.sql', 33.1, '2001-12-03'),
       ('certificate_2', 'From data.sql (part2)', 43.1, '2021-12-04');


insert into tag_certificate (tagId, certificateId)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (1, 2),
       (4, 2);