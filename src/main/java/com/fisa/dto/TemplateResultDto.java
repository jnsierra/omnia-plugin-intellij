package com.fisa.dto;

public class TemplateResultDto {
    private String templateName;
    private String templateContent;
    private String templatePath;

    public TemplateResultDto(String templateName, String templateContent, String templatePath) {
        this.templateName = templateName;
        this.templateContent = templateContent;
        this.templatePath = templatePath;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public String toString() {
        return "TemplateResultDto{" +
                "templateName='" + templateName + '\'' +
                ", templateContent='" + templateContent + '\'' +
                ", templatePath='" + templatePath + '\'' +
                '}';
    }
}
