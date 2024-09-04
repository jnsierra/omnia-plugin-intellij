package com.fisa.logic.impl;

import com.fisa.dto.DataTypeDTO;
import com.fisa.dto.TemplateResultDto;
import com.fisa.logic.CreateFileTemplate;
import com.fisa.utils.PathsUtils;

import java.util.List;

public class DtoTemplate extends TemplateAbstract implements CreateFileTemplate {
    private String entityName;
    private List<DataTypeDTO> fields;

    public DtoTemplate() {
        super();
    }

    @Override
    protected void addParams() {
        context.put("PACKAGE_NAME", "package com.fisa.mobilemb.controller;");
        context.put("NAME", entityName);
        context.put("FIELDS", fields);
    }

    @Override
    public TemplateResultDto createFileTemplate() {
        addParams();
        generateTemplate(PathsUtils.PATH_TEMPLATE_DTO);
        fileName = String.format("%sDTO.java", entityName);
        return generateDto(PathsUtils.PATH_TEMPLATE_TARGET_DTO);
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
    public void setIsChildEntity(boolean isChildEntity) {
        super.isChildEntity = isChildEntity;
    }

    @Override
    public void setFatherEntity(String fatherEntity) {
        super.fatherEntity = fatherEntity;
    }
}
