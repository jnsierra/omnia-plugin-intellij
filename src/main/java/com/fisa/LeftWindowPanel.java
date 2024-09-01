package com.fisa;

import com.fisa.logic.GenerateTemplatesLogic;
import com.fisa.templates.VelocityTemplateProcessor;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import org.apache.velocity.VelocityContext;
import javax.swing.*;
import java.awt.*;

public class LeftWindowPanel {
    private final ToolWindow toolWindow;
    private final JPanel mainPanel;
    private final JTextField inputField;
    private final GenerateTemplatesLogic generateTemplatesLogic = new GenerateTemplatesLogic();

    public LeftWindowPanel(ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JBLabel labelTop = new JBLabel("Inserte el nombre de la entidad");
        inputField = new JTextField(20);
        topPanel.add(labelTop);
        topPanel.add(inputField);

        JButton executeButton = new JButton("Ejecutar");

        // Configurar la acción del botón
        executeButton.addActionListener(e -> {
            String inputText = inputField.getText();
            generateTemplatesLogic.setEntityName(inputText);
            generateTemplatesLogic.setToolWindow(this.toolWindow);
            generateTemplatesLogic.executeProcess();

            // Procesar la plantilla con el texto ingresado
            //VelocityTemplateProcessor templateProcessor = new VelocityTemplateProcessor();
            //VelocityContext context = new VelocityContext();
            //context.put("name", inputText);
            //context.put("place", "IntelliJ IDEA");

            //String result = templateProcessor.processTemplate("templates/myTemplate.vm", context);

            //JOptionPane.showMessageDialog(mainPanel, result);
        });

        //Agregamos el textArea
        // Crear un área de texto (JTextArea) para mostrar logs
        JTextArea logArea = new JTextArea(10, 30);  // El número de filas y columnas puede ajustarse
        logArea.setEditable(false);  // No editable, solo para mostrar logs
        JBScrollPane logScrollPane = new JBScrollPane(logArea);

        // Agregar el campo de texto y el botón al panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(logScrollPane, BorderLayout.CENTER);
        mainPanel.add(executeButton, BorderLayout.SOUTH);
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
