insert into users (user_id, email, first_name, last_name, password, last_password_reset_date, avatar_id, enabled)
VALUES (1,
        'michaelfmnk@gmail.com',
        'Kateryna',
        'Kanivets',
        '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK',
        '2018-07-16 14:40:14.518000',
        '0485de66-c013-11e8-a355-529269fb1459',
        true), --test
       (2,
        'nickBrown@gmail.com',
        'Nick',
        'Brown',
        '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK',
        '2016-07-16 14:40:14.518000',
        '0485de66-c013-11e8-a355-529269fb1459',
        false), --test
       (3,
        'admin@gmail.com',
        'admin',
        'admin',
        '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK',
        '2017-08-12 14:40:14.518000',
        '0485de66-c013-11e8-a355-529269fb1459',
        true),
       (4,
        'johnDoe@gmail.com',
        'John',
        'Doe',
        '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK',
        '2008-10-05 14:40:14.518000',
        '0485de66-c013-11e8-a355-529269fb1459',
        false); --test

insert into boards (board_id, name, owner_id, background_id)
values (1, 'board first', 1, null),
       (2, 'board second', 2, null),
       (3, 'board third', 3, null);

insert into boards_users (board_id, user_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 2),
       (3, 3);