package com.fisa.logic.impl;

import com.fisa.dto.DataTypeDTO;
import com.fisa.dto.TemplateResultDto;
import com.fisa.logic.CreateFileTemplate;
import com.fisa.utils.PathsUtils;
import com.fisa.utils.StringUtils;

import java.util.List;

public class LiquidbaseTemplate extends TemplateAbstract implements CreateFileTemplate {
    private String entityName;
    private List<DataTypeDTO> fields;
    public LiquidbaseTemplate(){
        super();
    }
    @Override
    public TemplateResultDto createFileTemplate() {
        addParams();
        generateTemplate(PathsUtils.PATH_TEMPLATE_LIQUIDBASE);
        String kebabEntity = StringUtils.toKebabCase(entityName);
        fileName = String.format("%s-table.xml", kebabEntity);
        return generateDto(PathsUtils.PATH_TEMPLATE_TARGET_LIQUIDBASE);
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
        context.put("NAME", entityName);
        context.put("KEBAB_CASE", StringUtils.toKebabCase(entityName));
        context.put("SNAKE_CASE", StringUtils.toSnakeCase(entityName));
        context.put("FIELDS", fields);
        context.put("IS_CHILD_ENTITY", isChildEntity);
        context.put("FATHER_ENTITY", StringUtils.toSnakeCase(fatherEntity) );
        context.put("FATHER_ENTITY_KEBAB", StringUtils.toKebabCase(fatherEntity) );
    }

    @Override
    public void setIsChildEntity(boolean isChildEntity) {
        super.isChildEntity = isChildEntity;
    }

    @Override
    public void setFatherEntity(String fatherEntity) {
        super.fatherEntity = fatherEntity;
    }
}
