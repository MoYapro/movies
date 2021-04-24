create sequence director_id_seq start with 1 increment by 20;
create sequence movie_id_seq start with 1 increment by 20;

create table director
(
    id         bigint not null,
    first_name varchar(255),
    last_name  varchar(255),
    primary key (id)
);

create table movie
(
    id          bigint  not null,
    title       varchar(255),
    year        integer not null,
    director_id bigint,
    primary key (id)
);

alter table movie
    add constraint fk_movie_director foreign key (director_id) references director