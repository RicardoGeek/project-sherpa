create table process_pool
(
    id          int                not null auto_increment,
    search_term text                   not null,
    domain      text                   not null,
    sink_date   datetime default NOW() not null,
    constraint process_pool_pk
        primary key (id)
);

create table proxies
(
    id          int                not null auto_increment,
    host     varchar(150) not null,
    port     int          null,
    username varchar(150) null,
    password varchar(150) null,
    quality  varchar(50)  null,
    provider varchar(50)  null,
    constraint proxies_pk
        primary key (id)
);
