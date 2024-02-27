package model;

import java.io.*;

import DAO.GenericDAO;
import view.UserInterface;

public class Administrator implements Serializable {
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    private GenericDAO<Teacher> teacherDAO = new GenericDAO<>("data/teachers.ser");
    private UserInterface ui = UserInterface.getInstance();

    public void inputTeacher (){
        // Call the UI to input the teacher details
        Teacher teacher = ui.inputTeacher();
        // Add the teacher to the DAO
        teacherDAO.addEntity(teacher);
    }

    public void displayAllTeachingRequirements(){
        // Call the UI to display the teaching requirements
        ui.displayTeachingRequirements();
    }

}
