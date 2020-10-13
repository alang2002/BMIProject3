import java.io.Serializable;
import java.text.DecimalFormat;

public class BMI implements Comparable<BMI>, Serializable {
    // This class will serve as an instantiable class that can be used to determine someone's BMI by calculating it
    // The class is set up with all the methods to correctly set up a BMI object (a patient)

    // Create private variables to be used in the code
    private int weight, height, option;
    private String fName, lName, fullName, status;
    private double bmi;

    // Default constructor with default values
    // option = 1 for metric
    public BMI() {
//        this.weight = 70;
//        this.height = 170;
//        this.option = 1;
//        this.fName = "Unknown";
//        this.lName = "Unknown";
//        this.fullName = "Unknown, Unknown";
        setWeight(70);
        setHeight(170);
        setOption(1);
        setfName("Unknown");
        setlName("Unknown");
        setFullName(fName + ", " + lName);
    }

    // overloaded constructor to accept values inputted by program/user
    public BMI(int weight, int height, int option, String fName, String lName) {
        setWeight(weight);
        setHeight(height);
        setOption(option);
        setfName(fName);
        setlName(lName);
        setFullName(lName + ", " + fName);
    }

    // Another overloaded constructor for full patient info
    public BMI(int weight, int height, int option, String fName, String lName, String status, double bmi) {
        setWeight(weight);
        setHeight(height);
        setOption(option);
        setfName(fName);
        setlName(lName);
        setFullName(lName + ", " + fName);
        setStatus(status);
        setBmi(bmi);
    }

    // Validation of setters to ensure there is no invalid inputs
    // else set to default value
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (weight > 0) {
            this.weight = weight;
        }
        else {
            this.weight = 70;
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
        else {
            this.height = 170;
        }
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        if (option == 1 || option == 2) {
            this.option = option;
        }
        else {
            this.option = 1;
        }
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        if (fName.equals("")) {
            this.fName = "Unknown";
        }
        else {
            this.fName = fName;
        }
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        if (lName.equals("")) {
            this.lName = "Unknown";
        }
        else {
            this.lName = lName;
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    // method to actually calculate BMI
    public double calcBMI() {
        double bmi = 0.0;
        double weight = getWeight();
        double height = getHeight();
        int option = getOption();

        if (option == 1) {
            // Metric
            height = height / 100;
            bmi = weight / (Math.pow(height, 2));
        }
        else if (option == 2) {
            // Standard
            bmi = 703 * (weight / (Math.pow(height, 2)));
        }
        return bmi;
    }

    @Override
    public String toString() {
        String category = "";
        double bmi = calcBMI();
        String output = "";

        // decision structure to choose the correct category depending on the BMI amount
        if (bmi < 18.5) {
            category = "Underweight";
        }
        else if (bmi >= 18.5 && bmi < 25) {
            category = "Normal weight";
        }
        else if (bmi >= 25 && bmi < 30) {
            category = "Overweight";
        }
        else {
            category = "Obesity";
        }

        // Set bmi and status for patient for complete info
        setBmi(bmi);
        setStatus(category);

        // Printing out the value
        // Formatting the BMI
        DecimalFormat formatter = new DecimalFormat("#0.00");
        output = "Patient is " + fullName + " and BMI is " + formatter.format(bmi) + " and it falls under the category " + category;
        return output;
    }

    @Override
    public int compareTo(BMI o) {
        lName = getlName();
        return lName.compareTo(o.lName);
    }
}