package ro.tuc.pt.logic;

import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.task.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public float addTask(List<Server> servers, Task t) {
        int minNoOfTasks = 9999, ind = 0;
        for (int i = 0; i < servers.size(); i++){
            if(servers.get(i).getTasks().size() < minNoOfTasks){
                minNoOfTasks = servers.get(i).getTasks().size();
                ind = i;
            }
        }
        servers.get(ind).synchronizeTasks(t,1);
        return  servers.get(ind).getWaitingPeriod().get();
    }
}
