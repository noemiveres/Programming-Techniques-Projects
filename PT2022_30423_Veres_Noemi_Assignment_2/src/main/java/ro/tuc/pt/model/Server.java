package ro.tuc.pt.model;

import ro.tuc.pt.model.task.Task;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private LinkedList<Task> tasks;
    private AtomicInteger waitingPeriod;    // client has to wait this amount when joining


    public Server() {
        tasks = new LinkedList<>();
        waitingPeriod = new AtomicInteger(0);
    }

    public synchronized int synchronizeTasks(Task task, int op){
        if (op == -1){
            tasks.remove(task);
        } else if(op == 0) {
            return tasks.size();
        } else if (op == 1){
            tasks.add(task);
            waitingPeriod.addAndGet(task.getServiceTime());
        }
        return -1;
    }
    @Override
    public void run() {
        while (true) {
            try {
                if(tasks.isEmpty()){
                    Thread.sleep(500);
                    continue;
                }
                Task taken = tasks.getFirst();
                boolean finished = false;
                while(!finished){
                    waitingPeriod.decrementAndGet();
                    taken.setServiceTime(taken.getServiceTime()-1);
                    if(taken.getServiceTime() < 1) {
                        finished = true;
                        synchronizeTasks(taken,-1);
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public LinkedList<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
}
