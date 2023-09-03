create table if not exists banks (
                       id SERIAL not null,
                       "name" varchar(50) unique not null,

                       primary key (id)
);