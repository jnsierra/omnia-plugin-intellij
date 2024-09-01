package com.fisa.utils;

public class PathsUtils {
    private static PathsUtils instance;

    private PathsUtils() {}

    public static PathsUtils getInstance() {
        if (instance == null) {
            instance = new PathsUtils();
        }
        return instance;
    }
    //URLs Templates
    public static final String PATH_TEMPLATE_REPOSITORY = "templates/REPOSITORY_TEMPLATE.vm";
    public static final String PATH_TEMPLATE_TARGET_REPOSITORY = "personas-rest-mb-datos/src/main/java/com/fisa/mobilemb/datos/repository";

}
