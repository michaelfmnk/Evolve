drop table boards_labels;

alter table labels add column board_id int not null;
alter table lasbels add constraint labels_boards_board_id_fk foreign key (board_id)
references boards (board_id) on delete cascade on update cascade;