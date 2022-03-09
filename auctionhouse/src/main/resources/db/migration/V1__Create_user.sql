create table User(
    id serial not null,
    username varchar(50) not null,
    password varchar(50) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    organisation varchar(50) not null,
    isAdmin boolean not null,
    isBlocked boolean not null,
    primary key(id)
);