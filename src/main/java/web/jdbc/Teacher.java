package web.jdbc;

public class Teacher {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private int courseId;
	private String courseName;

	public Teacher(int id, String firstName, String lastName, String email, int courseId, String courseName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.courseId = courseId;
		this.courseName = courseName;
	}

	public Teacher(int id, String firstName, String lastName, String courseName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.courseName = courseName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int id) {
		this.courseId = id;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String name) {
		this.courseName = name;
	}
	
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", courseId=" + courseId + ", courseName=" + courseName + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
}