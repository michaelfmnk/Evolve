DELETE FROM users_authorities;
DELETE FROM users;
DELETE FROM boards;
DELETE FROM boards_users;
DELETE FROM verification_codes;

ALTER SEQUENCE users_user_id_seq RESTART WITH 1000;
ALTER SEQUENCE boards_board_id_seq RESTART WITH 1000;
