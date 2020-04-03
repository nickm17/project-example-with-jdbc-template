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