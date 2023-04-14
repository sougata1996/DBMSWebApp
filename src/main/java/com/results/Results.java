package com.results;

public class Results {
private int score;
private String eval_name;
private String eval_type;
private int course_id;
private int student_id;

public Results(int score, String eval_name, String eval_type,
		int course_id, int student_id) {
	this.score = score;
	this.eval_name = eval_name;
	this.eval_type = eval_type;
	this.course_id = course_id;
	this.student_id = student_id;
}

public int getScore() {
	return score;
}

public void setScore(int score) {
	this.score = score;
}

public String getEval_name() {
	return eval_name;
}

public void setEval_name(String eval_name) {
	this.eval_name = eval_name;
}

public String getEval_type() {
	return eval_type;
}

public void setEval_type(String eval_type) {
	this.eval_type = eval_type;
}

public int getCourse_id() {
	return course_id;
}

public void setCourse_id(int course_id) {
	this.course_id = course_id;
}

public int getStudent_id() {
	return student_id;
}

public void setStudent_id(int student_id) {
	this.student_id = student_id;
}
}
