package ro.tuc.pt.logic;

import ro.tuc.pt.model.Server;
import ro.tuc.pt.model.task.Task;
import java.util.List;

public interface Strategy {
    float addTask(List<Server> servers, Task t);
}

