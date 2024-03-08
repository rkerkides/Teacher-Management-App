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
import java.util.ArrayList;
import java.util.Arrays;
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
                	matchTeachersWithRequirements();
                    break;
                case 5:
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
    	if (teachingRequirementService.getAllTeachingRequirements().isEmpty()) {
    		System.out.println("There are no outstanding teaching requirements.");
    		int decision = getIntInput ("\nPress 1 to return to the main menu.");
        	while (decision != 1) {
        		decision = getIntInput ("Press 1 to return to the main menu.");
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
    	findMatchingTeachers (teachingRequirementService.getTeachingRequirement(choice).get());
    }
    
    private void findMatchingTeachers (TeachingRequirement req) {
    	List <Teacher> perfectMatches = new ArrayList <Teacher> ();
    	List <Teacher> onlySubjectMatches = new ArrayList <Teacher> ();
    	List <Teacher> allQualMatches = new ArrayList <Teacher> ();
    	int qualsRequired = req.getQualificationsRequired().length;
    	for (Teacher teacher : teacherService.getAllTeachers()) {
    		boolean subjectMatch = false;
    		boolean allQualMatch = false;
    		int qualMatchCounter = 0;
    		for (String subject : teacher.getCanTeach()) {
    			if (matchCheck (req.getSubject(), subject)) {
    				subjectMatch = true;
    				break;
    			}
    		}
    		for (String qualRequired : req.getQualificationsRequired()) {
    			for (String qual : teacher.getQualifications()) {
    				if (matchCheck (qualRequired, qual)) {
    					qualMatchCounter++;
    					break;
    				}
    			}
    		}
    		if (qualMatchCounter>=qualsRequired) {
    			allQualMatch = true;
    		}
    		
    		if (subjectMatch && allQualMatch) {
    			perfectMatches.add(teacher);
    			continue;
    		}
    		else if (subjectMatch) {
    			onlySubjectMatches.add(teacher);
    			continue;
    		}
    		else if(allQualMatch) {
    			allQualMatches.add(teacher);
    			continue;
    		}
    		
    	}
    	System.out.println ("\nFinding matching teachers for: \n" + req.toString());
    	if (!(perfectMatches.isEmpty())) {
    		System.out.println ("\nThe following teachers meet all subject and qualification requirements: ");
    		for (Teacher teacher : perfectMatches) {
    			System.out.println(teacher.getName() + " (ID: " + teacher.getId() + ")");
    		}
    	}
    	if (!(onlySubjectMatches.isEmpty())) {
    		System.out.println ("\nThe following teachers can teach the required subject: ");
    		for (Teacher teacher : onlySubjectMatches) {
    			System.out.println(teacher.getName() + " (ID: " + teacher.getId() + ")");
    		}
    	}
    	if (!(allQualMatches.isEmpty())) {
    		System.out.println ("\nThe following teachers have all required qualifications: ");
    		for (Teacher teacher : allQualMatches) {
    			System.out.println(teacher.getName() + " (ID: " + teacher.getId() + ")");
    		}
    	}
    	if (perfectMatches.isEmpty() && onlySubjectMatches.isEmpty() && allQualMatches.isEmpty()) {
    		System.out.println("\nThere are no teachers that match this requirement.");
    	}
    	
    	int decision = getIntInput ("\nPress 1 to return to the main menu.");
    	while (decision != 1) {
    		decision = getIntInput ("Press 1 to return to the main menu.");
    	}
    }
    
    private boolean matchCheck (String s1, String s2) {
    	// If strings are equal (non-case-sensitive), return true
    	if (s1.equalsIgnoreCase(s2)) {
    		return true;
    	}
    	// If strings are substrings of one another, return true
    	else if (s1.toLowerCase().contains(s2.toLowerCase())) {
    		return true;
    	}
    	else if (s2.toLowerCase().contains(s1.toLowerCase())) {
    		return true;
    	}
    	// If 80% or more of letters of shorter string match, return true, else return false
    	else {
    		int counter = 0;
    		char [] s1Array = s1.toCharArray();
    		char [] s2Array = s2.toCharArray();
    		int shortestLength = s1Array.length;
    		if (s1Array.length>s2Array.length) {
    			shortestLength = s2Array.length;
    		}
    		for (int i = 0; i < shortestLength; i++) {
    			if (s1Array[i] == s2Array[i]) {
    				counter++;
    			}
    		}
    		if ((double)counter/shortestLength >= 0.8) {
    			return true;
    		}
    		else {
    			int wordCounter = 0;
    			String [] s1Words = s1.toLowerCase().split("\\W+");
    			String [] s2Words = s2.toLowerCase().split("\\W+");
    			for (String word : s1Words) {
    				if (Arrays.asList(s2Words).contains(word.toLowerCase()))  {
    					wordCounter++;
    					continue;
    				}
    			}
    			if (wordCounter >= 2) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    	}
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

    public void displayAllTeachingRequirements() {
        List<TeachingRequirement> requirements = teachingRequirementService.getAllTeachingRequirements();
        requirements.forEach(requirement -> System.out.println(requirement.toString()));
    }
}