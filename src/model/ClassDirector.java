package model;

import java.io.Serializable;
import java.util.List;

public class ClassDirector implements Serializable {
    List<TeachingRequirement> requirements;

    public List<TeachingRequirement> viewTeachingRequirements(){
        // Alternative implementation would involve a void method that prints the requirements to the console
        return requirements;
    }

    public void inputTeachingRequirement(TeachingRequirement requirement){
        // Add requirement to list
        requirements.add(requirement);
    }
}

