package com.fisa.logic.impl;

import com.fisa.dto.DataTypeDTO;
import com.fisa.dto.TemplateResultDto;
import com.fisa.logic.CreateFileTemplate;
import com.fisa.utils.PathsUtils;

import java.util.List;

public class ServiceTemplate  extends TemplateAbstract implements CreateFileTemplate {
    private String entityName;
    private List<DataTypeDTO> fields;

    public ServiceTemplate() {
        super();
    }

    @Override
    public TemplateResultDto createFileTemplate() {
        addParams();
        generateTemplate(PathsUtils.PATH_TEMPLATE_SERVICE);
        fileName = String.format("%sService.java", entityName);
        return generateDto(PathsUtils.PATH_TEMPLATE_TARGET_SERVICE);
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
        context.put("PACKAGE_NAME", "package com.fisa.mobilemb.service;");
        context.put("NAME", entityName);
        context.put("IMPORT_ENTITY", "import com.fisa.mobilemb.datos.entity." + entityName+ "Entity;");
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
