package DAO;

import interfaces.Identifiable;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class GenericDAO<T extends Identifiable> {
    private Map<Integer, T> entities = new HashMap<>();
    private String filePath;

    public GenericDAO(String filePath) {
        this.filePath = filePath;
        loadEntities();
    }

    public Map<Integer, T> getAllEntities() {
        return entities;
    }

    public void addEntity(T entity) {
        entities.put(entity.getId(), entity);
        saveEntities();
    }

    public void removeEntity(T entity) {
        entities.remove(entity.getId());
        saveEntities();
    }

    public void updateEntity(T entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
            saveEntities();
        }
    }

    public T getEntity(int id) {
        return entities.get(id);
    }

    // Serialize the entities map to a file
    private void saveEntities() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserialize the entities map from a file
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
