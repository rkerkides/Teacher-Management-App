package view;

import service.TeacherService;
import service.TeachingRequirementService;
import model.Teacher;
import model.TeachingRequirement;
import service.TrainingSessionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static UserInterface instance;
    private final Scanner scanner;
    private TeacherService teacherService;
    private TeachingRequirementService teachingRequirementService;
    private TrainingSessionService trainingSessionService;

    private UserInterface() {
        scanner = new Scanner(System.in);
    }

    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    // Setter methods for service dependencies
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public void setTeachingRequirementService(TeachingRequirementService teachingRequirementService) {
        this.teachingRequirementService = teachingRequirementService;
    }

    public void setTrainingSessionService(TrainingSessionService trainingSessionService) {
        this.trainingSessionService = trainingSessionService;
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

    // Public method to display the main menu
    public void displayMainMenu() {
        boolean exit = false;

        while (!exit) {
            showMessage("\nWelcome to the Part-Time Teachers Management System.\n");
            showMessage("Please select an option:");
            showMessage("1. Input Teaching Requirements (Class Director)");
            showMessage("2. View Teaching Requirements (Administrator)");
            showMessage("3. Maintain Teacher Database (Administrator)");
            showMessage("4. Match Teachers with Requirements (Administrator)");
            showMessage("5. Schedule Training for Teachers (Administrator)");
            showMessage("6. Exit");

            int choice = getIntInput("");
            switch (choice) {
                case 1:
                    // Implement feature using teachingRequirementService
                    break;
                case 2:
                    displayAllTeachingRequirements();
                    break;
                case 3:
                    maintainTeacherDatabase();
                    break;
                case 4:
                    // Implement feature using teachingRequirementService and teacherService
                    break;
                case 5:
                    // Implement feature using trainingSessionService
                    break;
                case 6:
                    showMessage("Exiting...");
                    exit = true;
                    break;
                default:
                    showMessage("Invalid option, please try again.");
                    break;
            }
        }
    }

    private void inputTeachingRequirements() {
        Scanner scanner = new Scanner(System.in);

        String subject = getInput("Enter required subject: ");

        String qualification = getInput("Enter required qualification: ");

        String experience = getInput("Enter required experience: ");
    }


    private void viewTeachingRequirements() {
        // Implement functionality to view teaching requirements
    }

    private void maintainTeacherDatabase() {
        System.out.println("Choose an option:");
        System.out.println("1. Add Teacher");
        System.out.println("2. Update Teacher");
        System.out.println("3. Remove Teacher");
        System.out.println("4. View All Teachers");
        int choice = getIntInput("");
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                addTeacher();
                break;
            case 2:
                // Logic for updating a teacher
                break;
            case 3:
                // Logic for removing a teacher
                break;
            case 4:
                viewAllTeachers();
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private void matchTeachersWithRequirements() {
    }

    private void scheduleTrainingForTeachers() {
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

    private void addTeacher() {
        // Simplified example
        System.out.println("Enter teacher's name:");
        String name = scanner.nextLine();
        System.out.println("Enter teacher's experience:");
        String experience = scanner.nextLine();
        Teacher newTeacher = new Teacher(name, experience); // Assuming a constructor exists
        teacherService.addTeacher(newTeacher);
    }

    private void viewAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        teachers.forEach(teacher -> System.out.println(teacher.toString()));
    }

    private void displayAllTeachingRequirements() {
        List<TeachingRequirement> requirements = teachingRequirementService.getAllTeachingRequirements();
        requirements.forEach(requirement -> System.out.println(requirement.toString()));
    }
}