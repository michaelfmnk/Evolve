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
       (3, 1, 'column 3', 3),
       (4, 2, 'column 1 in the board 2', 1);


insert into boards_users (board_id, user_id)
values
       (1, 1);
