DELETE FROM users;
DELETE FROM boards;
DELETE FROM columns;

ALTER SEQUENCE users_user_id_seq RESTART WITH 1000;
ALTER SEQUENCE boards_board_id_seq RESTART WITH 1000;
ALTER SEQUENCE columns_column_id_seq RESTART WITH 1000;
