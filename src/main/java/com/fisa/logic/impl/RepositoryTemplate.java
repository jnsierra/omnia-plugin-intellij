package com.fisa.logic.impl;

import com.fisa.dto.DataTypeDTO;
import com.fisa.dto.TemplateResultDto;
import com.fisa.logic.CreateFileTemplate;
import com.fisa.templates.VelocityTemplateProcessor;
import com.fisa.utils.PathsUtils;
import org.apache.velocity.VelocityContext;

import java.util.List;

public class RepositoryTemplate implements CreateFileTemplate {
    private String entityName;
    private List<DataTypeDTO> fields;

    @Override
    public TemplateResultDto createFileTemplate() {
        VelocityTemplateProcessor templateProcessor = new VelocityTemplateProcessor();
        VelocityContext context = new VelocityContext();
        String PACKAGE_NAME = "package com.fisa.logic.impl;";
        context.put("PACKAGE_NAME", PACKAGE_NAME);
        context.put("NAME", entityName);
        context.put("IMPORT_ENTITY", "import com.fisa.mobilemb.datos.entity." + entityName+ "Entity;");
        String PATH_TEMPLATE = PathsUtils.PATH_TEMPLATE_REPOSITORY;
        String result = templateProcessor.processTemplate(PATH_TEMPLATE, context);
        return new TemplateResultDto(String.format("%sRepository.java", entityName), result, PathsUtils.PATH_TEMPLATE_TARGET_REPOSITORY);
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
    }

    @Override
    public void setFatherEntity(String fatherEntity) {
    }
}
