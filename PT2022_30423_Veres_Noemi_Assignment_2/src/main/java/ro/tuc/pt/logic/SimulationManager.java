package ro.tuc.pt.logic;

import ro.tuc.pt.gui.SimulationFrame;
import ro.tuc.pt.model.task.RandomTask;
import ro.tuc.pt.model.task.Task;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SimulationManager implements Runnable {
    private int numberOfClients;
    private int numberOfServers;
    private int timeLimit;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minProcessingTime;
    private int maxProcessingTime;
    private float averageWaitingTime;
    private float averageServiceTime;
    private int peakHour;
    private String output;
    private boolean ended;
    private BufferedWriter writer;
    private Scheduler scheduler;
    private List<Task> generatedTasks;

    public SimulationManager(int N, int Q, int tMAX, int minArrivalTime, int maxArrivalTime, int minProcessingTime,
                             int maxProcessingTime) {
        this.numberOfClients = N;
        this.numberOfServers = Q;
        this.timeLimit = tMAX;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.averageWaitingTime = 0.0f;
        this.averageServiceTime = 0.0f;
        this.peakHour = 0;
        this.scheduler = new Scheduler(numberOfServers);     // numberOfServers servers are created in scheduler
        generateNRandomTasks();
    }

    private void generateNRandomTasks() {
        generatedTasks = new LinkedList<>();
        for (int i = 0; i < numberOfClients; i++) {
            generatedTasks.add(RandomTask.randomTask(i, minArrivalTime, maxArrivalTime,
                    minProcessingTime, maxProcessingTime));
            averageServiceTime += generatedTasks.get(i).getServiceTime();
        }
        averageServiceTime /= numberOfClients;
        generatedTasks.sort(new Task.OrderTasks());
    }

    @Override
    public void run() {
        ended = false;
        int currentTime = 0, endSoon = 3, maxSum = 0;
        openWriter("Log.txt");
        try {
            while (currentTime <= timeLimit && endSoon != 1) {
                for (int i = 0; i < generatedTasks.size(); i++) {
                    if (generatedTasks.get(i).getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(generatedTasks.get(i));
                        generatedTasks.remove(i);
                        i--;    // not to jump over
                    }
                }
                printTimeAndWaitingClients(currentTime);
                if (scheduler.totalServersSize(numberOfServers) > maxSum) {
                    maxSum = scheduler.totalServersSize(numberOfServers);
                    peakHour = currentTime;
                }
                currentTime++;
                scheduler.printServers(writer);
                output += scheduler.getOutputQueues();
                writer.write("\n");
                Thread.sleep(1000);
                if ((scheduler.emptyServers(numberOfServers) && generatedTasks.size() == 0)) {
                    endSoon--;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        ended = true;
        closeWriter();
    }

    public void openWriter(String fileName){
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter(){
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printTimeAndWaitingClients(int currentTime) throws IOException {
        writer.write("Time " + currentTime + "\nWaiting clients: ");
        output = "Time " + currentTime + " <br> Waiting clients: ";
        for (int i = 0; i < generatedTasks.size(); i++) {
            writer.write("(" + generatedTasks.get(i).getID() + "," +
                    generatedTasks.get(i).getArrivalTime()
                    + "," + generatedTasks.get(i).getServiceTime() + "); ");
            output += "(" + generatedTasks.get(i).getID() + "," +
                    generatedTasks.get(i).getArrivalTime()
                    + "," + generatedTasks.get(i).getServiceTime() + "); ";
        }
        writer.write("\n");
        output += " <br> ";
    }

    public float getGetAverageWaitingTime() {
        averageWaitingTime = scheduler.getWaitingSum() / numberOfClients;
        return averageWaitingTime;
    }

    public float getAverageServiceTime() {
        return averageServiceTime;
    }

    public int getPeakHour() {
        return peakHour;
    }

    public String getOutput() {
        return output;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public boolean isEnded() {
        return ended;
    }

    public static void main(String[] args) {
        JFrame frame = new SimulationFrame("Queues management system");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
