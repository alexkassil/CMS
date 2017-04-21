package project;
import java.util.*;

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
		if(!assignments.contains(assignment)) {
			assignments.add(assignment);
		}
	}
	
	public void printIDs() {
		for(Assignment a : assignments) {
			System.out.println(a.getName() + " " + a.getID());
		}
	}
	
	public void addAssignments(ArrayList<Assignment> assignments) {
		for(Assignment assignment : assignments) {
			addAssignment(assignment);
		}
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
	
	@Override
	public String toString() {
		return id + " " + name;
	}
	
	public String studentRecordInfo() {
		return "Name: " + name + " ID: " + id + " " + assignments.size() + " assignment(s)";
	}
	
	@Override
	public boolean equals(Object otherRecord) {
		if(!(otherRecord instanceof StudentRecord)) {
			return false;
		}
		
		return ((StudentRecord)otherRecord).getID() == (this.getID());
	}
	
	public void printAssignments() {
		for(Assignment a : assignments) {
			System.out.println(a);
			System.out.println(a.getGrade());
		}
	}
	
	public void printGrades() {
		for(Assignment a : assignments) {
			System.out.println(a.getGrade());
		}
	}
}
