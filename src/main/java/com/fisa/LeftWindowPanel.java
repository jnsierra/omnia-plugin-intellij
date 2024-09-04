package com.fisa;

import com.fisa.dto.DataTypeDTO;
import com.fisa.logic.GenerateTemplatesImpl;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeftWindowPanel {
    private final ToolWindow toolWindow;
    private final JPanel mainPanel;
    private final JTextField inputField;
    private final JTextField inputFieldPlural;
    private RightWindowPanel rightWindowPanel;

    public LeftWindowPanel(ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        this.mainPanel = new JPanel(new BorderLayout());
        this.inputField = new JTextField(10);
        this.inputFieldPlural = new JTextField(10);

        // Configurar el panel superior
        JPanel topPanel = createTopPanel();

        // Crear el área de log
        JTextArea logArea = createLogArea();

        // Crear y configurar el botón de ejecutar
        JButton executeButton = createExecuteButton();

        // Agregar componentes al panel principal
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JBScrollPane(logArea), BorderLayout.CENTER);
        mainPanel.add(executeButton, BorderLayout.SOUTH);
    }

    private @NotNull JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JBLabel labelTop = new JBLabel("Inserte el nombre de la entidad");
        JBLabel labelTopPlural = new JBLabel("Plural");
        topPanel.add(labelTop);
        topPanel.add(inputField);
        topPanel.add(labelTopPlural);
        topPanel.add(inputFieldPlural);
        return topPanel;
    }

    private @NotNull JTextArea createLogArea() {
        JTextArea logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        return logArea;
    }

    private @NotNull JButton createExecuteButton() {
        JButton executeButton = new JButton("Ejecutar");
        executeButton.addActionListener(e -> executeAction());
        return executeButton;
    }

    private void executeAction() {
        if (validateInputEntity() && validateRightPanelData()) {
            String inputText = inputField.getText().trim();
            String pluralText = inputFieldPlural.getText().trim();
            List<DataTypeDTO> dataTable = rightWindowPanel.getDataTable().orElseThrow();
            String father = rightWindowPanel.isChildEntity() ? rightWindowPanel.getFatherEntityField() : null;
            GenerateTemplatesImpl generateTemplatesLogic = new GenerateTemplatesImpl(inputText, toolWindow, dataTable, rightWindowPanel.isChildEntity(), father, pluralText );
            generateTemplatesLogic.executeProcess();
        }
    }

    private boolean validateInputEntity() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            showMessageDialog("Por favor ingrese el nombre de la entidad");
            return false;
        }
        return true;
    }

    private boolean validateRightPanelData() {
        if (rightWindowPanel == null || rightWindowPanel.getDataTable().orElse(List.of()).isEmpty()) {
            showMessageDialog("Por favor asegúrese de que la tabla tiene datos.");
            return false;
        }
        return true;
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(toolWindow.getComponent(), message);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setRightWindowPanel(RightWindowPanel rightWindowPanel) {
        this.rightWindowPanel = rightWindowPanel;
    }
}