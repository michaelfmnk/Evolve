INSERT INTO users (user_id, email, first_name, last_name, password, last_password_reset_date, avatar_id, enabled)
VALUES (1,
        'michaelfmnk@gmail.com',
        'Michael',
        'Fomenko',
        '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK',
        '2017-07-16 14:40:14.518000',
        '0485de66-c013-11e8-a355-529269fb1459',
        true), --test
       (2,
        'someoneelse@gmail.com',
        'Nick',
        'Brown',
        '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK',
        '2007-07-16 14:40:14.518000',
        '0485de66-c013-11e8-a355-529269fb1459',
        false), --test
       (3,
        'admin@gmail.com',
        'admin',
        'admin',
        '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK',
        '2007-07-16 14:40:14.518000',
        '0485de66-c013-11e8-a355-529269fb1459',
        true),
       (4,
        'steven@gmail.com',
        'Steven',
        'Else',
        '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK',
        '2007-07-16 14:40:14.518000',
        '0485de66-c013-11e8-a355-529269fb1459',
        false); --test

INSERT INTO boards (board_id, "name", owner_id)
VALUES (1, 'SUPERMEGA BOARD', 1),
       (2, 'DOLGOV RULIT', 2),
       (3, 'FPECS FOREVER', 3),
       (4, 'PIU-PIU-PIU', 2);

INSERT INTO users_authorities (user_id, authority_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2);

INSERT INTO columns (column_id, board_id, name, order_num)
VALUES (1, 1, 'column 1', 1),
       (2, 1, 'column 2', 2),
       (3, 1, 'column 3', 3);

INSERT INTO cards (card_id, column_id, title, content, author_id, created_time, order_num)
VALUES (1, 1, 'card 1', 'card 1 from col 1', 1, '2017-07-16 14:40:14.518000', 1),
       (2, 1, 'card 2', 'card 2 from col 1', 2, '2017-08-14 14:30:19.258000', 2),
       (3, 2, 'card 3', 'card 3 from col 2', 3, '2018-08-15 14:20:11.557000', 3),
       (4, 3, 'card 4', 'card 4 from col 3', 4, '2018-09-17 16:30:10.527000', 4);

INSERT INTO labels (label_id, name, color, board_id)
VALUES (1, 'orange label', '#fb5f09', 1),
       (2, 'red label', '#ba3c3c', 1),
       (3, 'green label', '#2a9d3a', 1),
       (4, 'yellow label', '#eae31d', 2),
       (5, 'pink label', '#ed1acd', 2),
       (6, 'brown label', '#95654e', 3);

INSERT INTO cards_labels (card_id, label_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (3, 5),
       (4, 6);

INSERT INTO cards_users (card_id, user_id)
VALUES
       (1, 2);


insert into boards_users (board_id, user_id)
values
       (1, 1),
       (1, 2),
       (1, 3);
