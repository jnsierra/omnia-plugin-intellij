package com.fisa.dto;

import com.fisa.utils.StringUtils;

public class DataTypeDTO {
    private String nameField;
    private String typeFieldJava;
    private String typeFieldSql;
    private String snakeCaseName;

    public DataTypeDTO(String nameField, String typeFieldJava, String typeFieldSql) {
        this.nameField = nameField;
        this.typeFieldJava = typeFieldJava;
        this.typeFieldSql = typeFieldSql;
        this.snakeCaseName = StringUtils.toSnakeCase(nameField);
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String getTypeFieldJava() {
        return typeFieldJava;
    }

    public void setTypeFieldJava(String typeFieldJava) {
        this.typeFieldJava = typeFieldJava;
    }

    public String getTypeFieldSql() {
        return typeFieldSql;
    }

    public void setTypeFieldSql(String typeFieldSql) {
        this.typeFieldSql = typeFieldSql;
    }

    public String getSnakeCaseName() {
        return snakeCaseName;
    }

    public void setSnakeCaseName(String snakeCaseName) {
        this.snakeCaseName = snakeCaseName;
    }
}
