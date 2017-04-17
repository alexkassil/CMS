package project;
import java.util.*;

public class FinalProject {

	public static void main(String[] args) throws CloneNotSupportedException {
		Assignment assignment0 = new Assignment(0, 7, "HW 0", assignmentType.HOMEWORK);
		Assignment assignment1 = new Assignment(1, 15, "HW 1", assignmentType.HOMEWORK);
		Assignment assignment2 = new Assignment(2, new Date(), 20, "QUIZ 1", assignmentType.QUIZ);
		Assignment assignment3 = new Assignment(3, 100, "Test 1", assignmentType.TEST);
		Assignment[] assignments = {assignment0, assignment1, assignment2, assignment3};
		
		List<Assignment> assignmentsList = new ArrayList(Arrays.asList(assignments));
		
		StudentRecord sr1 = new StudentRecord("Charles Smith", 000);		
		StudentRecord sr2 = new StudentRecord("Charles Smith", 001);		
		StudentRecord sr3 = new StudentRecord("George MacDonald", 002);
		
		List<StudentRecord> records = new ArrayList<StudentRecord>();
		records.add(sr1);
		records.add(sr2);
		records.add(sr3);
		
		Course Math54 = new Course("MATH54", records);
		Math54.addAssignments(assignmentsList);
		
		Math54.setHomeworkWeight(.2);
		Math54.setQuizWeight(.3);
		Math54.setTestWeight(.5);

		System.out.println(Math54);
		System.out.println(Math54.weights());
		Math54.assignAll();
		
		Math54.gradeAssignment(sr1, 0, 6);
		Math54.gradeAssignment(sr1, 1, 14);
		Math54.gradeAssignment(sr1, 2, 19);
		Math54.gradeAssignment(sr1, 3, 99);
		
		sr1.printGrades();
		System.out.println(Math54.getGrade(sr1));
		
		
	}

}
