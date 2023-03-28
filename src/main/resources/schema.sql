create table if not exists tags
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
    duration       timestamp,
    createDate     timestamp        not null,
    lastUpdateDate timestamp
);

create table tag_certificate
(
    id            bigserial,
    tagId         bigint,
    certificateId bigint,
    constraint fk_tag
        foreign key (tagId) references tags (id),
    constraint fk_certificate
        foreign key (certificateId) references certificates (id)
);