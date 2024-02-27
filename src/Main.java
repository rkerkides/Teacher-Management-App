import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Create a new DAO
        DAO dao = new DAO();
        // Create a new Teacher
        Teacher teacher = new Teacher(1, "John Doe", new ArrayList<>(), new ArrayList<>(), "5 years", new ArrayList<>(), new ArrayList<>());
        // Add the teacher to the DAO
        dao.addTeacher(teacher);
        // Create another teacher
        Teacher teacher2 = new Teacher(2, "Jane Smith", new ArrayList<>(), new ArrayList<>(), "10 years", new ArrayList<>(), new ArrayList<>());
        // Add the teacher to the DAO
        dao.addTeacher(teacher2);
        // Update the first teacher
        teacher.setExperience("10 years");
        dao.updateTeacher(teacher);
        // Get all teachers from the DAO
        Map<Integer, Teacher> teachers = dao.getAllTeachers();
        // Print all teachers
        for (Teacher t : teachers.values()) {
            System.out.println(t);
        }
    }
}