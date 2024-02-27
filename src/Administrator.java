import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Administrator {

    List<Teacher> teachers; // List for storing teachers
    List<TeachingRequirement> requirements; // List for accessing requierments for teaching


    public void inputTeacher (List<Teacher> teachers){
        this.teachers = teachers;
        writeTeachersToFile("teachers.txt");
    }



    public void writeTeachersToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for (Teacher teacher : teachers){
                writer.write("ID: " + teacher.id + "\n");
                writer.write("name: " + teacher.name + "\n");
                writer.write("availabilities: " + Arrays.toString(teacher.availabilities) + "\n");
                writer.write("qualifications: " + Arrays.toString(teacher.qualifications) + "\n");
                writer.write("experience: " + teacher.experience + "\n");
                writer.write("canTeach: " + teacher.canTeach + "\n");
                writer.write("trainingSessions: " + teacher.trainingSessions + "\n");
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayAllTeachingRequirements(String fileName){
        requirements = new ArrayList<>(); // Initialize the list
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
           while((line = reader.readLine()) != null){
               String[] parts = line.split(",");
               TeachingRequirement requirement = new TeachingRequirement();
               requirement.id = Integer.parseInt(parts[0]);
               requirement.subject = parts[1];
               requirement.qualificationsRequired = parts[2].split(";"); // lets assume " ; " is used
               requirement.courseNames = parts[3].split(";");
               requirements.add(requirement);
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

