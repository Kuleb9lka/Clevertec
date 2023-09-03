create table if not exists transactions (
            id serial unique not null,
            date_time timestamp with time zone not null,
            money_amount numeric not null,
            currency_name varchar(15) not null,
            sender_account_number varchar(50) not null,
            receiver_account_number varchar(50) not null,
            foreign key (sender_account_number) references bank_accounts(number) on delete no action,
            foreign key (receiver_account_number) references bank_accounts(number) on delete no action,
            foreign key (currency_name) references currencies("name") on delete no action,
            primary key (id)
);