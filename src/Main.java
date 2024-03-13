import dao.FileGenericDAO;
import model.Teacher;
import model.TeachingRequirement;
import model.TrainingSession;
import service.TeacherService;
import service.TeachingRequirementService;
import service.TrainingSessionService;
import view.UserInterface;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Instantiate DAOs first to ensure IdGenerator is initialized
        FileGenericDAO<Teacher> teacherDAO = new FileGenericDAO<>("data/teachers.ser", Teacher.class);
        FileGenericDAO<TeachingRequirement> teachingRequirementDAO = new FileGenericDAO<>("data/teachingRequirements.ser", TeachingRequirement.class);
        FileGenericDAO<TrainingSession> trainingSessionDAO = new FileGenericDAO<>("data/trainingSessions.ser", TrainingSession.class);

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

    private static void rebuildAssociations(TeacherService teacherService, TrainingSessionService trainingSessionService) {
        List<TrainingSession> sessions = trainingSessionService.getAllTrainingSessions();
        sessions.forEach(session -> {
            // Assuming TrainingSession has getTeacherId() method
            int teacherId = session.getTeacherId();
            teacherService.getTeacher(teacherId).ifPresent(teacher -> {
                teacher.addTrainingSession(session);
            });
        });
    }
}
