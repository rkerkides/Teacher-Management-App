package dao;

import model.Identifiable;
import java.io.*;
import java.util.*;

// FileGenericDAO implements the GenericDAO interface using file storage
public class FileGenericDAO<T extends Identifiable> implements GenericDAO<T> {
    private Map<Integer, T> entities = new HashMap<>();
    private final String filePath;

    // Constructor initializes the DAO with a specific file path for storage
    public FileGenericDAO(String filePath) {
        this.filePath = filePath;
        loadEntities(); // Load existing entities from file at initialization
    }

    @Override
    public void addEntity(T entity) {
        entities.put(entity.getId(), entity);
        saveEntities(); // Save changes to file
    }

    @Override
    public Optional<T> getEntity(int id) {
        // Returns an Optional, allowing for a more nuanced handling of null values
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public void updateEntity(T entity) {
        // Update only if the entity exists, ensuring data integrity
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
        // Return a new list to avoid external modifications to the internal map
        return new ArrayList<>(entities.values());
    }

    // Saves the current state of entities to the file
    private void saveEntities() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads entities from the file, initializing the internal map
    @SuppressWarnings("unchecked")
    private void loadEntities() {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                entities = (Map<Integer, T>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
