package model;

import util.IdGenerator;

import java.io.Serializable;

public class TeachingRequirement implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    private final int id;
    String subject;
    String[] qualificationsRequired;

    public TeachingRequirement(String subject, String[] qualificationsRequired) {
        this.id = IdGenerator.generateTeachingRequirementId();
        this.subject = subject;
        this.qualificationsRequired = qualificationsRequired;
    }

    @Override
    public int getId() {
        return id;
    }
    
    public String getSubject () {
    	return subject;
    }
    
    public void setSubject (String subject) {
    	this.subject = subject;
    }
    
    public String [] getQualificationsRequired (){
    	return qualificationsRequired;
    }
    
    public void setQualificationsRequired (String [] qualificationsRequired) {
    	this.qualificationsRequired = qualificationsRequired;
    }
    
    @Override
    public String toString() {
    	String display = "ID " + id + ": " + subject + (" {Qualifications Required: ");
    	for (String qual : qualificationsRequired) {
    		display += qual + ", ";
    	}
    	display += "}";
        return display;
    }
}
