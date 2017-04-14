import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class FinalProject {

	public static void main(String[] args) {
		Assignment assignment1 = new Assignment();
		Assignment assignment2 = new Assignment(new Date(), 20, "QUIZ 1", assignmentType.QUIZ);
		Assignment assignment3 = new Assignment(100, "Test 1", assignmentType.TEST);
		Assignment[] assignments = {assignment1, assignment2, assignment3};
		
		StudentRecord sr1 = new StudentRecord("Charles Smith", 000);
		sr1.addAssignments(Arrays.asList(assignments));
		
		StudentRecord sr2 = new StudentRecord("Charles Smith", 001);
		sr2.setAssignments(Arrays.asList(assignments));
		
		StudentRecord sr3 = new StudentRecord("George MacDonald", 002);
		sr3.addAssignment(new Assignment(50, "QUIZ 5", assignmentType.QUIZ));
		
		StudentRecord[] records = {sr1, sr2, sr3};
		
		for(StudentRecord record: records) {
			System.out.println(record);
		}
	}

}
