package service;

import dao.GenericDAO;
import model.Teacher;

import java.util.List;
import java.util.Optional;

public class TeacherService {
    private final GenericDAO<Teacher> teacherDAO;

    public TeacherService(GenericDAO<Teacher> teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    public void addTeacher(Teacher teacher) {
        teacherDAO.addEntity(teacher);
    }

    public List<Teacher> getAllTeachers() {
        // Directly use the list returned by getAllEntities
        return teacherDAO.getAllEntities();
    }

    public Optional<Teacher> getTeacher(int id) {
        // Return an Optional to gracefully handle null values
        return teacherDAO.getEntity(id);
    }

    public boolean updateTeacher(Teacher teacher) {
        // Check if the teacher exists before updating
        if (getTeacher(teacher.getId()).isPresent()) {
            teacherDAO.updateEntity(teacher);
            return true;
        } else {
            // Log or handle the case where the teacher does not exist
            return false;
        }
    }

    public boolean removeTeacher(int id) {
        // Remove a teacher by id and handle non-existent teachers
        Optional<Teacher> teacher = getTeacher(id);
        if (teacher.isPresent()) {
            teacherDAO.removeEntity(teacher.get());
            return true;
        } else {
            // Log or handle the case where the teacher does not exist
            return false;
        }
    }
}