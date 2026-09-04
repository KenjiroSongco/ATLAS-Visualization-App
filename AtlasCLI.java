import java.util.Scanner;
public class AtlasCLI {

    private Scanner scanner;
    private TrafficLightAlgorithm algo;
    public AtlasCLI() {
        this.scanner = new Scanner(System.in);
        this.algo = new TrafficLightAlgorithm(120.0,4.0,40.0,0.5,10.0,60.0); //mga times to
    }
    public static void main(String[] args) {
        AtlasCLI cli = new AtlasCLI();
        cli.start();
    }
    public void start() {
        System.out.println(" ATLAS - Advancing Traffic Lights Algorithm System");
        System.out.println("----------------------------------------------------");
        int numberOfPhases = readInt("Enter number of phases (approaches) at this intersection: ");
        while (numberOfPhases <= 0) {
            System.out.println("Number of phases must be at least 1.");
            numberOfPhases = readInt("Enter number of phases (approaches) at this intersection: ");
        }
        Phase[] phases = new Phase[numberOfPhases];
        for (int i = 0; i < numberOfPhases; i++) {
            String defaultName = "Phase " + (i + 1);
            phases[i] = gatherPhaseInput(defaultName);
        }
        double[] greenTimes = algo.calculateGreenTimes(phases);
        displayResults(phases, greenTimes);
        scanner.close();
    }
    private Phase gatherPhaseInput(String phaseName) {
        System.out.println("\n " + phaseName + " ");

        System.out.print("Direction/label for this phase: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = phaseName;
        }
        int queueLength = readInt("Queue length (number of waiting vehicles): ");
        double averageSpeed = readDouble("Average approach speed (km/h): ");
        return new Phase(name, queueLength, averageSpeed);
    }
    private void displayResults(Phase[] phases, double[] greenTimes) {
        System.out.println(" ATLAS RESULTS - Recommended Green Times");
        System.out.println("");
        double totalGreen = 0.0;
        for (int i = 0; i < phases.length; i++) {
            System.out.printf("%-15s | Queue: %-4d | Speed: %-6.1f km/h | Green Time: %.1f sec%n",phases[i].getName(),phases[i].getQueueLength(),phases[i].getAverageSpeed(),greenTimes[i]);
            totalGreen += greenTimes[i];
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("Total allocated green time: %.1f sec%n", totalGreen);
    }
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}