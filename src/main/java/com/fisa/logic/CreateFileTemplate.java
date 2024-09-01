package com.fisa.logic;

import com.fisa.dto.TemplateResultDto;

public interface CreateFileTemplate {
    TemplateResultDto createFileTemplate();
    void setEntityName(String entityName);
}
