package com.fisa.logic.impl;

import com.fisa.dto.DataTypeDTO;
import com.fisa.dto.TemplateResultDto;
import com.fisa.logic.CreateFileTemplate;
import com.fisa.utils.PathsUtils;
import com.fisa.utils.StringUtils;

import java.util.List;

public class EntityTemplate extends TemplateAbstract implements CreateFileTemplate {
    private String entityName;
    private List<DataTypeDTO> fields;

    @Override
    public TemplateResultDto createFileTemplate() {
        addParams();
        generateTemplate(PathsUtils.PATH_TEMPLATE_ENTITY);
        fileName = String.format("%sEntity.java", entityName);
        return generateDto(PathsUtils.PATH_TEMPLATE_TARGET_ENTITY);
    }

    @Override
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public void setFields(List<DataTypeDTO> fields) {
        this.fields = fields;
    }

    @Override
    protected void addParams() {
        context.put("PACKAGE_NAME", "package com.fisa.mobilemb.datos.entity;");
        context.put("NAME", entityName);
        context.put("KEBAB_CASE", StringUtils.toKebabCase(entityName));
        context.put("SNAKE_CASE", StringUtils.toSnakeCase(entityName));
        context.put("FIELDS", fields);
    }
}
