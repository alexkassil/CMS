package application;

import java.util.Date;

public class Assignment {
	private Date dueDate;
	private int points;
	private int score;
	private String name;
	// TODO Enum for assignmentTypes 
	private int type;

	// Constructors
	public Assignment(Date dueDate, int points, String name, int type) {
		this.dueDate = dueDate;
		this.points = points;
		this.name = name;
		this.type = type;
		score = 0;
	}
	
	public Assignment(int points, String name, int type) {
		this(new Date(), points, name, type);
	}
	
	// Getters
	public Date getDueDate() {
		return dueDate;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	// Setters
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
