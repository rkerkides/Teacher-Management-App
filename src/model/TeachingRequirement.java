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
}
