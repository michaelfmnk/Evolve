insert into users (user_id, email, first_name, last_name, password, last_password_reset_date, avatar_id, enabled) VALUES
(1, 'michaelfmnk@gmail.com', 'Michael', 'Fomenko', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2017-07-16 14:40:14.518000', '0485de66-c013-11e8-a355-529269fb1459', true), --test
(2, 'someoneelse@gmail.com', 'Nick', 'Brown', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2007-07-16 14:40:14.518000', '0485de66-c013-11e8-a355-529269fb1459', false), --test
(3, 'admin@gmail.com', 'admin', 'admin', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2007-07-16 14:40:14.518000', '0485de66-c013-11e8-a355-529269fb1459', true),
(4, 'steven@gmail.com', 'Steven', 'Else', '$2a$10$noFrZfy.dxossQlZ4WqX2.U66nRVUeGkjQtNFP7298bcqKmd.amsK', '2007-07-16 14:40:14.518000', '0485de66-c013-11e8-a355-529269fb1459', false);--test

INSERT INTO users_authorities (user_id, authority_id) VALUES
(1, 2),
(2, 2),
(3, 2),
(4, 2);
