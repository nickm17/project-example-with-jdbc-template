create table car
(
    car_id     serial not null
        constraint car_pk
            primary key,
    car_name   varchar,
    brand      varchar,
    birth_date date,
    color      varchar,
    speed      integer
);

create table customer
(
    lastname     varchar,
    firstname    varchar,
    age          integer,
    address      varchar,
    birthdate    integer,
    "customerId" serial not null
        constraint customer_pk
            primary key
);

alter table customer
    owner to postgres;
