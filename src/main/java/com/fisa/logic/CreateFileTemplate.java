package com.fisa.logic;

import com.fisa.dto.DataTypeDTO;
import com.fisa.dto.TemplateResultDto;

import java.util.List;

public interface CreateFileTemplate {
    TemplateResultDto createFileTemplate();
    void setEntityName(String entityName);
    void setFields(List<DataTypeDTO> fields);
    void setIsChildEntity(boolean isChildEntity);
    void setFatherEntity(String fatherEntity);

}
