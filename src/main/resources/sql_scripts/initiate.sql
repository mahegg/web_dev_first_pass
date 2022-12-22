alter table password_store
    owner to myusername;

create table role
(
    id   serial
        constraint role_pk
            primary key,
    role varchar
);

alter table role
    owner to myusername;

create table user_roles
(
    user_id integer,
    role_id integer
);

alter table user_roles
    owner to myusername;

create table my_users
(
    id       serial
        constraint user_pk
            primary key,
    username varchar,
    email    varchar,
    password varchar
);

alter table my_users
    owner to myusername;

