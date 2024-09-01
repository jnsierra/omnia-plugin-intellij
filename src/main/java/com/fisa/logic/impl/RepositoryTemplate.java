package com.fisa.logic.impl;

import com.fisa.dto.TemplateResultDto;
import com.fisa.logic.CreateFileTemplate;
import com.fisa.templates.VelocityTemplateProcessor;
import com.fisa.utils.PathsUtils;
import org.apache.velocity.VelocityContext;

public class RepositoryTemplate implements CreateFileTemplate {
    private String entityName;

    @Override
    public TemplateResultDto createFileTemplate() {
        VelocityTemplateProcessor templateProcessor = new VelocityTemplateProcessor();
        VelocityContext context = new VelocityContext();
        String PACKAGE_NAME = "package com.fisa.logic.impl;";
        context.put("PACKAGE_NAME", PACKAGE_NAME);
        context.put("NAME", entityName);
        String PATH_TEMPLATE = PathsUtils.PATH_TEMPLATE_REPOSITORY;
        String result = templateProcessor.processTemplate(PATH_TEMPLATE, context);
        return new TemplateResultDto(String.format("%sRepository.java", entityName), result, PathsUtils.PATH_TEMPLATE_TARGET_REPOSITORY);
    }

    @Override
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
