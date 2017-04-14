import java.util.*;

public class Course {
	private String courseID;
	private List<StudentRecord> records;
	private List<Assignment> assignments;
	private double homeworkWeight = 0;
	private double quizWeight = 0;
	private double testWeight = 0;

	public Course(String courseID, List<StudentRecord> records) {
		this.courseID = courseID;
		this.records = records;
		assignments = new ArrayList<Assignment>();
	}
	
	public Course(String courseID) {
		this(courseID, new ArrayList<StudentRecord>());
	}
	
	// TODO
	
	public double getGrade(StudentRecord record) {
		System.out.println("TODO");
		return 0;
	}
	
	// TODO
	
	public void assign(Assignment assignment) {
		System.out.println("TODO");

	}
	
	// TODO
	
	public void assign(List<Assignment> assignments) {
		System.out.println("TODO");

	}
	
	public void addAssignment(Assignment assignment) {
		assignments.add(assignment);
	}
	
	public void addAssignments(List<Assignment> assignments) {
		this.assignments.addAll(assignments);
	}
	
	public void assignAll() {
		assign(assignments);
	}
	
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

	@Override
	public String toString() {
		return courseID + " " + assignments.size() + " assignment(s) and " + records.size() + 
				" record(s)";
	}
	
	public String weights() {
		return "Homework Weight: " + homeworkWeight + "\nQuiz Weight: " + quizWeight + 
				"\nTest Weight:" + testWeight;
	}
}
