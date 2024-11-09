package Assignment;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loyalty {

    private Map<String, Double> tierMultipliers; // Mapping between tier and multiplier
    private String nextTiers;

    public Loyalty() {
        //initialize the tierMultiplier
        tierMultipliers = new HashMap<>();
        tierMultipliers.put("Bronze", 1.0);
        tierMultipliers.put("Silver", 1.25);
        tierMultipliers.put("Gold", 1.5);
        tierMultipliers.put("Platinum", 2.0);
    }

    public void updateMultiplier(String tier, double multiplier) {
        tierMultipliers.put(tier, multiplier);
    }

    // Display tier multipliers to the admin to modify
    public void printTierMultipliers() {
        int counter = 1;
        System.out.println("---------------------------------");
        System.out.println("|     Tier Multiplier Menu      |");
        System.out.println("---------------------------------");
        for (Map.Entry<String, Double> entry : tierMultipliers.entrySet()) {
            System.out.println("| " + counter + ". " + entry.getKey() + "\t: " + entry.getValue() + "\t\t|");
            counter++;
        }
        System.out.println("---------------------------------");
    }

    // Return grade level based on accumulated points
    public String determineGrade(int points) {
        if (points >= 3000) {
            return "Platinum";
        } else if (points >= 2000) {
            return "Gold";
        } else if (points >= 1000) {
            return "Silver";
        } else {
            return "Bronze";
        }
    }

    // Overloaded method to determine grade based on customer ID
    public String determineGrade(String customerId) throws FileNotFoundException {
        int totalPoints = calculateAccumulatedPoint(customerId);
        return determineGrade(totalPoints); // Delegates to the int parameter version
    }

    // Calculate accumulated points for a customer
    public int calculateAccumulatedPoint(String customerId) throws FileNotFoundException {
        List<Earning> earnings = CSVWrite.getAllEarnings();
        int totalPoints = 0;
        for (Earning earn : earnings) {
            if (earn.getMemberNo().equals(customerId)) {
                totalPoints += earn.getOriValue();
            }
        }
        return totalPoints;
    }

    // Get multiplier based on customer's grade
    public double getMultiplier(String customerId) throws FileNotFoundException {
        return tierMultipliers.get(determineGrade(customerId));
    }

    // Overloaded method to get multiplier based on customer's grade
    public double getMultiplier(int points) {
        return tierMultipliers.get(determineGrade(points)); // Delegates to the int parameter version of determineGrade
    }

    // String representation of customer grade details
    public String toString(String customerId) throws FileNotFoundException {
        String grade = determineGrade(customerId);
        int accumulatedPoint = calculateAccumulatedPoint(customerId);
        if (accumulatedPoint >= 3000) {
            nextTiers = "No more tiers";
        } else if (accumulatedPoint >= 2000) {
            nextTiers = String.valueOf(accumulatedPoint) + "/3000" + " Platinum";
        } else if (accumulatedPoint >= 1000) {
            nextTiers = String.valueOf(accumulatedPoint) + "/2000" + " Gold";
        } else {
            nextTiers = String.valueOf(accumulatedPoint) + "/1000" + " Silver";
        }

        return "Current Grade\t\t: " + grade + "(Earning Points x " + String.valueOf(getMultiplier(customerId)) + ")\t|"
                + "\n| Next Tier\t\t: " + nextTiers + "\t\t\t\t|";
    }

    // Overloaded method to generate string representation of customer grade details
    public String toString(int points) {
        String grade = determineGrade(points);
        if (points >= 3000) {
            nextTiers = "No more tiers";
        } else if (points >= 2000) {
            nextTiers = String.valueOf(points) + "/3000" + " Platinum";
        } else if (points >= 1000) {
            nextTiers = String.valueOf(points) + "/2000" + " Gold";
        } else {
            nextTiers = String.valueOf(points) + "/1000" + " Silver";
        }

        return "Current Grade\t\t: " + grade + "(Earning Points x " + String.valueOf(getMultiplier(points)) + ")\t|"
                + "\n| Next Tier\t\t: " + nextTiers + "\t\t\t\t|";
    }

    public String getNextTiers(String customerId) throws FileNotFoundException {    
        int accumulatedPoint = calculateAccumulatedPoint(customerId);
        if (accumulatedPoint >= 3000) {
            nextTiers = "No more tiers";
        } else if (accumulatedPoint >= 2000) {
            nextTiers = String.valueOf(accumulatedPoint) + "/3000" + " Platinum";
        } else if (accumulatedPoint >= 1000) {
            nextTiers = String.valueOf(accumulatedPoint) + "/2000" + " Gold";
        } else {
            nextTiers = String.valueOf(accumulatedPoint) + "/1000" + " Silver";
        }
        return nextTiers;
    }

}
