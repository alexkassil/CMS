package project;
import java.util.*;

public class StudentRecord {
	private String name;
	private int id; // Unique
	private List<Assignment> assignments;
	
	public StudentRecord(String name, int id) {
		this.name = name;
		this.id = id;
		assignments = new ArrayList<Assignment>();
	}
	
	public void addAssignment(Assignment assignment) {
		assignments.add(assignment);
	}
	
	public void addAssignments(List<Assignment> assignments) {
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
	
	public List<Assignment> getAssignments() {
		return assignments;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
	
	@Override
	public String toString() {
		return "Name: " + name + " ID: " + id + " " + assignments.size() + " assignment(s)";
	}
	
	public void printAssignments() {
		for(Assignment a : assignments) {
			System.out.println(a);
		}
	}
	
	public void printGrades() {
		for(Assignment a : assignments) {
			System.out.println(a.getGrade());
		}
	}
}
