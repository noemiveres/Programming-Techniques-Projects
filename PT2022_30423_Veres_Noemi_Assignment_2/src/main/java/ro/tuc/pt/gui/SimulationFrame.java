package ro.tuc.pt.gui;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private JPanel contentPane;
    private JPanel inputPanel;
    private JLabel numberOfClientsLabel;
    private JTextField numberOfClientsTextField;
    private JLabel numberOfQueuesLabel;
    private JTextField numberOfQueuesTextField;
    private JLabel simulationIntervalLabel;
    private JTextField simulationIntervalTextField;
    private JLabel minArrivalTimeLabel;
    private JTextField minArrivalTimeTextField;
    private JLabel maxArrivalTimeLabel;
    private JTextField maxArrivalTimeTextField;
    private JLabel minServiceTimeLabel;
    private JTextField minServiceTimeTextField;
    private JLabel maxServiceTimeLabel;
    private JTextField maxServiceTimeTextField;
    private JLabel strategyLabel;
    private JComboBox strategyComboBox;
    private JButton validateButton;
    private JButton startButton;
    private JPanel logOfEvents;
    private JPanel statistics;
    private JLabel emptyLabel1;
    private JLabel outputValueLabel;
    private JLabel averageWaitingTimeLabel;
    private JLabel averageWaitingTimeValueLabel;
    private JLabel averageServiceTimeLabel;
    private JLabel averageServiceTimeValueLabel;
    private JLabel peakTimeLabel;
    private JLabel peakTimeValueLabel;

    Controller controller = new Controller(this);

    public SimulationFrame(String name) {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(3, 2));
        this.prepareInputPanel();
        this.prepareLogOfEvents();
        this.prepareStatistics();
        this.setContentPane(this.contentPane);
    }

    private void prepareInputPanel() {
        this.inputPanel = new JPanel();
        this.inputPanel.setLayout(new GridLayout(5, 2));
        this.numberOfClientsLabel = new JLabel("Number of clients", JLabel.CENTER);
        this.inputPanel.add(this.numberOfClientsLabel);
        this.numberOfClientsTextField = new JTextField();
        this.inputPanel.add(this.numberOfClientsTextField);
        this.numberOfQueuesLabel = new JLabel("Number of queues", JLabel.CENTER);
        this.inputPanel.add(numberOfQueuesLabel);
        this.numberOfQueuesTextField = new JTextField();
        this.inputPanel.add(numberOfQueuesTextField);
        this.minArrivalTimeLabel = new JLabel("Minimum arrival time", JLabel.CENTER);
        this.inputPanel.add(minArrivalTimeLabel);
        this.minArrivalTimeTextField = new JTextField();
        this.inputPanel.add(minArrivalTimeTextField);
        this.maxArrivalTimeLabel = new JLabel("Maximum arrival time", JLabel.CENTER);
        this.inputPanel.add(maxArrivalTimeLabel);
        this.maxArrivalTimeTextField = new JTextField();
        this.inputPanel.add(maxArrivalTimeTextField);
        this.minServiceTimeLabel = new JLabel("Minimum service time", JLabel.CENTER);
        this.inputPanel.add(minServiceTimeLabel);
        this.minServiceTimeTextField = new JTextField();
        this.inputPanel.add(minServiceTimeTextField);
        this.maxServiceTimeLabel = new JLabel("Maximum service time", JLabel.CENTER);
        this.inputPanel.add(maxServiceTimeLabel);
        this.maxServiceTimeTextField = new JTextField();
        this.inputPanel.add(maxServiceTimeTextField);
        this.simulationIntervalLabel = new JLabel("Simulation interval (max time)", JLabel.CENTER);
        this.inputPanel.add(simulationIntervalLabel);
        this.simulationIntervalTextField = new JTextField();
        this.inputPanel.add(simulationIntervalTextField);
        this.strategyLabel = new JLabel("Select strategy", JLabel.CENTER);
        this.inputPanel.add(this.strategyLabel);
        String[] strategy = new String[]{"Shortest time", "Shortest queue"};
        this.strategyComboBox = new JComboBox(strategy);
        this.inputPanel.add(strategyComboBox);
        this.validateButton = new JButton("Validate");
        this.validateButton.setActionCommand("VALIDATE");
        this.validateButton.addActionListener(this.controller);
        this.inputPanel.add(this.validateButton);
        this.emptyLabel1 = new JLabel("", JLabel.CENTER);
        this.inputPanel.add(this.emptyLabel1);
        this.startButton = new JButton("Start");
        this.startButton.setActionCommand("START");
        this.startButton.addActionListener(this.controller);
        this.inputPanel.add(this.startButton);
        this.contentPane.add(this.inputPanel);
    }
    
    private void prepareLogOfEvents() {
        this.logOfEvents = new JPanel();
        this.logOfEvents.setLayout(new GridLayout(1,1));
        this.outputValueLabel = new JLabel("", JLabel.CENTER);
        this.logOfEvents.add(this.outputValueLabel);
        this.contentPane.add(this.logOfEvents);
    }

    private void prepareStatistics() {
        this.statistics = new JPanel();
        this.statistics.setLayout(new GridLayout(3,2));
        this.averageWaitingTimeLabel = new JLabel("   Average waiting time", JLabel.LEFT);
        this.averageWaitingTimeValueLabel = new JLabel("", JLabel.LEFT);
        this.averageServiceTimeLabel = new JLabel("   Average service time", JLabel.LEFT);
        this.averageServiceTimeValueLabel = new JLabel("", JLabel.LEFT);
        this.peakTimeLabel = new JLabel("   Peak time", JLabel.LEFT);
        this.peakTimeValueLabel = new JLabel("", JLabel.LEFT);
        this.statistics.add(this.averageWaitingTimeLabel);
        this.statistics.add(this.averageWaitingTimeValueLabel);
        this.statistics.add(this.averageServiceTimeLabel);
        this.statistics.add(this.averageServiceTimeValueLabel);
        this.statistics.add(this.peakTimeLabel);
        this.statistics.add(this.peakTimeValueLabel);
        this.contentPane.add(this.statistics);
    }

    public JComboBox getOperationsComboBox() {
        return strategyComboBox;
    }

    public JLabel getAverageWaitingTimeValueLabel() {return averageWaitingTimeValueLabel;}

    public JLabel getAverageServiceTimeValueLabel() {return averageServiceTimeValueLabel;}

    public JLabel getPeakTimeValueLabel() {return  peakTimeValueLabel;}

    public JLabel getOutputValueLabel() {return outputValueLabel;}

    public JTextField getNumberOfClientsTextField() {
        return numberOfClientsTextField;
    }

    public JTextField getNumberOfQueuesTextField() { return numberOfQueuesTextField;    }

    public JTextField getSimulationIntervalTextField() {
        return simulationIntervalTextField;
    }

    public JTextField getMinArrivalTimeTextField() {
        return minArrivalTimeTextField;
    }
    public JTextField getMaxArrivalTimeTextField() {
        return maxArrivalTimeTextField;
    }
    public JTextField getMinServiceTimeTextField() {
        return minServiceTimeTextField;
    }
    public JTextField getMaxServiceTimeTextField() {
        return maxServiceTimeTextField;
    }
}
