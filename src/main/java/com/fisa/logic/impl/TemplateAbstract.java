package com.fisa.logic.impl;

import com.fisa.dto.TemplateResultDto;
import com.fisa.templates.VelocityTemplateProcessor;
import org.apache.velocity.VelocityContext;

abstract class TemplateAbstract {
    protected VelocityTemplateProcessor templateProcessor;
    protected VelocityContext context;
    protected String resultTemplate;
    protected String fileName;
    protected boolean isChildEntity;
    protected String fatherEntity;

    public TemplateAbstract(){
        templateProcessor = new VelocityTemplateProcessor();
        context = new VelocityContext();
    }

    protected abstract void addParams();

    protected void generateTemplate(String PATH_TEMPLATE){
        resultTemplate = templateProcessor.processTemplate(PATH_TEMPLATE, context);;
    }
    protected TemplateResultDto generateDto(String templatePath){
        return new TemplateResultDto(fileName, resultTemplate, templatePath);
    }

}
