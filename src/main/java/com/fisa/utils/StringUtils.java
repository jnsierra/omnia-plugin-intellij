package com.fisa.utils;

public enum StringUtils {
    INSTANCE;

    // Método para validar si un String es Upper CamelCase
    public boolean isUpperCamelCase(String input) {
        // Expresión regular para validar Upper CamelCase
        String camelCasePattern = "^[A-Z][a-z]+(?:[A-Z][a-z]+)*$";

        // Validar el String contra la expresión regular
        return input != null && input.matches(camelCasePattern);
    }

    public String mappingDataTypes(String javaDataType) {
        String sqlType;
        switch (javaDataType) {
            case "Date":
                sqlType = "datetime";
                break;
            case "Long":
                sqlType = "INT";
                break;
            default:
                sqlType = "VARCHAR(255)";
                break;
        }
        return sqlType;

    }

    public static String toKebabCase(String input) {
        if(input == null || "".equals(input)) {
            return "";
        }
        return input
                .replaceAll("([a-z])([A-Z])", "$1-$2") // Inserta un guion entre letras minúsculas y mayúsculas
                .replaceAll("([A-Z])([A-Z][a-z])", "$1-$2") // Inserta un guion entre dos letras mayúsculas si la segunda es seguida por una minúscula
                .toLowerCase(); // Convierte todo a minúsculas
    }

    public static String toSnakeCase(String input) {
        if(input == null || "".equals(input)) {
            return "";
        }
        return input
                .replaceAll("([a-z])([A-Z])", "$1_$2") // Inserta un guion bajo entre letras minúsculas y mayúsculas
                .replaceAll("([A-Z])([A-Z][a-z])", "$1_$2") // Inserta un guion bajo entre dos letras mayúsculas si la segunda es seguida por una minúscula
                .toLowerCase(); // Convierte todo a minúsculas
    }
}
