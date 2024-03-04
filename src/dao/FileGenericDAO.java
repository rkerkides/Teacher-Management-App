package dao;

import model.Identifiable;
import model.Teacher;
import model.TeachingRequirement;
import model.TrainingSession;
import util.IdGenerator; // Import the IdGenerator
import java.io.*;
import java.util.*;

public class FileGenericDAO<T extends Identifiable> implements GenericDAO<T> {
    private Map<Integer, T> entities = new HashMap<>();
    private final String filePath;
    private final Class<T> entityType;

    public FileGenericDAO(String filePath, Class<T> entityType) {
        this.filePath = filePath;
        this.entityType = entityType;
        loadEntities();
    }

    @Override
    public void addEntity(T entity) {
        entities.put(entity.getId(), entity);
        saveEntities(); // Save changes to file
    }

    @Override
    public Optional<T> getEntity(int id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public void updateEntity(T entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
            saveEntities(); // Save changes to file
        }
    }

    @Override
    public void removeEntity(T entity) {
        entities.remove(entity.getId());
        saveEntities(); // Save changes to file
    }

    @Override
    public List<T> getAllEntities() {
        return new ArrayList<>(entities.values());
    }

    private void saveEntities() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEntities() {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                entities = (Map<Integer, T>) ois.readObject();
                if (!entities.isEmpty()) {
                    int maxId = Collections.max(entities.keySet());
                    // Directly update the IdGenerator based on entityType
                    if (Teacher.class.equals(entityType)) {
                        IdGenerator.setHighestTeacherId(maxId);
                    } else if (TeachingRequirement.class.equals(entityType)) {
                        IdGenerator.setHighestTeachingRequirementId(maxId);
                    } else if (TrainingSession.class.equals(entityType)) {
                        IdGenerator.setHighestTrainingSessionId(maxId);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
