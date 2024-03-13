package model;

import util.IdGenerator;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Teacher implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    private final int id;
    private String name;
    private List<Time> twoHourSlotAvailabilities;
    private List<String> daysOfWeekAvailable;
    private List<String> qualifications;
    private String experience;
    private List<String> canTeach;
    private List<TrainingSession> trainingSessions;

    // Modified Short Constructor
    public Teacher(String name, String experience) {
        this.id = IdGenerator.generateTeacherId(); // Use IdGenerator for ID
        this.name = name;
        this.twoHourSlotAvailabilities = new ArrayList<>();
        this.qualifications = new ArrayList<>();
        this.experience = experience;
        this.canTeach = new ArrayList<>();
        this.trainingSessions = new ArrayList<>();
    }

    // Modified Constructor
    // Removed the ID parameter since ID should not be set externally
    public Teacher(String name, List<Time> twoHourSlotAvailabilities, List<String> qualifications, String experience,
                   List<String> canTeach, List<TrainingSession> trainingSessions, List<String> daysOfWeekAvailable) {
        this.id = IdGenerator.generateTeacherId(); // Use IdGenerator for ID
        this.name = name;
        this.twoHourSlotAvailabilities = twoHourSlotAvailabilities;
        this.qualifications = qualifications;
        this.experience = experience;
        this.canTeach = canTeach;
        this.trainingSessions = trainingSessions;
        this.daysOfWeekAvailable = daysOfWeekAvailable;
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

    public List<Time> getAvailabilities() {
        return twoHourSlotAvailabilities;
    }

    public void setAvailabilities(List<Time> twoHourSlotAvailabilities) {
        this.twoHourSlotAvailabilities = twoHourSlotAvailabilities;
    }

    public List<String> getDaysOfWeekAvailable() {
        return daysOfWeekAvailable;
    }

    public void setDaysOfWeekAvailable(List<String> daysOfWeekAvailable) {
        this.daysOfWeekAvailable = daysOfWeekAvailable;
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

    public void addTrainingSession(TrainingSession trainingSession) {
        trainingSessions.add(trainingSession);
    }

    public void removeTrainingSession(TrainingSession trainingSession) {
        trainingSessions.remove(trainingSession);
    }
    
    // check this
    public String displayAvailabilities() {
        return "Teacher{" +
                "id=" + id +
                ", twoHourSlotAvailabilities=" + twoHourSlotAvailabilities +
                '}';
    }

    @Override
    public String toString() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        // Process each availability slot to format it into "start time - end time"
        String availabilitiesFormatted = twoHourSlotAvailabilities.stream()
                .map(time -> {
                    // Start time as String
                    String startTimeStr = timeFormat.format(time);

                    // Calculate end time by adding 2 hours (7200000 ms) to the start time
                    Time endTime = new Time(time.getTime() + 7200000); // Adding 2 hours in milliseconds
                    String endTimeStr = timeFormat.format(endTime);

                    return startTimeStr + " - " + endTimeStr;
                })
                .collect(Collectors.joining(", "));

        String qualificationsFormatted = String.join(", ", qualifications);
        String canTeachFormatted = String.join(", ", canTeach);

        // Update for training sessions to only include time slot and day of the week
        String trainingSessionsFormatted = trainingSessions.stream()
                .map(session -> "Day of the Week: " + session.getDay() +
                        ", Time Slot: " + timeFormat.format(session.getTwoHourSlotStartTime()) + " - " +
                        timeFormat.format(new Time(session.getTwoHourSlotStartTime().getTime() + 7200000)))
                .collect(Collectors.joining("; "));

        return "Teacher Information:\n" +
                "  Name: " + name + "\n" +
                "  ID: " + id + "\n" +
                "  Experience: " + experience + "\n" +
                "  Availabilities: [" + availabilitiesFormatted + "]\n" +
                "  Days of Week Available: " + daysOfWeekAvailable + "\n" +
                "  Qualifications: [" + qualificationsFormatted + "]\n" +
                "  Can Teach: [" + canTeachFormatted + "]\n" +
                "  Training Sessions: [" + trainingSessionsFormatted + "]";
    }
}
