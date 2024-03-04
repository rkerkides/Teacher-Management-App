package dao;

import model.Identifiable;

import java.util.List;
import java.util.Optional;

/**
 * The GenericDAO interface defines a standard set of CRUD operations for entities.
 * Entities are required to be identifiable, thus extending the Identifiable interface.
 * This design allows for flexible implementations of the DAO pattern, enabling
 * seamless transition between different forms of persistence mechanisms such as
 * file-based storage or database integration.

 * By abstracting the CRUD operations at the interface level, this design allows
 * future implementations to focus on the specifics of the persistence mechanism
 * (e.g., SQL databases, NoSQL databases, or file systems) without changing the
 * service layer that interacts with this DAO.
 *
 * @param <T> the type of the entity. It must extend Identifiable to ensure that
 *            it can be uniquely identified, typically by an ID.
 */
public interface GenericDAO<T extends Identifiable> {
    void addEntity(T entity);
    Optional<T> getEntity(int id);
    void updateEntity(T entity);
    void removeEntity(T entity);
    List<T> getAllEntities();
}
