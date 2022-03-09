create table AuctionUser(
    id serial primary key not null,
    username varchar(50) unique not null,
    password varchar(50) not null,
    isAdmin boolean not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    organisation varchar(50) not null,
    blocked boolean not null,
);

insert into AuctionUser (
    username, password, isAdmin, first_name, last_name, organisation, blocked
) values ('ADMIN', 'adminpassword', true, 'admin', 'admin', 'Adaptive', false);