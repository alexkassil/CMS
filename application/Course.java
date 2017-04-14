import java.util.*;

public class Course {
	private String courseID;
	private List<StudentRecord> records;
	private List<Assignment> assignments;
	private double homeworkWeight;
	private double quizWeight;
	private double testWeight;

	public String getCourseID() {
		return courseID;
	}

	public List<StudentRecord> getRecords() {
		return records;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public double getHomeworkWeight() {
		return homeworkWeight;
	}

	public double getQuizWeight() {
		return quizWeight;
	}

	public double getTestWeight() {
		return testWeight;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public void setRecords(List<StudentRecord> records) {
		this.records = records;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public void setHomeworkWeight(double homeworkWeight) {
		this.homeworkWeight = homeworkWeight;
	}

	public void setQuizWeight(double quizWeight) {
		this.quizWeight = quizWeight;
	}

	public void setTestWeight(double testWeight) {
		this.testWeight = testWeight;
	}

}
