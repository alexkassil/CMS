package project;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Assignment implements Cloneable {
	private int id;
	private Date dueDate;
	private int points;
	private double score;
	private String name;
	private assignmentType type;

	// Constructors
	public Assignment(int id, Date dueDate, int points, String name, assignmentType type) {
		this.id = id;
		this.dueDate = dueDate;
		this.points = points;
		this.name = name;
		this.type = type;
		score = -1;
	}
	
	public Assignment(int id, int points, String name, assignmentType type) {
		this(id, new Date(), points, name, type);
	}
	
	public Assignment(int id) {
		this(id, new Date(), 0, "DEFAULT", assignmentType.HOMEWORK);
	}
	
	// Getters
	public Date getDueDate() {
		return dueDate;
	}
	
	public int getPoints() {
		return points;
	}
	
	public double getScore() {
		return score;
	}
	
	public assignmentType getAssignmentType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	// Setters
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public void setassignmentType(assignmentType type) {
		this.type = type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String date = dateFormat.format(dueDate);
		return id + " Assignment " + name + " is " + type + ". It is due " + date +
				" and it's worth " + points + " point(s).";
	}
	
	public String getGrade() {
		return score + " out of " + points + " points";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Assignment assignmentClone = (Assignment)super.clone();
		assignmentClone.setID(this.getID());
		assignmentClone.setDueDate((Date)(this.getDueDate().clone()));
		return assignmentClone;
	}
}
