import java.util.Map;

/*
 * This interface is used to define the methods that will be used to interact with the database.
 * It includes methods for CRUD (Create, Read, Update, Delete) operations.
 * It can be implemented by both the current CSV implementation and a future database implementation.
 * This allows the application to remain simple for now, whilst ensuring that it can be easily extended in the future.
 */

public interface TeacherDAO {
    Map<Integer,Teacher> getAllTeachers();
    void addTeacher(Teacher teacher);

    void removeTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);
    Teacher getTeacher(int id);
}