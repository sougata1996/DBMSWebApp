package web.jdbc;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private List<Integer> courseIds = new ArrayList<>();
	private List<String> courseNames = new ArrayList<>();

	public Teacher(int id, String firstName, String lastName, String email, Integer courseId, String courseName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.courseIds.add(courseId);
		this.courseNames.add(courseName);
	}

	public Teacher(int id, String firstName, String lastName, String email, List<Integer> courseIds, List<String> courseNames) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.courseIds = courseIds;
		this.courseNames = courseNames;
	}
	
	public Teacher(int id, String firstName, String lastName, List<String> courseNames, List<Integer> courseIds) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.courseNames = courseNames;
		this.courseIds = courseIds;
	}
	
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Integer getCourseId(int index) {
		return courseIds.get(index);
	}

	public String getCourseName(int index) {
		return courseNames.get(index);
	}
	
	public List<Integer> getCourseIds() {
		return courseIds;
	}

	public List<String> getCourseNames() {
		return courseNames;
	}
}