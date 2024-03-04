package model;

import java.io.Serializable;
import java.util.Date;
public class TrainingSession implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L; // ensures class version compatibility
    private int id;
    private Date date;
    private Teacher teacher;
    private String subject;
    private String course;

    // Constructor
    public TrainingSession(int id, Date date, Teacher teacher, String subject, String course) {
        this.id = id;
        this.date = date;
        this.teacher = teacher;
        this.subject = subject;
        this.course = course;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "id=" + id +
                ", date=" + date +
                ", teacher=" + teacher +
                ", subject='" + subject + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}
