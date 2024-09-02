package com.fisa;

import com.fisa.dto.DataTypeDTO;
import com.fisa.utils.StringUtils;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RightWindowPanel {
    private final JPanel mainPanel;
    private final JBTable table;

    public RightWindowPanel() {
        mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // Datos para la tabla
        String[] columnNames = {"Campo", "Tipo de dato (Java)", "Tipo de dato (Sql)"};
        Object[][] data = {};

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        table = new JBTable(model);
        JBScrollPane scrollPane = new JBScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton executeButton = getAddButton(table, model);
        JButton deleteButton = getDeleteButton(table, model);
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 1) {
                        String selectedValue = (String) model.getValueAt(row, column);
                        model.setValueAt(StringUtils.INSTANCE.mappingDataTypes(selectedValue), row,column+1);

                    }
                }
            }
        });
        topPanel.add(executeButton);
        topPanel.add(deleteButton);
        mainPanel.add(topPanel, BorderLayout.SOUTH);
    }

    private static @NotNull JButton getAddButton(JBTable table, DefaultTableModel model) {
        JButton executeButton = new JButton("Add row");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] comboBoxItems = {"String", "Date", "Long"};
                JComboBox<String> comboBox = new JComboBox<>(comboBoxItems);
                TableColumn comboBoxColumn = table.getColumnModel().getColumn(1);
                comboBoxColumn.setCellEditor(new DefaultCellEditor(comboBox));
                model.addRow(new Object[]{"", "String", "VARCHAR(255)"});
            }
        });
        return executeButton;
    }

    private static @NotNull JButton getDeleteButton(JBTable table, DefaultTableModel model) {
        JButton deleteButton = new JButton("Delete row");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
                }
            }
        });
        return deleteButton;
    }

    public Optional<List<DataTypeDTO>> getDataTable(){
        List<DataTypeDTO> data = new ArrayList<>();
        if(isTableEmpty()){
            JOptionPane.showMessageDialog(null, "Tabla sin datos, por favor ingrese al menos una fila");
            return Optional.empty();
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int row = 0; row < model.getRowCount(); row++) {
            String nameEntity = "";
            String dataTypeJava = "";
            String dataTypeSql = "";
            for (int col = 0; col < model.getColumnCount(); col++) {
                String value = (String) model.getValueAt(row, col);
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(value)){
                    if(col == 0){
                        nameEntity = value;
                    }else if (col == 1){
                        dataTypeJava = value;
                    }else if (col == 2){
                        dataTypeSql = value;
                    }
                }else{
                    JOptionPane.showMessageDialog(null, String.format("Ingrese datos en la fila %s y columna %s", row, col) );
                    return Optional.of(new ArrayList<>());
                }
            }
            data.add(new DataTypeDTO(nameEntity, dataTypeJava, dataTypeSql));
        }
        return Optional.of(data);
    }

    public boolean isTableEmpty() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        return model.getRowCount() == 0;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
