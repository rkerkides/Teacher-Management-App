package util;

public class IdGenerator {
    private static int highestTeacherId = 0;
    private static int highestTeachingRequirementId = 0;
    private static int highestTrainingSessionId = 0;

    public static synchronized int generateTeacherId() {
        return ++highestTeacherId;
    }

    public static synchronized int generateTeachingRequirementId() {
        return ++highestTeachingRequirementId;
    }

    public static synchronized int generateTrainingSessionId() {
        return ++highestTrainingSessionId;
    }

    public static void setHighestTeacherId(int id) {
        highestTeacherId = id;
    }

    public static void setHighestTeachingRequirementId(int id) {
        highestTeachingRequirementId = id;
    }

    public static void setHighestTrainingSessionId(int id) {
        highestTrainingSessionId = id;
    }
}
