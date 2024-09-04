package com.fisa;

import com.fisa.dto.DataTypeDTO;
import com.fisa.utils.StringUtils;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RightWindowPanel {
    private static final String[] COLUMN_NAMES = {"Campo", "Tipo de dato (Java)", "Tipo de dato (Sql)"};
    private static final String[] COMBO_BOX_ITEMS = {"String", "Date", "Long"};

    private final JPanel mainPanel;
    private final JBTable table;
    private final JBCheckBox isChildEntity;
    private final JBTextField fatherEntityField;

    public RightWindowPanel() {
        mainPanel = new JPanel(new BorderLayout());
        table = createTable();
        isChildEntity = new JBCheckBox("¿Es una entidad hija?");
        fatherEntityField = new JBTextField(20);


        mainPanel.add(new JBScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(createTopPanel(), BorderLayout.NORTH);
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private @NotNull JBTable createTable() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, COLUMN_NAMES);
        JBTable table = new JBTable(model);
        configureTableModel(model);
        configureComboBoxColumn(table);
        return table;
    }

    private void configureTableModel(@NotNull DefaultTableModel model) {
        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 1) {
                int row = e.getFirstRow();
                String selectedValue = (String) model.getValueAt(row, 1);
                model.setValueAt(StringUtils.INSTANCE.mappingDataTypes(selectedValue), row, 2);
            }
        });
    }

    private void configureComboBoxColumn(@NotNull JBTable table) {
        TableColumn comboBoxColumn = table.getColumnModel().getColumn(1);
        comboBoxColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(COMBO_BOX_ITEMS)));
    }

    private @NotNull JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JBLabel label = new JBLabel("Digite el nombre de la entidad:");

        label.setVisible(false);
        fatherEntityField.setVisible(false);

        isChildEntity.addActionListener(e -> {
            boolean selected = isChildEntity.isSelected();
            label.setVisible(selected);
            fatherEntityField.setVisible(selected);
            if (selected) {
                fatherEntityField.requestFocusInWindow();
            }
        });

        topPanel.add(label);
        topPanel.add(fatherEntityField);
        topPanel.add(isChildEntity);
        return topPanel;
    }

    public boolean isChildEntity(){
        return isChildEntity.isSelected();
    }

    public String getFatherEntityField(){
        return fatherEntityField.getText();
    }

    private @NotNull JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(createAddButton());
        bottomPanel.add(createDeleteButton());
        return bottomPanel;
    }

    private @NotNull JButton createAddButton() {
        JButton addButton = new JButton("Add row");
        addButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{"", "String", "VARCHAR(255)"});
        });
        return addButton;
    }

    private @NotNull JButton createDeleteButton() {
        JButton deleteButton = new JButton("Delete row");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            }
        });
        return deleteButton;
    }

    public Optional<List<DataTypeDTO>> getDataTable() {
        if (isTableEmpty()) {
            JOptionPane.showMessageDialog(null, "Tabla sin datos, por favor ingrese al menos una fila");
            return Optional.empty();
        }

        List<DataTypeDTO> data = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int row = 0; row < model.getRowCount(); row++) {
            String nameEntity = getValueAt(model, row, 0);
            String dataTypeJava = getValueAt(model, row, 1);
            String dataTypeSql = getValueAt(model, row, 2);

            if (nameEntity.isEmpty() || dataTypeJava.isEmpty() || dataTypeSql.isEmpty()) {
                JOptionPane.showMessageDialog(null, String.format("Ingrese datos en la fila %d", row + 1));
                return Optional.empty();
            }

            data.add(new DataTypeDTO(nameEntity, dataTypeJava, dataTypeSql));
        }

        return Optional.of(data);
    }

    private @NotNull String getValueAt(@NotNull DefaultTableModel model, int row, int col) {
        Object value = model.getValueAt(row, col);
        return value != null ? value.toString().trim() : "";
    }

    public boolean isTableEmpty() {
        return table.getRowCount() == 0;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
