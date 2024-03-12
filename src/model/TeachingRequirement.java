package model;

import util.IdGenerator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
//@ Abs changes to Teaching requirements, including new variables
public class TeachingRequirement implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L; 
    private final int id;
    private String subject;
    private List<String> qualificationsRequired; 
    private String experience;
    private String educationLevel;

    public TeachingRequirement(String subject, List<String> qualificationsRequired, String experience, String educationLevel) {
        this.id = IdGenerator.generateTeachingRequirementId();
        this.subject = subject;
        this.qualificationsRequired = qualificationsRequired;
        this.experience = experience;
        this.educationLevel = educationLevel;
    }

    // Getters and Setters for new variables
    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }
    
    // Existing getters and setters, no change needed

    @Override
    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getQualificationsRequired() {
        return qualificationsRequired;
    }

    public void setQualificationsRequired(List<String> qualificationsRequired) {
        this.qualificationsRequired = qualificationsRequired;
    }
    
    @Override
    public String toString() {
        String display = "ID " + id + ": " + subject;
        display += " {Qualifications Required: " + Arrays.toString(qualificationsRequired.toArray()) + ", ";
        display += "Experience: " + experience + ", ";
        display += "Education Level: " + educationLevel + "}";
        return display;
    }
}
