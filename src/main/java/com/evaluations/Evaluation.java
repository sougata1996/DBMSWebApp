package com.evaluations;

/**
 * Class to store the evaluation structure or info set by teacher.
 */
public class Evaluation {

	private String eval_name;
	private String eval_type;
	private int teacher_id;
	private int course_id;
	
	public Evaluation(int teacher_id, int course_id, String eval_name, String eval_type) {
		this.teacher_id = teacher_id;
		this.course_id = course_id;
		this.eval_name = eval_name;
		this.eval_type = eval_type;
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
	public int getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	
	
}
