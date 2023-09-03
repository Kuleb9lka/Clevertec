CREATE TABLE IF NOT EXISTS users (
                       id serial primary key not null,
                       "name" varchar(50) not null,
                       surname varchar(50) not null,
                       patronymic varchar(50)
);