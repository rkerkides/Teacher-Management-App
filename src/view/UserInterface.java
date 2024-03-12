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

    public Date getDateInput(String prompt) {
        System.out.println(prompt + " (YYYY-MM-DD)");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return formatter.parse(input);
            } catch (ParseException e) {
                System.out.println("Invalid input. Please enter a date in the format YYYY-MM-DD.");
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
                    inputTeachingRequirements();
					pauseBeforeContinuing();
                    break;
                case 2:
                    displayAllTeachingRequirements();
                    pauseBeforeContinuing();
                    break;
                case 3:
                    maintainTeacherDatabase();
                    pauseBeforeContinuing();
                    break;
                case 4:
                    matchTeachersWithRequirements();
                    break;
                case 5:
                    scheduleTrainingForTeachers();
                    pauseBeforeContinuing();
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

    // Helper method for making a smoother transition back to the main menu after completing a task
    private void pauseBeforeContinuing() {
        showMessage("\nPress Enter to return to the main menu...");
        scanner.nextLine();
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


    private void matchTeachersWithRequirements() {
        if (teachingRequirementService.getAllTeachingRequirements().isEmpty()) {
            System.out.println("There are no outstanding teaching requirements.");
            int decision = getIntInput("\nPress 1 to return to the main menu.");
            while (decision != 1) {
                decision = getIntInput("Press 1 to return to the main menu.");
            }
            return;
        }
        showMessage("\nPlease select a requirement from the following list to find matching teachers (enter ID number only):\n");
        displayAllTeachingRequirements();
        int choice = getIntInput("");

        // If number entered is not a valid ID, will continuously request a new number and store new input as choice
        while (teachingRequirementService.getTeachingRequirement(choice).isEmpty()) {
            showMessage("The number you entered is not a valid Requirement ID. Please enter a valid Requirement ID: ");
            choice = getIntInput("");
        }
        findMatchingTeachers(teachingRequirementService.getTeachingRequirement(choice).get());
    }

    private void findMatchingTeachers(TeachingRequirement req) {
        List<Teacher> perfectMatches = new ArrayList<Teacher>();
        List<Teacher> onlySubjectMatches = new ArrayList<Teacher>();
        List<Teacher> allQualMatches = new ArrayList<Teacher>();
        int qualsRequired = req.getQualificationsRequired().length;
        for (Teacher teacher : teacherService.getAllTeachers()) {
            boolean subjectMatch = false;
            boolean allQualMatch = false;
            int qualMatchCounter = 0;
            for (String subject : teacher.getCanTeach()) {
                if (matchCheck(req.getSubject(), subject)) {
                    subjectMatch = true;
                    break;
                }
            }
            for (String qualRequired : req.getQualificationsRequired()) {
                for (String qual : teacher.getQualifications()) {
                    if (matchCheck(qualRequired, qual)) {
                        qualMatchCounter++;
                        break;
                    }
                }
            }
            if (qualMatchCounter >= qualsRequired) {
                allQualMatch = true;
            }

            if (subjectMatch && allQualMatch) {
                perfectMatches.add(teacher);
                continue;
            } else if (subjectMatch) {
                onlySubjectMatches.add(teacher);
                continue;
            } else if (allQualMatch) {
                allQualMatches.add(teacher);
                continue;
            }

        }
        System.out.println("\nFinding matching teachers for: \n" + req.toString());
        if (!(perfectMatches.isEmpty())) {
            System.out.println("\nThe following teachers meet all subject and qualification requirements: ");
            for (Teacher teacher : perfectMatches) {
                System.out.println(teacher.getName() + " (ID: " + teacher.getId() + ")");
            }
        }
        if (!(onlySubjectMatches.isEmpty())) {
            System.out.println("\nThe following teachers can teach the required subject: ");
            for (Teacher teacher : onlySubjectMatches) {
                System.out.println(teacher.getName() + " (ID: " + teacher.getId() + ")");
            }
        }
        if (!(allQualMatches.isEmpty())) {
            System.out.println("\nThe following teachers have all required qualifications: ");
            for (Teacher teacher : allQualMatches) {
                System.out.println(teacher.getName() + " (ID: " + teacher.getId() + ")");
            }
        }
        if (perfectMatches.isEmpty() && onlySubjectMatches.isEmpty() && allQualMatches.isEmpty()) {
            System.out.println("\nThere are no teachers that match this requirement.");
        }

        int decision = getIntInput("\nPress 1 to return to the main menu.");
        while (decision != 1) {
            decision = getIntInput("Press 1 to return to the main menu.");
        }
    }

    private boolean matchCheck(String s1, String s2) {
        // If strings are equal (non-case-sensitive), return true
        if (s1.equalsIgnoreCase(s2)) {
            return true;
        }
        // If strings are substrings of one another, return true
        else if (s1.toLowerCase().contains(s2.toLowerCase())) {
            return true;
        } else if (s2.toLowerCase().contains(s1.toLowerCase())) {
            return true;
        }
        // If 80% or more of letters of shorter string match, return true, else return false
        else {
            int counter = 0;
            char[] s1Array = s1.toCharArray();
            char[] s2Array = s2.toCharArray();
            int shortestLength = s1Array.length;
            if (s1Array.length > s2Array.length) {
                shortestLength = s2Array.length;
            }
            for (int i = 0; i < shortestLength; i++) {
                if (s1Array[i] == s2Array[i]) {
                    counter++;
                }
            }
            if ((double) counter / shortestLength >= 0.8) {
                return true;
            } else {
                int wordCounter = 0;
                String[] s1Words = s1.toLowerCase().split("\\W+");
                String[] s2Words = s2.toLowerCase().split("\\W+");
                for (String word : s1Words) {
                    if (Arrays.asList(s2Words).contains(word.toLowerCase())) {
                        wordCounter++;
                        continue;
                    }
                }
                if (wordCounter >= 2) {
                    return true;
                } else {
                    return false;
                }
            }
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
        teachers.forEach(teacher -> System.out.println(teacher.toString() + "\n"));
    }

    public void displayAllTeachingRequirements() {
        List<TeachingRequirement> requirements = teachingRequirementService.getAllTeachingRequirements();
        requirements.forEach(requirement -> System.out.println(requirement.toString()));
    }

    // case 5 logic @Bariscan
    private void scheduleTrainingForTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        teachers.forEach(System.out::println);

        int teacherId = getIntInput("Enter ID of Teacher to schedule training for:");

        Optional<Teacher> optionalTeacher = teacherService.getTeacher(teacherId);
        if (!optionalTeacher.isPresent()) {
            System.out.println("Teacher not found.");
            return;
        }
        Teacher selectedTeacher = optionalTeacher.get();

        System.out.println(selectedTeacher.displayAvailabilities());

        Date date = getDateInput("Enter date to schedule training for (YYYY-MM-DD):");

        if (!selectedTeacher.getAvailabilities().contains(date)) {
            System.out.println("Date not available for this teacher.");
            return;
        }

        selectedTeacher.getAvailabilities().remove(date);

        String subject = getInput("Enter subject to schedule training for:");
        String course = getInput("Enter course to schedule training for:");

        TrainingSession newSession = new TrainingSession(date, selectedTeacher, subject, course);
        trainingSessionService.addTrainingSession(newSession);

        System.out.println("Training Session Scheduled:");
        System.out.println(newSession);
    }
	
	// Case 1 @Abs
	private void inputTeachingRequirements() {
		showMessage("Please enter the teaching requirements.");
		
		String subject = getInput("Please enter your subject: ");
		List<String> qualifications = new ArrayList<>();
		String qualificationInput;
		do {
			qualificationInput = getInput("Please enter a qualification (or type 'done' to finish): ");
			if (!"done".equalsIgnoreCase(qualificationInput)) {
				qualifications.add(qualificationInput);
			}
		} while (!"done".equalsIgnoreCase(qualificationInput));
		
		String experience = getInput("Please enter your experience:");
		String educationLevel = getInput("Please enter your education level:");
		
		// Add the new teaching requirement to the teachingRequirementService
		TeachingRequirement newRequirement = new TeachingRequirement(subject, qualifications, experience, educationLevel);
		teachingRequirementService.addTeachingRequirement(newRequirement);
		
		showMessage("Teaching requirement successfully added.");
	}

}
