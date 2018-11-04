create table cards_users (
  card_id int,
  user_id int,
  constraint cards_users_pk primary key (card_id, user_id),
  constraint cards_users_users_user_id_fk foreign key (user_id)
  references users (user_id) on delete cascade on update cascade,
  constraint cards_users_cards_card_id_fk
  foreign key (card_id) references cards (card_id) on delete cascade on update cascade
);

alter table "columns" rename column "order" TO order_num;
alter table checklist_items rename column "order" to order_num;
alter table cards add column order_num int;
