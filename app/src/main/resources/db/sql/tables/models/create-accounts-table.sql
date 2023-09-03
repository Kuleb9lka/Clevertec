create table if not exists bank_accounts (
                       id serial unique not null,
                       number varchar(50) unique not null,
                       bank_id bigint not null,
                       user_id bigint not null,
                       date_open timestamp not null,
                       currency_name varchar(15) not null,
                       money_amount numeric default 0,
                       is_active boolean not null,
                       foreign key (bank_id) references banks(id) on delete cascade,
                       foreign key (user_id) references users(id) on delete cascade,
                       foreign key (currency_name) references currencies("name") on delete no action,
                       primary key (id)
);