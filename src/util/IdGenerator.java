package util;

/**
 * A utility class for generating unique identifiers for different types of entities within the system.
 * It maintains the highest ID generated for each entity type to ensure uniqueness.
 */
public class IdGenerator {
    private static int highestTeacherId = 0; // Tracks the highest ID generated for Teacher entities
    private static int highestTeachingRequirementId = 0; // Tracks the highest ID generated for TeachingRequirement entities
    private static int highestTrainingSessionId = 0; // Tracks the highest ID generated for TrainingSession entities

    /**
     * Generates a unique ID for a Teacher entity.
     *
     * @return the generated unique ID
     */
    public static synchronized int generateTeacherId() {
        return ++highestTeacherId;
    }

    /**
     * Generates a unique ID for a TeachingRequirement entity.
     *
     * @return the generated unique ID
     */
    public static synchronized int generateTeachingRequirementId() {
        return ++highestTeachingRequirementId;
    }

    /**
     * Generates a unique ID for a TrainingSession entity.
     *
     * @return the generated unique ID
     */
    public static synchronized int generateTrainingSessionId() {
        return ++highestTrainingSessionId;
    }

    /**
     * Updates the highest ID recorded for Teacher entities.
     * This can be used to synchronize the ID generation with external storage systems.
     *
     * @param id the highest ID known for Teacher entities
     */
    public static void setHighestTeacherId(int id) {
        highestTeacherId = id;
    }

    /**
     * Updates the highest ID recorded for TeachingRequirement entities.
     * This can be used to synchronize the ID generation with external storage systems.
     *
     * @param id the highest ID known for TeachingRequirement entities
     */
    public static void setHighestTeachingRequirementId(int id) {
        highestTeachingRequirementId = id;
    }

    /**
     * Updates the highest ID recorded for TrainingSession entities.
     * This can be used to synchronize the ID generation with external storage systems.
     *
     * @param id the highest ID known for TrainingSession entities
     */
    public static void setHighestTrainingSessionId(int id) {
        highestTrainingSessionId = id;
    }
}
