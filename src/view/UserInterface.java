package view;

import model.TrainingSession;
import service.TeacherService;
import service.TeachingRequirementService;
import model.Teacher;
import model.TeachingRequirement;
import service.TrainingSessionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        return input;
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

    private void maintainTeacherDatabase() {
        System.out.println("Choose an option:");
        System.out.println("1. Add Teacher");
        System.out.println("2. Update Teacher");
        System.out.println("3. Remove Teacher");
        System.out.println("4. View All Teachers");
        int choice = getIntInput("");

        switch (choice) {
            case 1:
                addTeacher();
                break;
            case 2:
                updateTeacher();
                break;
            case 3:
                removeTeacher();
                break;
            case 4:
                viewAllTeachers();
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private void addTeacher() {
        // Similar to the inputTeacher() method, but directly adds the teacher to the service
        Teacher newTeacher = inputTeacher();
        teacherService.addTeacher(newTeacher);
        System.out.println("Teacher added successfully!");
    }

    public Teacher inputTeacher() {
        String name = getInput("Enter the teacher's name:");
        String experience = getInput("Enter the teacher's experience:");
        List<Date> availabilities = new ArrayList<>();
        List<String> qualifications = new ArrayList<>();
        List<String> canTeach = new ArrayList<>();
        List<TrainingSession> trainingSessions = new ArrayList<>();

        // Add qualifications
        if (getYesNoInput("Would you like to add qualifications?")) {
            do {
                String qualification = getInput("Enter a qualification:");
                qualifications.add(qualification);
            } while (getYesNoInput("Would you like to add another qualification?"));
        }

        // Add availabilities
        if (getYesNoInput("Would you like to add availabilities?")) {
            do {
                availabilities.add(inputDate());
            } while (getYesNoInput("Would you like to add another availability?"));
        }

        // Add subjects the teacher can teach
        if (getYesNoInput("Would you like to add subjects the teacher can teach?")) {
            do {
                String subject = getInput("Enter a subject:");
                canTeach.add(subject);
            } while (getYesNoInput("Would you like to add another subject?"));
        }

        // Note: Adding TrainingSessions to the teacher would require additional logic
        return new Teacher(name, availabilities, qualifications, experience, canTeach, trainingSessions);
    }

    private void updateTeacher() {
        int teacherId = getIntInput("Enter the ID of the teacher you want to update:");
        Optional<Teacher> optionalTeacher = teacherService.getTeacher(teacherId);

        if (optionalTeacher.isEmpty()) {
            showMessage("Teacher not found.");
            return;
        }

        Teacher teacher = optionalTeacher.get();

        String name = getInput("Enter the teacher's new name (leave blank to keep current):");
        if (!name.isEmpty()) {
            teacher.setName(name);
        }

        String experience = getInput("Enter the teacher's new experience (leave blank to keep current):");
        if (!experience.isEmpty()) {
            teacher.setExperience(experience);
        }

        if (getYesNoInput("Would you like to update the teacher's qualifications?")) {
            List<String> newQualifications = new ArrayList<>();
            do {
                String qualification = getInput("Enter a qualification (leave blank to finish):");
                if (qualification.isEmpty()) break;
                newQualifications.add(qualification);
            } while (true);
            teacher.setQualifications(newQualifications);
        }

        if (getYesNoInput("Would you like to update the teacher's availabilities?")) {
            List<Date> newAvailabilities = new ArrayList<>();
            do {
                Date date = inputDate();
                newAvailabilities.add(date);
                if (!getYesNoInput("Would you like to add another availability?")) break;
            } while (true);
            teacher.setAvailabilities(newAvailabilities);
        }

        if (getYesNoInput("Would you like to update the subjects the teacher can teach?")) {
            List<String> newCanTeach = new ArrayList<>();
            do {
                String subject = getInput("Enter a subject (leave blank to finish):");
                if (subject.isEmpty()) break;
                newCanTeach.add(subject);
            } while (true);
            teacher.setCanTeach(newCanTeach);
        }

        boolean updateResult = teacherService.updateTeacher(teacher);
        if (updateResult) {
            showMessage("Teacher updated successfully.");
        } else {
            showMessage("Failed to update teacher.");
        }
    }


    // Removes teachers from the database
    // Ids are not changed after removal to ensure referential integrity
    private void removeTeacher() {
        int teacherId = getIntInput("Enter the ID of the teacher you want to remove:");
        boolean success = teacherService.removeTeacher(teacherId);

        if (success) {
            showMessage("Teacher removed successfully.");
        } else {
            showMessage("Could not find a teacher with the specified ID.");
        }
    }

    private void viewAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        teachers.forEach(teacher -> System.out.println(teacher.toString()));
    }

    public void displayAllTeachingRequirements() {
        List<TeachingRequirement> requirements = teachingRequirementService.getAllTeachingRequirements();
        requirements.forEach(requirement -> System.out.println(requirement.toString()));
    }

    // Other methods like inputTeachingRequirements(), viewTeachingRequirements(), matchTeachersWithRequirements(), scheduleTrainingForTeachers() would be implemented here.
}
