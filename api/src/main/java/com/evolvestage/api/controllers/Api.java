package com.evolvestage.api.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Api {
    public final String ROOT = "/api";

    @UtilityClass
    public class Commons {
        public final String VERSION = "/version";
        public final String GIT_LOG = "/gitlog";
        public final String BACKGROUNDS = "/backgrounds";
    }

    @UtilityClass
    public class Auth {
        public final String LOGIN = "/auth/login";
        public final String SIGN_UP = "/auth/sign-up";
    }

    @UtilityClass
    public class Boards {
        public final String BOARDS = "/boards";
        public final String BOARD_BY_ID = "/boards/{board_id}";
        public final String BOARD_ACTIVITIES = "/boards/{board_id}/activities";
        public final String BOARD_COLUMNS = "/boards/{board_id}/columns";
        public final String BOARD_COLUMN_BY_ID = "/boards/{board_id}/columns/{column_id}";
        public final String BOARD_LABELS = "/boards/{board_id}/labels";
        public final String BOARD_CARDS = "/boards/{board_id}/columns/{column_id}/cards";
        public final String BOARD_CARD_BY_ID = "/boards/{board_id}/cards/{card_id}";
        public final String MOVE_CARD_BY_ID = "/boards/{board_id}/cards/{card_id}/move/{destination_id}";
        public final String BOARD_CARDS_ARCHIVE_CARD = "/boards/{board_id}/cards/{card_id}/archive";
    }

    @UtilityClass
    public class Users {
        public final String VERIFY = "/users/{user_id}/verify";
        public final String USER = "/users/{user_id}";
        public final String USER_ACTIVITIES = "/users/{user_id}/activities";
    }
}
