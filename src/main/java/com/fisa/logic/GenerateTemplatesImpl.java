package com.fisa.logic;

import com.fisa.logic.impl.*;
import com.fisa.dto.DataTypeDTO;
import com.fisa.logic.java.JavaClassModifierEntity;
import com.fisa.utils.StringUtils;
import com.intellij.openapi.wm.ToolWindow;
import com.fisa.logic.xml.AddChangelogXml;

import javax.swing.*;
import java.util.List;
import java.util.function.Predicate;

enum FieldsEntity {entity_name};

public class GenerateTemplatesImpl {

    private final String entityName;
    private final String plurarEntity;
    private final FieldsEntity field = FieldsEntity.entity_name;
    private final ToolWindow toolWindow;
    private final List<DataTypeDTO> fields;
    private final List<CreateFileTemplate> templatesList = List.of(
            new RepositoryTemplate(),
            new DtoTemplate(),
            new ServiceTemplate(),
            new ServiceImplTemplate(),
            new EntityTemplate(),
            new ControllerTemplate(),
            new LiquidbaseTemplate()
    );
    private final AddChangelogXml changelogXml = new AddChangelogXml();

    private final Predicate<String> isValidEntityName = entityName -> entityName != null && !entityName.isEmpty();
    //Campos crear las relaciones
    private final boolean isChildEntity;
    private final String fatherEntity;
    private final JavaClassModifierEntity javaClassModifierEntity;


    public GenerateTemplatesImpl(String entityName, ToolWindow toolWindow, List<DataTypeDTO> fields, boolean isChildEntity, String fatherEntity, String plurarEntity) {
        this.entityName = entityName;
        this.toolWindow = toolWindow;
        this.fields = fields;
        this.isChildEntity = isChildEntity;
        this.fatherEntity = fatherEntity;
        this.plurarEntity = plurarEntity;
        this.javaClassModifierEntity = new JavaClassModifierEntity( entityName, plurarEntity ,fatherEntity);
    }

    public void executeProcess() {
        if (isValidEntityName.and(StringUtils.INSTANCE::isUpperCamelCase).test(entityName)) {
            /*
            boolean allSuccess = templatesList.stream()
                    .peek(it -> it.setEntityName(entityName))
                    .peek(it -> it.setFields(fields))
                    .peek(it -> it.setFatherEntity(fatherEntity))
                    .peek(it -> it.setIsChildEntity(isChildEntity))
                    .map(CreateFileTemplate::createFileTemplate)
                    .allMatch(WriteFileTemplates.getInstance()::write);

            if (allSuccess) {
                handleChangelog();
            } else {
                log("There was a problem creating one or more templates.");
            }
            */

            //Validamos si la entidad es hija para añadir las variables de la relacion
            if(isChildEntity){
                if(javaClassModifierEntity.addFatherParameter()){
                    log("Añadio lo necesario para entidad padre");
                }else{
                    log("Error al añadir atributos en entidad padre");
                }
            }
        } else {
            showErrorDialog();
        }
    }

    private void handleChangelog() {
        boolean response = changelogXml.addNewTableChangeLog(String.format("db/%s-table.xml", StringUtils.toKebabCase(entityName)));
        if (response) {
            log("Changelog entry added successfully.");
        } else {
            log("Failed to add changelog entry.");
        }
    }
    private void showErrorDialog() {
        JOptionPane.showMessageDialog(toolWindow.getComponent(), String.format("Field %s is required", field.toString()));
    }
    private void log(String message) {
        System.out.println(message);
    }
}