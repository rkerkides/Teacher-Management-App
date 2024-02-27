import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class DAO implements TeacherDAO {
    // Singleton instance
    private static DAO instance;
    private Map<Integer, Teacher> teachers = new HashMap<>();
    private static final String FILE_PATH = "teachers.ser";

    // Private constructor
    private DAO() {
        loadTeachers();
    }

    // Public method to get instance
    public static DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }
        return instance;
    }
    public Map<Integer, Teacher> getAllTeachers() {
        return teachers;
    }

    public void addTeacher(Teacher teacher) {
        teachers.put(teacher.getId(), teacher);
        saveTeachers();
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher.getId());
        saveTeachers();
    }

    public void updateTeacher(Teacher teacher) {
        // Assuming Teacher class has an `id` field and setters for other fields
        if (teachers.containsKey(teacher.getId())) {
            teachers.put(teacher.getId(), teacher);
            saveTeachers();
        }
    }

    public Teacher getTeacher(int id) {
        return teachers.get(id);
    }

    // Serialize the teachers map to a file
    private void saveTeachers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(teachers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserialize the teachers map from a file
    @SuppressWarnings("unchecked")
    private void loadTeachers() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                teachers = (Map<Integer, Teacher>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
