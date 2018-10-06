create table authorities (
  authority_id serial primary key,
  authority_name varchar(40) unique not null
);

create table users_authorities (
  user_id      int not null references users(user_id) on update cascade on delete cascade,
  authority_id int not null references authorities(authority_id) on update cascade on delete cascade,
  constraint users_authorities_pk primary key (user_id, authority_id)
);

insert into authorities (authority_id, authority_name)
values (1, 'ADMIN'),
       (2, 'USER');
