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


    public static final String PATH_TEMPLATE_DTO = "templates/DTO_TEMPLATE.vm";
    public static final String PATH_TEMPLATE_TARGET_DTO = "/personas-rest-mb-resources/src/main/java/com/fisa/mobilemb/controller";


    public static final String PATH_TEMPLATE_SERVICE = "templates/SERVICE_TEMPLATE.vm";
    public static final String PATH_TEMPLATE_TARGET_SERVICE = "/personas-rest-mb-resources/src/main/java/com/fisa/mobilemb/service";

    public static final String PATH_TEMPLATE_SERVICE_IMPL = "templates/SERVICE_IMPL_TEMPLATE.vm";
    public static final String PATH_TEMPLATE_TARGET_SERVICE_IMPL = "/personas-rest-mb-resources/src/main/java/com/fisa/mobilemb/service/impl";

    public static final String PATH_TEMPLATE_ENTITY = "templates/ENTITY_TEMPLATE.vm";
    public static final String PATH_TEMPLATE_TARGET_ENTITY = "personas-rest-mb-datos/src/main/java/com/fisa/mobilemb/datos/entity";

    public static final String PATH_TEMPLATE_CONTROLLER = "templates/CONTROLLER_TEMPLATE.vm";
    public static final String PATH_TEMPLATE_TARGET_CONTROLLER = "/personas-rest-mb-resources/src/main/java/com/fisa/mobilemb/controller";

    public static final String PATH_TEMPLATE_LIQUIDBASE = "templates/LIQUIDBASE_TEMPLATE.vm";
    public static final String PATH_TEMPLATE_TARGET_LIQUIDBASE = "personas-rest-mb-datos/src/main/resources/db";

}
