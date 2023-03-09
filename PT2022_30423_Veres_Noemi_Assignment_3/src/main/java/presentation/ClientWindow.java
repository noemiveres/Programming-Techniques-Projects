package presentation;

import bll.ClientBLL;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * A special window which contains the tables, buttons and labels needed to make operations on clients.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class ClientWindow extends JFrame{
    private JPanel contentPane, operationsPanel, clientsPanel;
    private JLabel operationLabel;
    private JButton addClientButton, editClientButton, deleteClientButton, viewAllClientsButton;
    private JTable clientsTable, addClientTable, editClientTable, deleteClientTable;
    ClientBLL clientBLL = new ClientBLL();
    Controller controller = new Controller(this);

    public ClientWindow(String name){
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(950,700);
        this.setLocation(330,0);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(2,1));
        this.setVisible(true);
        this.prepareOperationsPanel();
        this.prepareClientsPanel();
        this.setContentPane(this.contentPane);
        controller.updateClientTable();
    }

    private void prepareClientsPanel() {
        this.clientsPanel = new JPanel();
        this.clientsPanel.setLayout(new GridLayout(1,1));
        this.clientsTable = new JTable();
        JTableHeader header = clientsTable.getTableHeader();
        header.setBackground(Color.PINK);
        JScrollPane sp = new JScrollPane(clientsTable);
        this.clientsPanel.add(sp);
        this.contentPane.add(clientsPanel);
    }

    private void prepareOperationsPanel(){
        this.operationsPanel = new JPanel();
        this.operationsPanel.setLayout(new GridLayout(8,1));
        this.operationLabel = new JLabel("Select the operation: ", JLabel.CENTER);
        this.operationsPanel.add(this.operationLabel);
        this.addClientButton = new JButton("Add new client");
        this.addClientButton.setActionCommand("Add new client");
        this.addClientButton.addActionListener(this.controller);
        this.operationsPanel.add(this.addClientButton);
        String data1[][] = {{"", "", "", "", "", ""}};
        String column1[] = {"userName", "firstName", "lastName", "address", "phoneNumber",
                "emailAddress"};
        this.addClientTable = new JTable(data1,column1);
        JScrollPane sp1 = new JScrollPane(addClientTable);
        this.operationsPanel.add(sp1);
        this.editClientButton = new JButton("Edit client");
        this.editClientButton.setActionCommand("Edit client");
        this.editClientButton.addActionListener(this.controller);
        this.operationsPanel.add(this.editClientButton);
        String data2[][] = {{"", "", "", "", "", "", ""}};
        String column2[] = {"id","userName", "firstName", "lastName", "address", "phoneNumber",
                "emailAddress"};
        this.editClientTable = new JTable(data2,column2);
        JScrollPane sp2 = new JScrollPane(editClientTable);
        this.operationsPanel.add(sp2);
        this.deleteClientButton = new JButton("Delete client");
        this.deleteClientButton.setActionCommand("Delete client");
        this.deleteClientButton.addActionListener(this.controller);
        this.operationsPanel.add(this.deleteClientButton);
        String data3[][] = {{""}};
        String column3[] = {"id"};
        this.deleteClientTable = new JTable(data3,column3);
        JScrollPane sp3 = new JScrollPane(deleteClientTable);
        this.operationsPanel.add(sp3);
        this.viewAllClientsButton = new JButton("View all clients");
        this.viewAllClientsButton.setActionCommand("View all clients");
        this.viewAllClientsButton.addActionListener(this.controller);
        this.operationsPanel.add(this.viewAllClientsButton);
        this.contentPane.add(operationsPanel);
    }

    public JTable getClientsTable() {
        return clientsTable;
    }

    public JTable getDeleteClientTable() {
        return deleteClientTable;
    }

    public JTable getAddClientTable() {
        return addClientTable;
    }

    public JTable getEditClientTable() {
        return editClientTable;
    }
}
