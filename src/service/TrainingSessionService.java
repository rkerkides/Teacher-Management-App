package service;

import dao.GenericDAO;
import model.TrainingSession;

import java.util.List;
import java.util.Optional;

public class TrainingSessionService {
    private final GenericDAO<TrainingSession> trainingSessionDAO;

    public TrainingSessionService(GenericDAO<TrainingSession> trainingSessionDAO) {
        this.trainingSessionDAO = trainingSessionDAO;
    }

    public void addTrainingSession(TrainingSession trainingSession) {
        trainingSessionDAO.addEntity(trainingSession);
    }

    public List<TrainingSession> getAllTrainingSessions() {
        // Directly use the list returned by getAllEntities
        return trainingSessionDAO.getAllEntities();
    }

    public Optional<TrainingSession> getTrainingSession(int id) {
        // Return an Optional to gracefully handle null values
        return trainingSessionDAO.getEntity(id);
    }

    public boolean updateTrainingSession(TrainingSession trainingSession) {
        // Check if the training session exists before updating
        if (getTrainingSession(trainingSession.getId()).isPresent()) {
            trainingSessionDAO.updateEntity(trainingSession);
            return true;
        } else {
            // Log or handle the case where the training session does not exist
            return false;
        }
    }

    public boolean removeTrainingSession(int id) {
        // Remove a training session by id and handle non-existent sessions
        Optional<TrainingSession> trainingSession = getTrainingSession(id);
        if (trainingSession.isPresent()) {
            trainingSessionDAO.removeEntity(trainingSession.get());
            return true;
        } else {
            // Log or handle the case where the training session does not exist
            return false;
        }
    }
}

