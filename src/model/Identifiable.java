package model;

/**
 * Defines an interface for objects that can be uniquely identified within the system.
 */
public interface Identifiable {
    /**
     * Retrieves the unique identifier for this object.
     *
     * @return the unique ID of this object
     */
    int getId();
}
