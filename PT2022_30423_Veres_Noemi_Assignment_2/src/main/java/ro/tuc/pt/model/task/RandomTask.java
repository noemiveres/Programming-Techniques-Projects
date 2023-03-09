package ro.tuc.pt.model.task;

import java.util.Random;

public class RandomTask {
    static Random random = new Random();

    public static Task randomTask(int id, int minArrivalTime, int maxArrivalTime,
                                  int minServiceTime, int maxServiceTime) {
        int arrivalTime = getIntInRange(minArrivalTime, maxArrivalTime);
        int serviceTime = getIntInRange(minServiceTime, maxServiceTime);
        return new Task(id, arrivalTime, serviceTime);
    }

    private static int getIntInRange(int min, int max) {
        int range = max - min + 1;
        return random.nextInt(range) + min;
    }

}
