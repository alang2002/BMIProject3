import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BMIProgram {
    // Class to run main menu and accept user input
    // Aaron Lang

    // Static global List to hold BMI objects for persistence
    private static ArrayList<BMI> patientsList = new ArrayList<BMI>();

    public static void main(String[] args) {
        // De-serializing objects from input file at initialization
        // outputs to be used for serialization later on
        String filename = "bmi_patients.ser";
        FileInputStream inFile;
        ObjectInputStream inStream;
        FileOutputStream outFile;
        ObjectOutputStream outStream;

        // De-serializing
        try
        {
            inFile = new FileInputStream(filename);
            inStream = new ObjectInputStream(inFile);
            patientsList = (ArrayList<BMI>) inStream.readObject();

            inStream.close();
            inFile.close();
        }
        catch(Exception ex)
        {
            System.out.println("Error reading file");
        }

        // Setting up variables for input
        Scanner userInput = new Scanner(System.in);
        int optionSelected;

        // Menu display
        // Put inside a while loop to loop back to main menu until they choose to exit
        do {
            System.out.println("\n---------BMI Calculator---------");
            System.out.println("Created by Aaron Lang");
            System.out.println("\nSelect an option to continue (1-3):");
            System.out.println("Option 1: Metric");
            System.out.println("Option 2: Standard");
            System.out.println("Option 3: Print all patients");
            System.out.println("Option 4: Exit (Save patients to file)");
            optionSelected = userInput.nextInt();
            if (optionSelected > 4 || optionSelected < 1) {
                System.out.println("Not a valid input, try again.");
                optionSelected = 0;
            }
            else if (optionSelected == 1) {
                calcMetric();
            }
            else if (optionSelected == 2) {
                calcStandard();
            }
            else if (optionSelected == 3) {
                printAll();
            }
        }while (optionSelected != 4);
        System.out.println("Have a nice day! Exiting...");
        userInput.close();

        // Serializing objects to file (persistence)
        try{
            outFile = new FileOutputStream(filename);
            outStream = new ObjectOutputStream(outFile);

            // Writing list to file (updating)
            outStream.writeObject(patientsList);

            outStream.close();
            outFile.close();
        }
        catch(Exception ex){
            System.out.println("Error writing patients to file " + ex.getMessage());
        }
    }

    public static void calcMetric() {
        Scanner userInput = new Scanner(System.in);
        String fName, lName, addPatient;
        int weight;
        int height;
        // Option is not validated or prompted for because option has already been selected through menu.
        int option = 1;

        System.out.println("\n--Option 1 Selected: Metric--");
        // Get name
        System.out.println("Enter patient first name: ");
        fName = userInput.nextLine();
        System.out.println("Enter patient last name: ");
        lName = userInput.nextLine();

        // Do whiles to run through prompts at least once, then continuing to prompt until they enter a number
        // bigger than 0
        do {
            System.out.println("Enter weight in kilograms: ");
            weight = userInput.nextInt();
        } while (weight < 0);
        do {
            System.out.println("Enter height in centimeters: ");
            height = userInput.nextInt();
        } while (height < 0);

        // Creating new BMI object and using overloaded constructor to set information
        BMI user = new BMI(weight, height, option, fName, lName);
        // Calculating and printing out info
        System.out.println(user.toString());
        // Using other overloaded constructor to fill out all patient info (for printing out all patients)
        user = new BMI(weight, height, option, fName, lName, user.getStatus(), user.getBmi());

        // Prompt user to add entry to list
        System.out.println("Would you like to add this patient to the file? (Y/N)");
        userInput.nextLine();
        addPatient = userInput.nextLine();
        if (addPatient.toUpperCase().equals(("Y"))) {
            patientsList.add(user);
        }
    }

    public static void calcStandard() {
        Scanner userInput = new Scanner(System.in);
        String fName, lName, addPatient;
        int weight;
        int height;
        int option = 2;

        System.out.println("\n--Option 2 Selected: Standard--");
        do {
            System.out.println("Enter patient first name: ");
            fName = userInput.nextLine();
        } while (fName.equals(""));
        do {
            System.out.println("Enter patient last name: ");
            lName = userInput.nextLine();
        } while (lName.equals(""));

        do {
            System.out.println("Enter weight in pounds: ");
            weight = userInput.nextInt();
        } while (weight < 0);
        do {
            System.out.println("Enter height in inches: ");
            height = userInput.nextInt();
        } while (height < 0);

        BMI user = new BMI(weight, height, option, fName, lName);
        System.out.println(user.toString());
        user = new BMI(weight, height, option, fName, lName, user.getStatus(), user.getBmi());

        System.out.println("Would you like to add this patient to the file? (Y/N)");
        addPatient = userInput.next();
        if (addPatient.equals("Y")) {
            patientsList.add(user);
        }
    }

    public static void printAll() {
        // Setting up headers for columns
        String header1 = "Name";
        String header2 = "Height";
        String header3 = "Weight";
        String header4 = "Type";
        String header5 = "BMI";
        String header6 = "Status";
        // Variables to be used for patient values
        String fullName, status, type = null;
        double bmi;
        int height, weight;

        // Example output:
        //        Name		    Height		Weight	  Type		     BMI	    Status
        //        Doe, John	    70		    170		  Standard       24.2	    Normal
        //        Doe, Jane		170		    70		  Metric		 24.2	    Normal

        System.out.printf("%-20s %-15s %-15s %-15s %-15s %-15s", header1, header2, header3, header4, header5, header6);
        System.out.println("\n--------------------------------------------------------------------------------------------------");
        // Sorted list to be used for printing all patients
        List<BMI> sortedPatientsList = patientsList;
        Collections.sort(sortedPatientsList);
        for (BMI user : sortedPatientsList) {
            // Getting all the info and filling them into the variables each time
            fullName = user.getFullName();
            status = user.getStatus();
            if (user.getOption() == 1) {
                type = "Metric";
            }
            else if (user.getOption() == 2) {
                type = "Standard";
            }
            bmi = user.getBmi();
            height = user.getHeight();
            weight = user.getWeight();

            // Printing out patient info using left align formatting
            System.out.printf("%-20s %-15d %-15d %-15s %-15.2f %-15s \n", fullName, height, weight, type, bmi, status);
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("All patients displayed. Press enter key to return to menu.");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}