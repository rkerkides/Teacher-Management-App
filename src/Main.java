import dao.FileGenericDAO;
import model.Teacher;
import model.TeachingRequirement;
import model.TrainingSession;
import service.TeacherService;
import service.TeachingRequirementService;
import service.TrainingSessionService;
import view.UserInterface;

public class Main {
    public static void main(String[] args) {
        /*// Create a new DAO
        GenericDAO<Teacher> teacherDAO = new GenericDAO<>("data/teachers.ser");
        // Create a new model.Teacher
        Teacher teacher = new Teacher(1, "John Doe", new ArrayList<>(), new ArrayList<>(), "5 years", new ArrayList<>(), new ArrayList<>());
        // Add the teacher to the DAO
        teacherDAO.addEntity(teacher);
        // Create another teacher
        Teacher teacher2 = new Teacher(2, "Jane Smith", new ArrayList<>(), new ArrayList<>(), "10 years", new ArrayList<>(), new ArrayList<>());
        // Add the teacher to the DAO
        teacherDAO.addEntity(teacher2);
        // Update the first teacher
        teacher.setExperience("10 years");
        teacherDAO.updateEntity(teacher);
        // Get all teachers from the DAO
        Map<Integer, Teacher> teachers = teacherDAO.getAllEntities();
        // Print all teachers
        for (Identifiable t : teachers.values()) {
            System.out.println(t);
        }*/


        // Instantiate DAOs
        FileGenericDAO<Teacher> teacherDAO = new FileGenericDAO<>("data/teachers.ser");
        FileGenericDAO<TeachingRequirement> teachingRequirementDAO = new FileGenericDAO<>("data/teachingRequirements.ser");
        FileGenericDAO<TrainingSession> trainingSessionDAO = new FileGenericDAO<>("data/trainingSessions.ser");

        // Instantiate services with the DAOs
        TeacherService teacherService = new TeacherService(teacherDAO);
        TeachingRequirementService teachingRequirementService = new TeachingRequirementService(teachingRequirementDAO);
        TrainingSessionService trainingSessionService = new TrainingSessionService(trainingSessionDAO);

        // Instantiate the UI with the services
        UserInterface ui = UserInterface.getInstance();
        ui.setTeacherService(teacherService);
        ui.setTeachingRequirementService(teachingRequirementService);
        ui.setTrainingSessionService(trainingSessionService);
        ui.displayMainMenu();
    }
}