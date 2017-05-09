package project;
import java.util.*;

public class Course {
	private String courseID;
	private ArrayList<StudentRecord> records;
	private ArrayList<Assignment> assignments;
	private double homeworkWeight = 0;
	private double quizWeight = 0;
	private double testWeight = 0;

	public Course(String courseID,ArrayList<StudentRecord> records) {
		this(courseID, records, 0, 0, 0);
	}

	public Course(String courseID) {
		this(courseID, new ArrayList<StudentRecord>());
	}
	
	public Course(String courseID, ArrayList<StudentRecord> records, double hwWeight, double quizWeight, double testWeight) {
		this.courseID = courseID;
		this.records = records;
		this.homeworkWeight = hwWeight;
		this.quizWeight = quizWeight;
		this.testWeight = testWeight;
		assignments = new ArrayList<Assignment>();
	}
	
	public Course(String courseID, double hwWeight, double quizWeight, double testWeight) {
		this(courseID, new ArrayList<StudentRecord>(), hwWeight, quizWeight, testWeight);
	}

	
	
	public void gradeAssignment(StudentRecord record, int assignmentID, double score) {
		List<Assignment> a = record.getAssignments();
		for(Assignment assignment: a) {
			if(assignment.getID() == assignmentID) {
				assignment.setScore(score);
			}
		}
	}
	
	public void gradeAssignmentPercent(StudentRecord record, int assignmentID, double percent) {
		List<Assignment> a = record.getAssignments();
		for(Assignment assignment: a) {
			if(assignment.getID() == assignmentID) {
				assignment.setScore(percent * assignment.getPoints());
			}
		}
	}

	public double getGrade(StudentRecord record) {
		return getHomeworkGradeTotal(record) * homeworkWeight + getQuizGradeTotal(record) * quizWeight
				+ getTestGradeTotal(record) * testWeight;
	}

	public double getHomeworkGradeTotal(StudentRecord record) {
		return getTotalScores(record, assignmentType.HOMEWORK) / getTotalPoints(record, assignmentType.HOMEWORK);
	}

	public double getQuizGradeTotal(StudentRecord record) {
		return getTotalScores(record, assignmentType.QUIZ) / getTotalPoints(record, assignmentType.QUIZ);
	}

	public double getTestGradeTotal(StudentRecord record) {
		return getTotalScores(record, assignmentType.TEST) / getTotalPoints(record, assignmentType.TEST);
	}

	public double getTotalPoints(StudentRecord record, assignmentType type) {
		List<Assignment> a = record.getAssignments();
		double points = 0;

		for (Assignment assignment : a) {
			if (assignment.getAssignmentType() == type) {
				points += assignment.getPoints();
			}
		}

		return points;
	}

	private double getTotalScores(StudentRecord record, assignmentType type) {

		ArrayList<Assignment> a = record.getAssignments();
		double scores = 0;

		for (Assignment assignment : a) {
			if (assignment.getAssignmentType() == type && assignment.getScore() > 0) {
				scores += assignment.getScore();
			}
		}

		return scores;
	}

	public void assign(Assignment assignment) throws CloneNotSupportedException {
		for (StudentRecord record : records) {
			record.addAssignment((Assignment)(assignment.clone()));
		}
	}

	public void assign(ArrayList<Assignment> assignments) throws CloneNotSupportedException {
		for(Assignment assignment : assignments) {
			assign(assignment);
		}
	}
	
	public void addRecord(StudentRecord record) {
		records.add(record);
	}

	public void addAssignment(Assignment assignment) {
		assignments.add(assignment);
	}

	public void addAssignments(ArrayList<Assignment> assignments) {
		this.assignments.addAll(assignments);
	}

	public void assignAll() throws CloneNotSupportedException {
		assign(assignments);
	}

	public String getCourseID() {
		return courseID;
	}

	public ArrayList<StudentRecord> getRecords() {
		return records;
	}

	public ArrayList<Assignment> getAssignments() {
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

	public void setRecords(ArrayList<StudentRecord> records) {
		this.records = records;
	}

	public void setAssignments(ArrayList<Assignment> assignments) {
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
		return courseID;
	}
	public String courseInfo() {
		return courseID + " " + assignments.size() + " assignment(s) and " + records.size() + " record(s)";
	}

	public String weights() {
		String weights = String.format("Homework Weight: %.2f\nQuiz Weight: %.2f\nTestWeight %.2f", homeworkWeight, quizWeight, testWeight);
		return weights;
	}
	
	@Override
	public boolean equals(Object otherCourse) {
		if(!(otherCourse instanceof Course)) {
			return false;
		}
		
		return ((Course)otherCourse).getCourseID().equals(this.getCourseID());
	}
}
