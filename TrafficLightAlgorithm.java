public class TrafficLightAlgorithm {
    //System Configuration (May be changed by the user for future versions)

    //Time set in seconds (sec)
    private double cycleLength = 120.0; //Total time for one full round of green lights  
    private double lostTimePerPhase = 5.0; //Time lost to yellow/red clearance lights
    private double minGreen = 15.0; //Minimum time allowed in green light
    private double maxGreen = 80.0; //Maximum time allowed in green light
    //Speed set in kilometers per hour (km/h)
    private double optimalSpeed = 40.0; //The baseline speed expected cars to move
    private double alpha = 0.8;//Congestion penalty (the higher, the more help for slow lanes)
    

    //Takes lane data (phases) and calculates the ideal green light duration
    public double[] calculateGreenTimes(Phase[] phases){
        int n = phases.length;
        double availableGreenTime = getAvailableGreenTime(n);

        //Loop thriough each lane to calculate their priority score and add to the total pool
        double totalPriority = 0.0;
        double[] priorityScores = new double[n]; 

        for(int i=0;i<n;i++){
            priorityScores[i] = phases[i].calculatePriorityScore(optimalSpeed,alpha);
            totalPriority += priorityScores[i];
        }

        double[] finalGreenTimes = new double[n]; //The array needed to return

        //If streets are completely empty, split the time evenly to avoid division by zero errors
        if (totalPriority == 0.0) {
            for(int i=0;i<n;i++){
                finalGreenTimes[i] = (availableGreenTime/n);
            }
        } else {
            //Give each lane a fair distribution of time, while using min and max to stay within allowed green light bounds
            for(int i=0;i<n;i++){
                double  x = availableGreenTime*(priorityScores[i]/totalPriority);
                finalGreenTimes[i] = Math.max(minGreen, Math.min(x,maxGreen));
            }
        }
        return finalGreenTimes; 
    }

    //Subtracts the yellow/clearance lights to find the actual go time available to distribute
    private double getAvailableGreenTime(int numberOfPhases){
        return cycleLength - (numberOfPhases*lostTimePerPhase);
    }
}
