
import java.util.Date;

public class Assignment {

	public enum assignmentType {
		HOMEWORK, QUIZ, TEST
	}
	
	private Date dueDate;
	private int points;
	private int score;
	private String name;
	private assignmentType type;

	// Constructors
	public Assignment(Date dueDate, int points, String name, assignmentType type) {
		this.dueDate = dueDate;
		this.points = points;
		this.name = name;
		this.type = type;
		score = -1;
	}
	
	public Assignment(int points, String name, assignmentType type) {
		this(new Date(), points, name, type);
	}
	
	public Assignment() {
		this(new Date(), 0, "DEFAULT", assignmentType.HOMEWORK);
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
	
	public assignmentType getAssignmentType() {
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
	
	public void setassignmentType(assignmentType type) {
		this.type = type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Assignment " + name + " is " + type + ", is due " + dueDate.toString() +
				", and is worth " + points + " point(s)";
	}
}
