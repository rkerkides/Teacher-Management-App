package model;

import java.io.Serializable;
import interfaces.Identifiable;

public class TeachingRequirement implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    int id;
    String subject;
    String[] qualificationsRequired;

    @Override
    public int getId() {
        return id;
    }
}
