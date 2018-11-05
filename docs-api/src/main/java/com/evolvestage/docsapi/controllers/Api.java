package com.evolvestage.docsapi.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Api {

    public static final String ROOT = "/docs-api";

    @UtilityClass
    public class TemporaryStorage {
        public final String TEMPORARY_LOCATION = "/temporary";
        public final String TEMPORARY_FILE_BY_ID = "/temporary/{file_id}";
    }

    @UtilityClass
    public class PermanentStorage {
        public final String PERMANENT_LOCATION = "/permanent";
        public final String PERMANENT_FILE_BY_ID = "/permanent/{file_id}";
        public final String TEMPORARY_PUBLIC_FILE = "/permanent/public/{file_id}";
    }

    @UtilityClass
    public class Common {
        public final String GIT_LOG = "/gitlog";
    }
}
