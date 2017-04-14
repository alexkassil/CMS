import java.util.ArrayList;
public class StudentRecord {
	private String name;
	private int id; // Unique
	private ArrayList<Assignment> assignments;
	
	public StudentRecord(String name, int id) {
		this.name = name;
		this.id = id;
		assignments = new ArrayList<Assignment>();
	}
	
	public void addAssignment(Assignment assignment) {
		assignments.add(assignment);
	}
	
	public void addAssignments(ArrayList<Assignment> assignments) {
		this.assignments.addAll(assignments);
	}
	
	public void removeAssignment(Assignment assignment) {
		assignments.remove(assignment);
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setAssignments(ArrayList<Assignment> assignments) {
		this.assignments = assignments;
	}
}
