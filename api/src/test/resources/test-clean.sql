DELETE FROM users_authorities;
DELETE FROM cards_labels;
DELETE FROM cards_users;
DELETE FROM users;
DELETE FROM boards;
DELETE FROM columns;
DELETE FROM cards;
DELETE FROM labels;
DELETE FROM boards_users;
DELETE FROM verification_codes;


ALTER SEQUENCE users_user_id_seq RESTART WITH 1000;
ALTER SEQUENCE boards_board_id_seq RESTART WITH 1000;
ALTER SEQUENCE columns_column_id_seq RESTART WITH 1000;
