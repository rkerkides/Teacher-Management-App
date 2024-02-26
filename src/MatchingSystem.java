import java.util.*;

public class MatchingSystem {
	
	// Takes in a requirement and an Array of teachers and matches all relevant teachers to the req
	public static List <Teacher> findTeachers (TeachingRequirement req, Teacher [] teachers) {
		// Will store the list of suitable teachers
		List <Teacher> suitableTeachers = new ArrayList <Teacher> ();
		
		// Loops through every teacher in the array
		for (int i = 0; i < teachers.length; i++) {
			
			// Checks if the teacher can teach the subject
			if (teachers[i].getCanTeach.contains(req.getSubject())) {
				
				// Loops through the teacher's availability
				for (int j = 0; j < teachers[i].getAvailabilities.length; j++) {
					
					// Checks if teacher availability matches req time
					if (req.getTime().equals(teachers[i].getAvailabilities[j])) {
						
						// Adds the suitable teacher to the list and breaks out of the loop
						suitableTeachers.add(teachers[i]);
						break;
					}
				}
			}
		}
		return suitableTeachers;
	}
}
