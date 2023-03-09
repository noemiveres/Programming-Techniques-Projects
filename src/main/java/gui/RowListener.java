package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/** Represents the selection listener of a table needed to select the products for ordering. */
public class RowListener implements ListSelectionListener {
    ReadingARow readRow;
    JTable table;

    public RowListener(ReadingARow rar)
    {
        readRow = rar;
        table = readRow.table;
    }

    public void valueChanged(ListSelectionEvent e)
    {
        if(!e.getValueIsAdjusting())
        {
            ListSelectionModel model = table.getSelectionModel();
            int lead = model.getLeadSelectionIndex();
            if(lead == -1){
                return;
            }
            displayRowValues(lead);
        }
    }

    private void displayRowValues(int rowIndex)
    {
        int columns = table.getColumnCount();
        String s = "";
        for(int col = 0; col < columns; col++)
        {
            Object o = table.getValueAt(rowIndex, col);
            s += o.toString();
            if(col < columns - 1)
                s += ", ";
        }
        readRow.label.setText(s);
    }

}
