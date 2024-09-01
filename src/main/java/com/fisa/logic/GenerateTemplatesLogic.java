package com.fisa.logic;

import com.fisa.utils.StringUtils;
import com.fisa.logic.impl.RepositoryTemplate;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

enum Fields {entity_name};

public class GenerateTemplatesLogic {
    private String entityName;
    private Fields field;
    private ToolWindow toolWindow;
    private final List<CreateFileTemplate> templatesList = new ArrayList<>();
    private final Predicate<String> isValidEntityName = entityName -> {
        if (entityName == null || entityName.isEmpty()) {
            field = Fields.entity_name;
            return false;
        }
        return true;
    };

    public GenerateTemplatesLogic() {
        templatesList.add(new RepositoryTemplate());
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setToolWindow(ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
    }

    public void executeProcess() {
        Optional.ofNullable(this.entityName)
                .filter(isValidEntityName)
                .filter(StringUtils.INSTANCE::isUpperCamelCase)
                .ifPresentOrElse(
                        s -> {
                            List<Boolean> resultList =templatesList.stream()
                                    .peek(it -> it.setEntityName(entityName))
                                    .map(CreateFileTemplate::createFileTemplate)
                                    .peek(it -> System.out.println(it.toString()))
                                    .map(WriteFileTemplates.getInstance()::write)
                                    .toList();
                            // Verifica si hay algún false
                            if (resultList.stream().anyMatch(result -> !result)) {
                                System.out.println("Hay al menos un false en la lista.");
                            } else if (resultList.stream().allMatch(result -> result)) {
                                System.out.println("Todos los valores son true.");
                            }

                        },
                        () -> JOptionPane.showMessageDialog(toolWindow.getComponent(), String.format("Field %s is required", field.toString()))
                );

    }

    private boolean validateInputs() {
        if (entityName == null || entityName.isEmpty()) {
            field = Fields.entity_name;
            return false;
        }
        return true;
    }
}

/**
 * boolean allValid = templatesList.stream().map(it -> {
 * it.setEntityName(this.entityName);
 * return it.createFileTemplate();
 * <p>
 * }).allMatch(valid -> {
 * if(!valid){
 * System.out.println("Alguno genero error");
 * return false;
 * }
 * System.out.println("Llego to true");
 * return valid;
 * });
 * System.out.println("Este es el allValid" + allValid);
 */