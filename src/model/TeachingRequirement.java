package model;

import util.IdGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
// Represents a 2-hour semester-long slot for a teacher to teach a subject
public class TeachingRequirement implements Serializable, Identifiable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int id;
    private String subject;
    private List<String> qualificationsRequired; 
    private String experience;
    private Teacher teacher;
    private Time startTime;
    private List<String> daysOfWeek;

    public TeachingRequirement(String subject, List<String> qualificationsRequired, String experience,
                               Time startTime, List<String> daysOfWeek) {
        this.id = IdGenerator.generateTeachingRequirementId();
        this.subject = subject;
        // Ensure that the lists are not null by initializing to empty lists if null is passed
        this.qualificationsRequired = qualificationsRequired != null ? qualificationsRequired : new ArrayList<>();
        this.experience = experience;
        this.teacher = null; // Initialized to null to indicate no teacher assigned yet
        this.startTime = startTime;
        this.daysOfWeek = daysOfWeek != null ? daysOfWeek : new ArrayList<>();
    }

    // Getters and Setters
    @Override
    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getQualificationsRequired() {
        return qualificationsRequired;
    }

    public List<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        // Using String.join to handle list conversions, ensuring no null lists
        String qualificationsFormatted = qualificationsRequired.isEmpty() ? "None specified" :
                String.join(", ", qualificationsRequired);
        String daysOfWeekFormatted = daysOfWeek.isEmpty() ? "Not specified" :
                String.join(", ", daysOfWeek);
        String teacherName = teacher == null ? "Unassigned" : teacher.getName();

        return "Teaching Requirement Information:\n" +
                "  ID: " + id + "\n" +
                "  Subject: " + subject + "\n" +
                "  Days of Week: " + daysOfWeekFormatted + "\n" +
                "  Time Slot: " + (startTime != null ? startTime + " to " + new Time(startTime.getTime() + 7200000) :
                "Not specified") + "\n" +
                "  Qualifications Preferred: [" + qualificationsFormatted + "]\n" +
                "  Experience Preferred: " + experience + " years\n" +
                "  Teacher Assigned: " + teacherName + "\n";
    }
}
