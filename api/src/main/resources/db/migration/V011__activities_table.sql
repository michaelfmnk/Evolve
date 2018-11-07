create table activities (
  activity_id serial,
  board_id int not null,
  actor_id int not null,
  type varchar(60) not null,
  recorded_date timestamp without time zone default now() not null,
  data jsonb,
  constraint activities_boards_board_id_fk foreign key (board_id)
  references boards (board_id) on delete cascade on update cascade,
  constraint activities_users_user_id_fk foreign key (actor_id)
  references users (user_id) on delete cascade on update cascade
);
