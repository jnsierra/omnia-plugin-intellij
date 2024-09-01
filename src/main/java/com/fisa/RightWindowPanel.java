package com.fisa;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RightWindowPanel {
    private final JPanel mainPanel;
    public RightWindowPanel() {
        mainPanel = new JPanel(new BorderLayout());
        // Datos para la tabla
        String[] columnNames = {"Campo", "Tipo de dato (Java)", "Tipo de dato (Sql)"};
        Object[][] data = {
                {"Dato 1", "Dato 2", "Dato 3"},
                {"Dato 4", "Dato 5", "Dato 6"},
                {"Dato 7", "Dato 8", "Dato 9"}
        };

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JBTable table = new JBTable(model);
        JBScrollPane scrollPane = new JBScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton executeButton = new JButton("Add row");
        mainPanel.add(executeButton, BorderLayout.SOUTH);
    }

    public JPanel getMainPanel (){
        return mainPanel;
    }
}
