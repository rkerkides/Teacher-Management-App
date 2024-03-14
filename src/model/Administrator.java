package model;

import java.io.Serializable;
import service.TeacherService;
import service.TeachingRequirementService;
import view.UserInterface;

public class Administrator implements Serializable {
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    private TeacherService teacherService;
    private TeachingRequirementService teachingRequirementService;
    private UserInterface ui = UserInterface.getInstance();

    // Constructor injection is preferred for dependencies
    public Administrator(TeacherService teacherService, TeachingRequirementService teachingRequirementService) {
        this.teacherService = teacherService;
        this.teachingRequirementService = teachingRequirementService;
    }

    public void inputTeacher() {
        // Call the UI to input the teacher details
        Teacher teacher = ui.inputTeacher();
        // Add the teacher to the service instead of the DAO
        teacherService.addTeacher(teacher);
    }

    public void displayAllTeachingRequirements() {
        ui.displayAllTeachingRequirements();
    }
}
