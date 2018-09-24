create table users (
  user_id serial primary key,
  email varchar(120) not null,
  first_name varchar(120),
  last_name varchar(120),
  password varchar(100) not null,
  last_password_reset_date timestamp without time zone not null default now(),
  avatar_id uuid not null,
  enabled boolean not null default false
);

create table boards (
    board_id serial primary key,
    name varchar(100) not null,
    owner_id int not null,
    constraint boards_users_user_id_fk foreign key (owner_id)
    references users (user_id) on delete cascade on update cascade
);

create table columns (
    column_id serial primary key,
    board_id int not null,
    name varchar(100) not null,
    "order" int,
    constraint columns_boards_board_id_fk foreign key (board_id)
    references boards (board_id) on delete cascade on update cascade
);

create table boards_users (
    board_id int,
    user_id int,
    constraint boards_users_pk primary key (board_id, user_id),
    constraint boards_users_users_user_id_fk foreign key (user_id)
    references users (user_id) on delete cascade on update cascade,
    constraint boards_users_boards_board_id_fk
    foreign key (board_id) references boards (board_id) on delete cascade on update cascade
);

create table labels (
    label_id serial primary key,
    name varchar(40),
    color varchar(10)
);

create table cards (
    card_id serial primary key,
    column_id int not null,
    title varchar(120) not null,
    content text,
    author_id int not null,
    created_time timestamp without time zone default now() not null,
    constraint cards_users_user_id_fk foreign key (author_id)
    references users (user_id) on delete cascade on update cascade,
    constraint cards_columns_column_id_fk foreign key (column_id)
    references columns (column_id) on delete cascade on update cascade
);

create table cards_labels (
    card_id int,
    label_id int,
    constraint cards_labels_pk primary key (card_id, label_id),
    constraint cards_labels_labels_label_id_fk foreign key (label_id)
    references labels (label_id) on delete cascade on update cascade,
    constraint cards_labels_cards_card_id_fk foreign key (card_id)
    references cards (card_id) on delete cascade on update cascade
);

create table boards_labels (
    board_id int,
    label_id int,
    constraint boards_labels_pk primary key (board_id, label_id),
    constraint boards_labels_boards_board_id_fk foreign key (board_id)
    references boards (board_id) on delete cascade on update cascade,
    constraint boards_labels_labels_label_id_fk foreign key (label_id)
    references labels (label_id) on delete cascade on update cascade
);

create table checklist_items (
    item_id serial primary key,
    card_id int not null,
    content varchar(120) not null,
    done boolean default false not null,
    "order" int,
    constraint checklist_items_cards_card_id_fk foreign key (card_id)
    references cards (card_id) on delete cascade on update cascade
);

create table card_comments (
    comment_id serial primary key,
    user_id int not null,
    card_id int not null,
    content text not null,
    created_time timestamp without time zone default now() not null,
    constraint card_comments_users_user_id_fk foreign key (user_id)
    references users (user_id) on delete cascade on update cascade,
    constraint card_comments_cards_card_id_fk foreign key (card_id)
    references cards (card_id) on delete cascade on update cascade
);

create table attachments (
    attachment_id serial primary key,
    file_id uuid not null
);

create table cards_attachments (
    attachment_id int,
    card_id int,
    constraint cards_attachments_pk primary key (attachment_id, card_id),
    constraint cards_attachments_cards_card_id_fk foreign key (card_id)
    references cards (card_id) on delete cascade on update cascade,
    constraint cards_attachments_attachments_attachment_id_fk foreign key (attachment_id)
    references attachments (attachment_id) on delete cascade on update cascade
);

create table comments_attachments
(
    attachment_id int,
    comment_id int,
    constraint comments_attachments_pk primary key (attachment_id, comment_id),
    constraint comments_attachments_cards_comments_comment_id_fk foreign key (comment_id)
    references card_comments (comment_id) on delete cascade on update cascade,
    constraint comments_attachments_attachments_attachment_id_fk foreign key (attachment_id)
    references attachments (attachment_id) on delete cascade on update cascade
);

