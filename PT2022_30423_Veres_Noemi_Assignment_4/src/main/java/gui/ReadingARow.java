package gui;

import javax.swing.*;

/** Responsible for reading a certain row from a table in the GUI.*/
public class ReadingARow {
    public JLabel label;
    public JTable table;
    public ReadingARow(JTable givenTable, String string){
        this.table = givenTable;
        ListSelectionModel selectionModel = this.table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        selectionModel.addListSelectionListener(new RowListener(this));
        this.label = new JLabel();
        this.label.setHorizontalAlignment(JLabel.CENTER);
        this.label.setBorder(BorderFactory.createTitledBorder(string));
    }
}
