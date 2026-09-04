public class Phase {
    private String name; //Label for the lane (e.g., "Phase 1")
    private int queueLength; //Number of cars waiting at the light
    private double averageSpeed; //Observed traffic speed in km/h

    public Phase(String name, int queueLength, double averageSpeed) {
        this.name = name; //Store the lane label
        this.queueLength = queueLength; //Store the queue size
        this.averageSpeed = averageSpeed; //Store the speed
    }

    public String getName() {
        return name; //Expose the lane label
    }

    public int getQueueLength() {
        return queueLength; //Expose the queue size
    }

    public double getAverageSpeed() {
        return averageSpeed; //Expose the speed
    }

    //Congestion penalty: alpha weights queue length (more cars = higher priority),
    //while (1 - alpha) rewards lanes moving closer to the optimal speed.
    public double calculatePriorityScore(double optimalSpeed, double alpha) {
        double speedFactor = Math.min(optimalSpeed, averageSpeed) / optimalSpeed; //How close this lane is to the ideal speed
        return alpha * queueLength + (1 - alpha) * speedFactor; //Blend congestion and speed into one score
    }
}
