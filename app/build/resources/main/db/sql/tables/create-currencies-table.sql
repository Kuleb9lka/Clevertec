CREATE TABLE IF NOT EXISTS currencies (
                        id serial unique not null,
                        "name" varchar(15) unique not null,
                        symbol character(5) unique not null,
                        PRIMARY KEY (id)
);

