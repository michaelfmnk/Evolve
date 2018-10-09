create table verification_codes (
  verification_code varchar(20) not null,
  user_id int not null references users(user_id) on update cascade on delete cascade,
  constraint verification_codes_pk primary key (verification_code, user_id)
);
