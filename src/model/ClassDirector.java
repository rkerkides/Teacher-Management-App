package model;

import service.TeachingRequirementService;
import java.io.Serializable;

public class ClassDirector implements Serializable {
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    private transient TeachingRequirementService teachingRequirementService;

    // Constructor injection is preferred for setting the TeachingRequirementService dependency
    public ClassDirector(TeachingRequirementService teachingRequirementService) {
        this.teachingRequirementService = teachingRequirementService;
    }

    public void viewTeachingRequirements() {
        // Now this method uses the TeachingRequirementService to get and display requirements
        teachingRequirementService.getAllTeachingRequirements().forEach(System.out::println);
    }

    public void inputTeachingRequirement(TeachingRequirement requirement) {
        // This method now delegates the addition of a new TeachingRequirement to the service
        teachingRequirementService.addTeachingRequirement(requirement);
    }
}
