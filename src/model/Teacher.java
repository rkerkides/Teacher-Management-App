package model;

import util.IdGenerator;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private List<TeachingRequirement> teachingRequirements;

    public Teacher(String name, List<Time> twoHourSlotAvailabilities, List<String> qualifications, String experience,
                   List<String> canTeach, List<TrainingSession> trainingSessions, List<String> daysOfWeekAvailable,
                   List<TeachingRequirement> teachingRequirements) {
        this.id = IdGenerator.generateTeacherId(); // Use IdGenerator for ID
        this.name = name;
        this.twoHourSlotAvailabilities = twoHourSlotAvailabilities;
        this.qualifications = qualifications;
        this.experience = experience;
        this.canTeach = canTeach;
        this.trainingSessions = trainingSessions;
        this.daysOfWeekAvailable = daysOfWeekAvailable;
        this.teachingRequirements = teachingRequirements;
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

    public List<TeachingRequirement> getTeachingRequirements() {
        return teachingRequirements;
    }

    public void setTeachingRequirements(List<TeachingRequirement> teachingRequirements) {
        this.teachingRequirements = teachingRequirements;
    }

    public void addTeachingRequirement(TeachingRequirement teachingRequirement) {
        teachingRequirements.add(teachingRequirement);
    }

    public void removeTeachingRequirement(TeachingRequirement teachingRequirement) {
        teachingRequirements.remove(teachingRequirement);
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

        String availabilitiesFormatted = Optional.ofNullable(twoHourSlotAvailabilities).orElseGet(ArrayList::new).stream()
                .map(time -> {
                    String startTimeStr = timeFormat.format(time);
                    Time endTime = new Time(time.getTime() + 7200000); // Adding 2 hours in milliseconds
                    String endTimeStr = timeFormat.format(endTime);
                    return startTimeStr + " - " + endTimeStr;
                })
                .collect(Collectors.joining(", ", "[", "]"));

        String qualificationsFormatted = qualifications != null ? String.join(", ", qualifications) : "None";
        String canTeachFormatted = canTeach != null ? String.join(", ", canTeach) : "None";
        String daysOfWeekAvailableFormatted = daysOfWeekAvailable != null ? String.join(", ", daysOfWeekAvailable) : "None";

        String trainingSessionsFormatted = Optional.ofNullable(trainingSessions).orElseGet(ArrayList::new).stream()
                .map(session -> "Day: " + session.getDay() +
                        ", Time Slot: " + timeFormat.format(session.getTwoHourSlotStartTime()) + " - " +
                        timeFormat.format(new Time(session.getTwoHourSlotStartTime().getTime() + 7200000)))
                .collect(Collectors.joining("; ", "[", "]"));

        String teachingRequirementsFormatted = Optional.ofNullable(teachingRequirements).orElseGet(ArrayList::new).stream()
                .map(requirement -> "Subject: " + requirement.getSubject() +
                        ", Day(s): " + String.join(", ", requirement.getDaysOfWeek()) +
                        ", Time Slot: " + (requirement.getStartTime() != null ? timeFormat.format(requirement.getStartTime()) + " - " +
                        timeFormat.format(new Time(requirement.getStartTime().getTime() + 7200000)) : "Not specified"))
                .collect(Collectors.joining("; ", "[", "]"));

        return "Teacher Information:\n" +
                "  Name: " + name + "\n" +
                "  ID: " + id + "\n" +
                "  Experience: " + experience + "\n" +
                "  Semester-Long Availabilities: " + availabilitiesFormatted + "\n" +
                "  Days of Week Available: " + daysOfWeekAvailableFormatted + "\n" +
                "  Qualifications: " + qualificationsFormatted + "\n" +
                "  Can Teach: " + canTeachFormatted + "\n" +
                "  Training Sessions: " + trainingSessionsFormatted + "\n" +
                "  Teaching Requirements: " + teachingRequirementsFormatted + "\n";
    }
}
