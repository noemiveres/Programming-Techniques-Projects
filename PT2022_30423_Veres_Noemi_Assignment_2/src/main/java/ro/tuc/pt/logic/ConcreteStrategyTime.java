package ro.tuc.pt.logic;

import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.task.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy{

    @Override
    public float addTask(List<Server> servers, Task t) {
        int ind = 0, minTime = 9999;
        for (int i = 0; i < servers.size(); i++){
            if(servers.get(i).getWaitingPeriod().get() < minTime){
                minTime = servers.get(i).getWaitingPeriod().get();
                ind = i;
            }
        }
        servers.get(ind).synchronizeTasks(t,1);
        return  servers.get(ind).getWaitingPeriod().get();
    }
}
