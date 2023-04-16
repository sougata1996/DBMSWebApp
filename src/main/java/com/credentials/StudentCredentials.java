package com.credentials;

/**
 * Stores the student credentials in this class.
 */
public class StudentCredentials {
private int id;
private String password;

public StudentCredentials (int id, String password) {
	this.id = id;
	this.password = password;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

}
