package service;

import dao.GenericDAO;
import model.TeachingRequirement;

import java.util.List;
import java.util.Optional;

public class TeachingRequirementService {
    private final GenericDAO<TeachingRequirement> teachingRequirementDAO;

    public TeachingRequirementService(GenericDAO<TeachingRequirement> teachingRequirementDAO) {
        this.teachingRequirementDAO = teachingRequirementDAO;
    }

    public void addTeachingRequirement(TeachingRequirement teachingRequirement) {
        teachingRequirementDAO.addEntity(teachingRequirement);
    }

    public List<TeachingRequirement> getAllTeachingRequirements() {
        // Directly use the list returned by getAllEntities
        return teachingRequirementDAO.getAllEntities();
    }

    public Optional<TeachingRequirement> getTeachingRequirement(int id) {
        // Return an Optional to gracefully handle null values
        return teachingRequirementDAO.getEntity(id);
    }

    public boolean updateTeachingRequirement(TeachingRequirement teachingRequirement) {
        // Check if the teaching requirement exists before updating
        if (getTeachingRequirement(teachingRequirement.getId()).isPresent()) {
            teachingRequirementDAO.updateEntity(teachingRequirement);
            return true;
        } else {
            // Log or handle the case where the teaching requirement does not exist
            return false;
        }
    }
}

