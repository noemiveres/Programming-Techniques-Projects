package ro.tuc.pt.gui;

import ro.tuc.pt.logic.SelectionPolicy;
import ro.tuc.pt.logic.SimulationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private SimulationFrame simulationFrame;
    private boolean valid = false;
    private int N = 0, Q = 0, tMAX = 0, minimumArrivalTime = 0, maximumArrivalTime = 0,
            minimumServiceTime = 0, maximumServiceTime = 0;
    public Controller(SimulationFrame f) {
        this.simulationFrame = f;
    }
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == "VALIDATE") {
            try {
                N = Integer.parseInt(simulationFrame.getNumberOfClientsTextField().getText());
                Q = Integer.parseInt(simulationFrame.getNumberOfQueuesTextField().getText());
                tMAX = Integer.parseInt(simulationFrame.getSimulationIntervalTextField().getText());
                minimumArrivalTime = Integer.parseInt(simulationFrame.getMinArrivalTimeTextField().getText());
                maximumArrivalTime = Integer.parseInt(simulationFrame.getMaxArrivalTimeTextField().getText());
                minimumServiceTime = Integer.parseInt(simulationFrame.getMinServiceTimeTextField().getText());
                maximumServiceTime = Integer.parseInt(simulationFrame.getMaxServiceTimeTextField().getText());
                valid = true;

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Wrong input data!\nYou should insert integers.", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                valid = false;
            }

            if (N < 0 || Q < 0 || tMAX < 0 || minimumArrivalTime < 0 || maximumArrivalTime < 0
                    || minimumServiceTime < 0 || maximumServiceTime < 0) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Wrong input data!\nYou cannot insert negative numbers.", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                valid = false;
            }

            if (minimumServiceTime > maximumServiceTime || minimumArrivalTime > maximumArrivalTime) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Wrong input data!\nMinimum cannot be greater than maximum.", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                valid = false;
            }

            if(valid){
                JOptionPane.showMessageDialog(new JFrame(),
                        "The input data is valid.\nYou can start the simulation now.", "VALIDATED",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            //valid = true;     // uncomment this line if you don't want to read the input, just run the tests
        } else if (command == "START" && valid) {
            valid = false;
            SimulationManager simulation = new SimulationManager(N,Q,tMAX,minimumArrivalTime,maximumArrivalTime,
                    minimumServiceTime, maximumServiceTime);
//            SimulationManager simulation = new SimulationManager(4,2,60,2,30,
//                   2, 4);
//            SimulationManager simulation = new SimulationManager(50,5,60,2,40,
//                    1, 7);
//            SimulationManager simulation = new SimulationManager(1000,20,200,10,
//                    100, 3, 9);
            String strategy = String.valueOf(simulationFrame.getOperationsComboBox().getSelectedItem());
            switch (strategy) {
                case "Shortest time":
                    simulation.getScheduler().changeStrategy(SelectionPolicy.SHORTEST_TIME);
                    break;
                case "Shortest queue":
                    simulation.getScheduler().changeStrategy(SelectionPolicy.SHORTEST_QUEUE);
                    break;
            }
            SwingWorker<Void,Void> swingWorker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Thread t = new Thread(simulation);
                    t.start();
                    valid = false;
                    while(!simulation.isEnded()){
                        simulationFrame.getOutputValueLabel().setText("<html> " + simulation.getOutput() + " </html>");
                    }
                    simulationFrame.getAverageWaitingTimeValueLabel().setText(simulation.getGetAverageWaitingTime() + "");
                    simulationFrame.getAverageServiceTimeValueLabel().setText(simulation.getAverageServiceTime() + "");
                    simulationFrame.getPeakTimeValueLabel().setText(simulation.getPeakHour() + " ");
                    return null;
                }
            };
            swingWorker.execute();
        } else {
            JOptionPane.showMessageDialog(new JFrame(),
                    "You should validate your data first!", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
