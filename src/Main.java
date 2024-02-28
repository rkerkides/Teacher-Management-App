import DAO.GenericDAO;
import interfaces.Identifiable;
import model.Teacher;
import view.UserInterface;
import java.util.ArrayList;
import java.util.Map;

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
        UserInterface ui = UserInterface.getInstance();
        ui.displayMainMenu();
    }
}