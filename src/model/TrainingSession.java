package model;

import util.IdGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
public class TrainingSession implements Serializable, Identifiable {
    @Serial
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    private final int id;
    private String day;
    private Time twoHourSlotStartTime;
    private Teacher teacher;
    private String subject;

    // Constructor
    public TrainingSession(String day, Teacher teacher, String subject, Time twoHourSlotStartTime) {
        this.id = IdGenerator.generateTrainingSessionId();
        this.day = day;
        this.teacher = teacher;
        this.subject = subject;
        this.twoHourSlotStartTime = twoHourSlotStartTime;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Time getTwoHourSlotStartTime() {
        return twoHourSlotStartTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String twoHourSlotFormatted = timeFormat.format(twoHourSlotStartTime) + " - " +
                timeFormat.format(new Time(twoHourSlotStartTime.getTime() + 7200000)); // Adding 2 hours in milliseconds for end time

        // For teacher, only include non-recursive information, like name or ID
        String teacherInfo = "ID: " + teacher.getId() + ", Name: " + teacher.getName();

        return "Training Session Information:\n" +
                "  ID: " + id + "\n" +
                "  Day of the Week: " + day + "\n" +
                "  Teacher: {" + teacherInfo + "}\n" +
                "  Subject: " + subject + "\n" +
                "  Two Hour Slot Start Time: " + twoHourSlotFormatted;
    }
}
