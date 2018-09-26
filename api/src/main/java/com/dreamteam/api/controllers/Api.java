package com.dreamteam.api.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Api {
    public final String ROOT = "/api";

    @UtilityClass
    public class Commons {
        public final String VERSION = "/version";
        public final String GIT_LOG = "/gitlog";
    }

    @UtilityClass
    public class Auth {
        public final String LOGIN = "/auth/login";
        public final String SIGN_UP = "/auth/sign-up";
    }
}
