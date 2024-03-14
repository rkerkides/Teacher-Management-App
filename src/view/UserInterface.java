package view;

import model.TrainingSession;
import service.TeacherService;
import service.TeachingRequirementService;
import model.Teacher;
import model.TeachingRequirement;
import service.TrainingSessionService;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The UserInterface class provides a user interface for interacting with the Part-Time Teachers Management System.
 * It handles user input, displays menus, and calls the appropriate service methods.
 */
public class UserInterface {
    private static UserInterface instance;
    private final Scanner scanner;
    private TeacherService teacherService;
    private TeachingRequirementService teachingRequirementService;
    private TrainingSessionService trainingSessionService;

    private UserInterface() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns the singleton instance of the UserInterface.
     *
     * @return The instance of the UserInterface.
     */
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

    public Time getTimeInput(String prompt) {
        System.out.println(prompt + " (HH:MM)");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        timeFormatter.setLenient(false); // Ensure strict parsing, e.g., no 25:61

        while (true) {
            String input = scanner.nextLine().trim();
            try {
                // Parse the input as a java.util.Date object first
                java.util.Date parsedTime = timeFormatter.parse(input);

                // Convert java.util.Date to java.sql.Time
                Time time = new Time(parsedTime.getTime());

                return time;
            } catch (ParseException e) {
                System.out.println("Invalid input. Please enter a time in the format HH:MM.");
            }
        }
    }

    public List<String> getDaysOfWeekInput(String prompt) {
        List<String> daysOfWeek = new ArrayList<>();
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        System.out.println(prompt);
        for (int i = 0; i < days.length; i++) {
            System.out.println((i + 1) + ". " + days[i]);
        }
        System.out.println("Enter the numbers corresponding to the days (e.g., 1 for Sunday, 2 for Monday). Enter '0' when finished:");

        while (true) {
            int input = getIntInput("Select a day:");
            if (input == 0) {
                break; // Exit loop when user is done
            } else if (input > 0 && input <= days.length) {
                String selectedDay = days[input - 1];
                if (!daysOfWeek.contains(selectedDay)) { // Avoid duplicate entries
                    daysOfWeek.add(selectedDay);
                } else {
                    System.out.println(selectedDay + " is already selected. Please choose another day or enter '0' to finish.");
                }
            } else {
                System.out.println("Invalid selection. Please enter a number between 1 and 7, or '0' to finish.");
            }
        }
        return daysOfWeek;
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

    /**
     * Displays the main menu and handles user input.
     */
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

    /**
     * Pauses the program and waits for the user to press Enter before returning to the main menu.
     */
    private void pauseBeforeContinuing() {
        showMessage("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }


    /**
     * Displays a menu for maintaining the teacher database and handles user input.
     */
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

    /**
     * Displays a menu for matching teachers with teaching requirements and handles user input.
     */
    private void matchTeachersWithRequirements() {
        List<TeachingRequirement> requirements = teachingRequirementService.getAllTeachingRequirements();
        if (requirements.isEmpty()) {
            System.out.println("There are no outstanding teaching requirements.");
            return;
        }
        boolean unassigned = false;
        for (TeachingRequirement requirement : requirements) {
            if (requirement.getTeacher() == null) {
                unassigned = true;
            }
        }
        if (!unassigned) {
            System.out.println("\nAll teaching requirements have already been assigned a teacher.");
            return;
        }
        System.out.println("\nPlease select a requirement from the following list to find matching teachers:");
        displayAllTeachingRequirements();
        int reqId = getIntInput("Enter the ID of the requirement:");

        TeachingRequirement selectedReq = teachingRequirementService.getTeachingRequirement(reqId).orElse(null);
        if (selectedReq == null) {
            System.out.println("Invalid requirement ID.");
            return;
        }
        findMatchingTeachers(selectedReq);
    }


    /**
     * Finds and displays teachers matching the given teaching requirement.
     *
     * @param req The teaching requirement to match teachers with.
     */
    private void findMatchingTeachers(TeachingRequirement req) {
        List<Teacher> perfectMatches = new ArrayList<>();
        List<Teacher> subjectMatches = new ArrayList<>();
        List<Teacher> qualificationMatches = new ArrayList<>();

        for (Teacher teacher : teacherService.getAllTeachers()) {
            if (teacher.getCanTeach().contains(req.getSubject())) {
                if (new HashSet<>(teacher.getQualifications()).containsAll(req.getQualificationsRequired())) {
                    perfectMatches.add(teacher);
                } else {
                    subjectMatches.add(teacher);
                }
            } else if (new HashSet<>(teacher.getQualifications()).containsAll(req.getQualificationsRequired())) {
                qualificationMatches.add(teacher);
            }
        }

        displayMatchResults("Perfect Matches (Subject & Qualifications):", perfectMatches);
        displayMatchResults("Matches by Subject:", subjectMatches);
        displayMatchResults("Matches by Qualifications:", qualificationMatches);

        String message = "\nWould you like to associate a teacher with this requirement?";
        if (getYesNoInput(message)) {
            associateTeacherWithRequirement(req);
        }
    }

    private void associateTeacherWithRequirement(TeachingRequirement req) {
        System.out.println("\nEnter the ID of the teacher you would like to associate with this requirement:");
        int teacherId = getIntInput("Enter Teacher ID:");

        Optional<Teacher> optionalTeacher = teacherService.getTeacher(teacherId);
        if (optionalTeacher.isEmpty()) {
            System.out.println("Teacher not found.");
            return;
        } else if (!(optionalTeacher.get().getAvailabilities().contains(req.getStartTime()) && optionalTeacher.get().
                getDaysOfWeekAvailable().contains(req.getDaysOfWeek()))) {
            System.out.println("\nTeacher " + optionalTeacher.get().getName() +" (ID:" + optionalTeacher.get().getId()
                    + ") is not available at time: " + req.getStartTime());
            return;
        }

        Teacher teacher = optionalTeacher.get();
        req.setTeacher(teacher);
        teachingRequirementService.updateTeachingRequirement(req);
        teacher.getAvailabilities().remove(req.getStartTime()); // Remove the time slot from the teacher's availability
        teacher.addTeachingRequirement(req);
        teacherService.updateTeacher(teacher);
        System.out.println("Teacher associated with requirement successfully.");
    }

    private void displayMatchResults(String header, List<Teacher> matches) {
        System.out.println("\n" + header);
        if (matches.isEmpty()) {
            System.out.println("None");
        } else {
            for (Teacher match : matches) {
                System.out.println(match.getName() + " (ID: " + match.getId() + ")");
            }
        }
    }

    /**
     * Prompts the user to input a new teacher and adds it to the teacher service.
     */
    private void addTeacher() {
        Teacher newTeacher = inputTeacher();
        teacherService.addTeacher(newTeacher);
        System.out.println("Teacher added successfully!");
    }

    /**
     * Prompts the user to input teacher details and returns a new Teacher object.
     *
     * @return The new Teacher object created from user input.
     */
    public Teacher inputTeacher() {
        String name = getInput("Enter the teacher's name:");
        String experience = getInput("Enter the teacher's experience:");
        List<Time> availabilities = new ArrayList<>();
        List<String> qualifications = new ArrayList<>();
        List<String> canTeach = new ArrayList<>();
        List<TrainingSession> trainingSessions = new ArrayList<>();
        List<String> daysOfWeekAvailable = new ArrayList<>();
        List<TeachingRequirement> teachingRequirements = new ArrayList<>();

        // Add qualifications
        if (getYesNoInput("Would you like to add qualifications?")) {
            do {
                String qualification = getInput("Enter a qualification:");
                qualifications.add(qualification);
            } while (getYesNoInput("Would you like to add another qualification?"));
        }

        // Add availabilities
        if (getYesNoInput("Would you like to add semester-long time availabilities?")) {
            do {
                availabilities.add(getTimeInput("Enter an availability"));
            } while (getYesNoInput("Would you like to add another availability?"));
        }

        // Add days of the week available
        if (getYesNoInput("Would you like to add days of the week available?")) {
            daysOfWeekAvailable = getDaysOfWeekInput("Select the days of the week the teacher is available:");
        }

        // Add subjects the teacher can teach
        if (getYesNoInput("Would you like to add subjects the teacher can teach?")) {
            do {
                String subject = getInput("Enter a subject:");
                canTeach.add(subject);
            } while (getYesNoInput("Would you like to add another subject?"));
        }

        // TrainingSessions are not added here as they are scheduled separately
        return new Teacher(name, availabilities, qualifications, experience,
                canTeach, trainingSessions, daysOfWeekAvailable, teachingRequirements);
    }

    /**
     * Prompts the user to update a teacher's details and updates the teacher in the service.
     */
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

        if (getYesNoInput("Would you like to add to the teacher's semester-long time availabilities?")) {
            List<Time> newAvailabilities = new ArrayList<>();
            do {
                Time time = getTimeInput("Enter a new availability");
                newAvailabilities.add(time);
                if (!getYesNoInput("Would you like to add another availability?")) break;
            } while (true);
            teacher.setAvailabilities(newAvailabilities);
        }

        if (getYesNoInput("Would you like to add to the days of the week the teacher is available?")) {
            List<String> newDaysOfWeek = getDaysOfWeekInput("Select the days of the week the teacher is available:");
            teacher.setDaysOfWeekAvailable(newDaysOfWeek);
        }

        if (getYesNoInput("Would you like to add to the subjects the teacher can teach?")) {
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


    /**
     * Removes a teacher from the teacher service based on the user input.
     * IDs are not updated after removal in order to ensure referential integrity.
     */
    private void removeTeacher() {
        int teacherId = getIntInput("Enter the ID of the teacher you want to remove:");
        boolean success = teacherService.removeTeacher(teacherId);

        if (success) {
            showMessage("Teacher removed successfully.");
        } else {
            showMessage("Could not find a teacher with the specified ID.");
        }
    }

    /**
     * Displays all teachers in the teacher service.
     */
    private void viewAllTeachers() {
        if (teacherService.getAllTeachers().isEmpty()) {
            showMessage("\nNo teachers available.");
            return;
        }
        List<Teacher> teachers = teacherService.getAllTeachers();
        teachers.forEach(teacher -> System.out.println("\n"+ teacher.toString()));
    }

    /**
     * Displays all teaching requirements in the teaching requirement service.
     */
    public void displayAllTeachingRequirements() {
        List<TeachingRequirement> requirements = teachingRequirementService.getAllTeachingRequirements();
        requirements.forEach(requirement -> System.out.println(requirement.toString()));
    }

    /**
     * Schedules a training session for a selected teacher based on their ID. It checks if the selected
     * teacher is available on the chosen date and schedules a training session for a specific subject and course
     * if the date is available. It then removes the date from the teacher's availability.
     */
    private void scheduleTrainingForTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        if (teachers.isEmpty()) {
            showMessage("No teachers available.");
            return;
        }
        teachers.forEach(teacher -> System.out.println(teacher)); // Display all teachers for selection

        int teacherId = getIntInput("Enter the ID of the Teacher to schedule training for:");
        Optional<Teacher> optionalTeacher = teacherService.getTeacher(teacherId);
        if (!optionalTeacher.isPresent()) {
            showMessage("Teacher not found.");
            return;
        }
        Teacher selectedTeacher = optionalTeacher.get();

        if (selectedTeacher.getDaysOfWeekAvailable().isEmpty()) {
            showMessage("This teacher has no availability.");
            return;
        }

        showMessage("Select a day for the training session from the teacher's availability: ");
        selectedTeacher.getDaysOfWeekAvailable().forEach(System.out::println);
        String dayOfWeek = getInput("Select the day for the training session:");
        if (!selectedTeacher.getDaysOfWeekAvailable().contains(dayOfWeek)) {
            showMessage("Invalid selection or teacher is not available on this day.");
            return;
        }

        Time timeSlot = getTimeInput("Enter the start time for the 2-hour training session");

        String subject = getInput("Enter the subject for the training session:");

        // Assuming a method to create and add a training session to the system and teacher
        TrainingSession newSession = new TrainingSession(dayOfWeek, selectedTeacher, subject, timeSlot);
        trainingSessionService.addTrainingSession(newSession);
        selectedTeacher.addTrainingSession(newSession);
        selectedTeacher.getAvailabilities().remove(timeSlot); // Remove the time slot from the teacher's availability
        teacherService.updateTeacher(selectedTeacher);

        System.out.println("Training Session Scheduled:");
        System.out.println(newSession);
    }

    /**
     * Prompts the user to input the details for a new teaching requirement and adds it to the teaching requirement service.
     */
    private void inputTeachingRequirements() {
        showMessage("\nEnter the details for the new teaching requirement.");

        String subject = getInput("Required subject:");

        // Initialize qualifications list
        List<String> qualifications = new ArrayList<>();
        if (getYesNoInput("Would you like to add qualifications?")) {
            do {
                String qualification = getInput("Enter a qualification:");
                qualifications.add(qualification);
            } while (getYesNoInput("Would you like to add another qualification?"));
        }

        String experience = getInput("Experience required (years):");

        Time startTime = getTimeInput("Enter the start time for the requirement");

        List<String> daysOfWeek = getDaysOfWeekInput("Select the days of the week the requirement is for:");

        TeachingRequirement newRequirement = new TeachingRequirement(subject, qualifications, experience, startTime, daysOfWeek);
        teachingRequirementService.addTeachingRequirement(newRequirement);
    }

}

