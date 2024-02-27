import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ClassDirector {

    private Administrator administrator;
    List<TeachingRequirement> requirements;


    public ClassDirector(Administrator administrator){
        this.administrator = administrator;
    }


    public void viewTeachingRequirement(String filename){
        administrator.displayAllTeachingRequirements(filename);

    }

    public void inputTeachingRequirement(String filename, List<TeachingRequirement> requirements){
        this.requirements = requirements;
        writeTeachingRequirement(filename); // Writes requirements into a file
    }

    public void writeTeachingRequirement(String filename) { // write method
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for(TeachingRequirement requirement : requirements){
                writer.write("Requirement ID: " + requirement.id + "\n");
                writer.write("Subject: " + requirement.subject + "\n");
                writer.write("QualificationsRequired: " + String.join("; ", requirement.qualificationsRequired) + "\n");
                writer.write("CourseNames: " + String.join("; ", requirement.courseNames) + "\n");
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

