package dao;

import model.Identifiable;
import model.Teacher;
import model.TeachingRequirement;
import model.TrainingSession;
import util.IdGenerator;
import java.io.*;
import java.util.*;

/**
 * A generic Data Access Object (DAO) class for handling file-based persistence of entities that implement the Identifiable interface.
 * This class supports basic CRUD operations (Create, Read, Update, Delete) along with loading and saving entities to a file.
 *
 * @param <T> the type of entity this DAO will manage; must extend Identifiable
 */
public class FileGenericDAO<T extends Identifiable> implements GenericDAO<T> {
    private Map<Integer, T> entities = new HashMap<>();
    private final String filePath;
    private final Class<T> entityType;

    /**
     * Constructs a new FileGenericDAO with specified file path and entity type.
     *
     * @param filePath   the path of the file used for persistence
     * @param entityType the class of the type T
     */
    public FileGenericDAO(String filePath, Class<T> entityType) {
        this.filePath = filePath;
        this.entityType = entityType;
        loadEntities();
    }

    /**
     * Adds or updates an entity in the persistence file.
     *
     * @param entity the entity to be added or updated
     */
    @Override
    public void addEntity(T entity) {
        entities.put(entity.getId(), entity);
        saveEntities();
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return an Optional containing the entity if found, or an empty Optional if not found
     */
    @Override
    public Optional<T> getEntity(int id) {
        return Optional.ofNullable(entities.get(id));
    }

    /**
     * Updates an existing entity in the persistence file.
     *
     * @param entity the entity to update
     */
    @Override
    public void updateEntity(T entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
            saveEntities();
        }
    }

    /**
     * Removes an entity from the persistence file.
     *
     * @param entity the entity to be removed
     */
    @Override
    public void removeEntity(T entity) {
        entities.remove(entity.getId());
        saveEntities();
    }

    /**
     * Retrieves all entities managed by this DAO.
     *
     * @return a list of all entities
     */
    @Override
    public List<T> getAllEntities() {
        return new ArrayList<>(entities.values());
    }

    /**
     * Saves the current state of entities to the specified file.
     */
    private void saveEntities() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads entities from the specified file into memory.
     */
    @SuppressWarnings("unchecked")
    private void loadEntities() {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                entities = (Map<Integer, T>) ois.readObject();
                if (!entities.isEmpty()) {
                    int maxId = Collections.max(entities.keySet());
                    updateIdGenerator(maxId);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the highest ID in the IdGenerator based on the loaded entities.
     *
     * @param maxId the highest ID found among the loaded entities
     */
    private void updateIdGenerator(int maxId) {
        if (Teacher.class.equals(entityType)) {
            IdGenerator.setHighestTeacherId(maxId);
        } else if (TeachingRequirement.class.equals(entityType)) {
            IdGenerator.setHighestTeachingRequirementId(maxId);
        } else if (TrainingSession.class.equals(entityType)) {
            IdGenerator.setHighestTrainingSessionId(maxId);
        }
    }
}
