import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    private int id;
    private String name;
    private List<Date> availabilities;
    private List<String> qualifications;
    private String experience;
    private List<String> canTeach; // Assuming Subject is a class you have defined elsewhere
    private List<TrainingSession> trainingSessions;

    // Constructor
    public Teacher(int id, String name, List<Date> availabilities, List<String> qualifications, String experience, List<String> canTeach, List<TrainingSession> trainingSessions) {
        this.id = id;
        this.name = name;
        this.availabilities = availabilities;
        this.qualifications = qualifications;
        this.experience = experience;
        this.canTeach = canTeach;
        this.trainingSessions = trainingSessions;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Date> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Date> availabilities) {
        this.availabilities = availabilities;
    }

    public List<String> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<String> qualifications) {
        this.qualifications = qualifications;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<String> getCanTeach() {
        return canTeach;
    }

    public void setCanTeach(List<String> canTeach) {
        this.canTeach = canTeach;
    }

    public List<TrainingSession> getTrainingSessions() {
        return trainingSessions;
    }

    public void setTrainingSessions(List<TrainingSession> trainingSessions) {
        this.trainingSessions = trainingSessions;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", availabilities=" + availabilities +
                ", qualifications=" + qualifications +
                ", experience='" + experience + '\'' +
                ", canTeach=" + canTeach +
                ", trainingSessions=" + trainingSessions +
                '}';
    }
}