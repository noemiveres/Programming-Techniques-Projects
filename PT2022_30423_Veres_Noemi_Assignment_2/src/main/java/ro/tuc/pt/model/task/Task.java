package ro.tuc.pt.model.task;

import java.util.Comparator;

public class Task {
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public static class OrderTasks implements Comparator<Task> {
        @Override
        public int compare(Task t1, Task t2){
            return Integer.compare(t1.getArrivalTime(), t2.getArrivalTime());
        }
    }


}
