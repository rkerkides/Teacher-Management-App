import java.util.Date;

public class TeachingRequirement {
	private int id;
	private Date time;
	private String subject;
	private String [] qualificationsRequired;
	private String [] courseNames;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String[] getQualificationsRequired() {
		return qualificationsRequired;
	}

	public void setQualificationsRequired(String[] qualificationsRequired) {
		this.qualificationsRequired = qualificationsRequired;
	}

	public String[] getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(String[] courseNames) {
		this.courseNames = courseNames;
	}

	public TeachingRequirement (int id, String subject, String [] qualificationsRequired, String [] courseNames, Date time) {
		this.id = id;
		this.subject = subject;
		this.qualificationsRequired = qualificationsRequired;
		this.courseNames = courseNames;
		this.time = time;
	}
	
	// Not sure if required
	public TeachingRequirement (int id, String subject, String  qualificationRequired, String [] courseNames, Date time) {
		this.id = id;
		this.subject = subject;
		this.qualificationsRequired = new String [1];
		qualificationsRequired[0] = qualificationRequired;
		this.courseNames = courseNames;
		this.time = time;
	}
	
	// Not sure if required
	public TeachingRequirement (int id, String subject, String [] qualificationsRequired, String courseName, Date time) {
		this.id = id;
		this.subject = subject;
		this.qualificationsRequired = qualificationsRequired;
		this.courseNames = new String [1];
		courseNames[0] = courseName;
		this.time = time;
	}
	
	// Not sure if required
	public TeachingRequirement (int id, String subject, String qualificationRequired, String courseName, Date time) {
		this.id = id;
		this.subject = subject;
		this.qualificationsRequired = new String [1];
		qualificationsRequired[0] = qualificationRequired;
		this.courseNames = new String [1];
		courseNames[0] = courseName;
		this.time = time;
	}
}


