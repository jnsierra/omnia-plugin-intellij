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
}
