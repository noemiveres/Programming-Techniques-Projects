package ro.tuc.pt.logic;

import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.task.Task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private Strategy strategy;
    private float waitingSum;
    private String outputQueues;

    public Scheduler(int Q) {
        servers = new LinkedList<>();
        for (int i = 1; i <= Q; i++) {
            Server newServer = new Server();
            servers.add(newServer);
            Thread t = new Thread(newServer);
            t.start();
        }
        waitingSum = 0.0f;
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t) {
        waitingSum += strategy.addTask(servers, t);
    }

    public void printServers(BufferedWriter writer) throws IOException {
        outputQueues = "";
        for (int i = 0; i < servers.size(); i++) {
            writer.write("Queue" + i + ": ");
            outputQueues += "Queue" + i + ": ";
            for (Task task : servers.get(i).getTasks()) {
                writer.write("(" + task.getID() + "," + task.getArrivalTime()
                        + "," + task.getServiceTime() + "); ");
                outputQueues += "(" + task.getID() + "," + task.getArrivalTime()
                        + "," + task.getServiceTime() + "); ";
            }
            writer.write("\n");
            outputQueues += " <br> ";
        }
    }

    public boolean emptyServers(int Q) {
        for (int i = 0; i < Q; i++) {
            if (!servers.get(i).getTasks().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int totalServersSize(int Q) {
        int sum = 0;
        for (int i = 0; i < Q; i++) {
            sum += servers.get(i).synchronizeTasks(null, 0);
        }
        return sum;
    }

    public String getOutputQueues() {
        return outputQueues;
    }

    public float getWaitingSum() {
        return waitingSum;
    }
}
