package com.fisa.logic.impl;

import com.fisa.dto.DataTypeDTO;
import com.fisa.dto.TemplateResultDto;
import com.fisa.logic.CreateFileTemplate;
import com.fisa.utils.PathsUtils;

import java.util.List;

public class ServiceImplTemplate extends TemplateAbstract implements CreateFileTemplate {
    private String entityName;
    private List<DataTypeDTO> fields;
    public ServiceImplTemplate(){
        super();
    }
    @Override
    public TemplateResultDto createFileTemplate() {
        addParams();
        generateTemplate(PathsUtils.PATH_TEMPLATE_SERVICE_IMPL);
        fileName = String.format("%sServiceImpl.java", entityName);
        return generateDto(PathsUtils.PATH_TEMPLATE_TARGET_SERVICE_IMPL);
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
        context.put("PACKAGE_NAME", "package com.fisa.mobilemb.service.impl;");
        context.put("NAME", entityName);
        context.put("nameService", entityName.substring(0,1).toLowerCase() + entityName.substring(1));
        context.put("IMPORT_REPOSITORY", "import com.fisa.mobilemb.datos.repository." + entityName + "Repository;");
        context.put("IMPORT_SERVICE", "import com.fisa.mobilemb.service." + entityName + "Service;");
        context.put("IMPORT_ENTITY", "import com.fisa.mobilemb.datos.entity." + entityName+ "Entity;");
    }
}
