package view;

import DAO.GenericDAO;
import model.Teacher;
import model.TeachingRequirement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
// Private static variable to store the singleton of the class
    private static UserInterface instance;
    private Scanner scanner;
    private GenericDAO<TeachingRequirement> teachingRequirementDAO;

    // Private constructor to prevent instantiation from outside the class
    private UserInterface() {
        this.scanner = new Scanner(System.in);
        this.teachingRequirementDAO = new GenericDAO<>("data/teachingRequirements.ser");
    }

    // Public static method to get the single instance of the class
    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    public String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public int getIntInput(String prompt) {
        System.out.println(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next(); // Read and discard unwanted input
            System.out.println("Invalid input. Please enter an integer.");
        }
        return scanner.nextInt();
    }

    public double getDoubleInput(String prompt) {
        System.out.println(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.next(); // Read and discard unwanted input
            System.out.println("Invalid input. Please enter a number.");
        }
        return scanner.nextDouble();
    }

    public boolean getYesNoInput(String prompt) {
        System.out.println(prompt + " (yes/no)");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if ("yes".equals(input)) {
                return true;
            } else if ("no".equals(input)) {
                return false;
            } else {
                System.out.println("Invalid input. Please answer 'yes' or 'no'.");
            }
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public Date inputDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        while (date == null) {
            String dateString = getInput("Enter the date (dd/MM/yyyy):");
            try {
                date = dateFormat.parse(dateString);
                // Additional check to ensure the date is in the future
                if (date.before(new Date())) {
                    System.out.println("The date must be in the future. Please try again.");
                    date = null; // Reset date to loop again if not in the future
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }
        return date;
    }

    public Teacher inputTeacher() {
        String name = getInput("Enter the teacher's name:");
        String experience = getInput("Enter the teacher's experience:");
        Teacher teacher = new Teacher(name, experience);

        String decision = getInput("Would you like to add qualifications? (yes/no)");
        while (decision.equals("yes")) {
            String qualification = getInput("Enter a qualification:");
            // Add the qualification to the teacher
            teacher.getQualifications().add(qualification);
            decision = getInput("Would you like to add another qualification? (yes/no)");
        }
        decision = getInput("Would you like to add availabilities? (yes/no)");
        while (decision.equals("yes")) {
            // Add the availability to the teacher
            teacher.getAvailabilities().add(inputDate());
            decision = getInput("Would you like to add another availability? (yes/no)");
        }
        decision = getInput("Would you like to add subjects the teacher can teach? (yes/no)");
        while (decision.equals("yes")) {
            String subject = getInput("Enter a subject:");
            // Add the subject to the teacher
            teacher.getCanTeach().add(subject);
            decision = getInput("Would you like to add another subject? (yes/no)");
        }
        return teacher;
    }

    public void displayTeachingRequirements(){
        // Read the teaching requirements from the file
        // Display the teaching requirements
        Map<Integer, TeachingRequirement> teachingRequirements = teachingRequirementDAO.getAllEntities();
        for (TeachingRequirement requirement : teachingRequirements.values()) {
            System.out.println(requirement);
        }
    }
}
