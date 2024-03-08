package model;

import util.IdGenerator;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Teacher implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    private final int id;
    private String name;
    private List<Date> availabilities;
    private List<String> qualifications;
    private String experience;
    private List<String> canTeach;
    private List<TrainingSession> trainingSessions;

    // Modified Short Constructor
    public Teacher(String name, String experience) {
        this.id = IdGenerator.generateTeacherId(); // Use IdGenerator for ID
        this.name = name;
        this.availabilities = new ArrayList<>();
        this.qualifications = new ArrayList<>();
        this.experience = experience;
        this.canTeach = new ArrayList<>();
        this.trainingSessions = new ArrayList<>();
    }

    // Modified Constructor
    // Removed the ID parameter since ID should not be set externally
    public Teacher(String name, List<Date> availabilities, List<String> qualifications, String experience, List<String> canTeach, List<TrainingSession> trainingSessions) {
        this.id = IdGenerator.generateTeacherId(); // Use IdGenerator for ID
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String availabilitiesFormatted = availabilities.stream()
                .map(dateFormat::format)
                .collect(Collectors.joining(", "));

        String qualificationsFormatted = String.join(", ", qualifications);
        String canTeachFormatted = String.join(", ", canTeach);
        String trainingSessionsFormatted = trainingSessions.stream()
                .map(TrainingSession::toString) // Assuming TrainingSession has a meaningful toString implementation
                .collect(Collectors.joining("; "));

        return "Teacher Information:\n" +
                "  Name: " + name + "\n" +
                "  ID: " + id + "\n" +
                "  Experience: " + experience + "\n" +
                "  Availabilities: [" + availabilitiesFormatted + "]\n" +
                "  Qualifications: [" + qualificationsFormatted + "]\n" +
                "  Can Teach: [" + canTeachFormatted + "]\n" +
                "  Training Sessions: [" + trainingSessionsFormatted + "]";
    }
}
