package project;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class FinalProject extends Application {
	static ArrayList<Course> courses = new ArrayList<Course>();
	Label message = new Label("test");

	@Override
	public void start(Stage primaryStage) {
		// Main pane
		BorderPane pane = new BorderPane();

		// Pane for ComboBox
		FlowPane pickPane = new FlowPane();
		pickPane.setPadding(new Insets(10, 10, 10, 10));
		pickPane.setAlignment(Pos.CENTER);

		Text select = new Text(10, 10, "Select from services: ");
		ComboBox<String> cb = new ComboBox<>();
		cb.setPrefWidth(600);

		// ComboBox Options
		cb.getItems().addAll("Create Course", "Update Course", "Add Student Record", "Add Assignment", "Grade Assignment");

		// Selecting options
		cb.setOnAction(e -> {
			if (cb.getValue().equals("Create Course")) {
				pane.setCenter(createCourse());
			} else if (cb.getValue().equals("Update Course")) {
				pane.setCenter(updateCourse());
			} else if (cb.getValue().equals("Add Student Record")) {
				pane.setCenter(addStudentRecord());
			} else if (cb.getValue().equals("Add Assignment")) {
				pane.setCenter(addAssignment());
			} else if (cb.getValue().equals("Grade Assignment")) {
				pane.setCenter(gradeAssignment());
			}
			clear();
		});

		pickPane.getChildren().addAll(select, cb);

		// Message formatting
		HBox hbMessage = new HBox(10);
		hbMessage.setAlignment(Pos.CENTER);
		hbMessage.getChildren().add(message);
		message.setPadding(new Insets(5, 10, 10, 5));
		message.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 16));

		pane.setTop(pickPane);
		pane.setBottom(hbMessage);

		Scene scene = new Scene(pane, 750, 750);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Kassil's Course Management Service");
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			primaryStage.close();
			for(Course c : courses) {
				System.out.println(c.courseInfo());
				System.out.println(c.weights());
			}
		});
	}

	public GridPane createCourse() {
		GridPane createCourse = new GridPane();
		createCourse.setAlignment(Pos.CENTER);
		createCourse.setHgap(20);
		createCourse.setVgap(20);

		// GUI
		Text courseID = new Text("Course ID: ");
		TextField tfCourseID = new TextField();

		createCourse.add(courseID, 0, 0);
		createCourse.add(tfCourseID, 1, 0);

		Text hwWeight = new Text("HW Weight: ");
		TextField tfHwWeight = new TextField();

		createCourse.add(hwWeight, 0, 1);
		createCourse.add(tfHwWeight, 1, 1);

		Text quizWeight = new Text("Quiz Weight: ");
		TextField tfQuizWeight = new TextField();

		createCourse.add(quizWeight, 0, 2);
		createCourse.add(tfQuizWeight, 1, 2);

		Text testWeight = new Text("Test Weight: ");
		TextField tfTestWeight = new TextField();

		createCourse.add(testWeight, 0, 3);
		createCourse.add(tfTestWeight, 1, 3);

		Button btCreateCourse = new Button("Create Course");

		createCourse.add(btCreateCourse, 1, 4);

		// Logic
		
		btCreateCourse.setOnAction(e -> {
			String courseName = tfCourseID.getText();
			double hw = 0;
			double quiz = 0;
			double test = 0;
			double hwPercent = 0;
			double quizPercent = 0;
			double testPercent = 0;
			
			if(courseName.length() < 3) {
				message("Course ID must be at least 3 characters", Color.RED);
				return;
			}
			
			try {
				hw = Double.parseDouble(tfHwWeight.getText());
				quiz = Double.parseDouble(tfQuizWeight.getText());
				test = Double.parseDouble(tfTestWeight.getText());
			} catch (Exception ex) {
				message("HW Weight, Quiz Weight, and Test Weight must all be numbers", Color.RED);
				return;
			}
			
			//System.out.println(courseName + " " + hw + " " + quiz + " " + test);
			
			double total = hw + quiz + test;
			hwPercent = hw / total;
			quizPercent = quiz / total;
			testPercent = test / total;
			
			Course newCourse = new Course(courseName, hwPercent, quizPercent, testPercent);
			if(courses.contains(newCourse)) {
				message("Another Course already has this Course ID", Color.RED);
				return;
			}
			
			courses.add(newCourse);
			//System.out.println(newCourse.weights());
			
			message("Course " + courseName + " added", Color.GREEN);
			
			
		});

		return createCourse;
	}

	public GridPane updateCourse() {
		GridPane updateCourse = new GridPane();
		updateCourse.setAlignment(Pos.CENTER);
		updateCourse.setHgap(20);
		updateCourse.setVgap(20);

		// GUI
		Text courseID = new Text("Course ID: ");
		ComboBox<Course> cbCourses = coursesComboBox();

		updateCourse.add(courseID, 0, 0);
		updateCourse.add(cbCourses, 1, 0);

		Text newCourseID = new Text("New Course ID: ");
		TextField tfNewCourseID = new TextField();

		updateCourse.add(newCourseID, 0, 1);
		updateCourse.add(tfNewCourseID, 1, 1);

		Text newHwWeight = new Text("New HW Weight: ");
		TextField tfNewHwWeight = new TextField();

		updateCourse.add(newHwWeight, 0, 2);
		updateCourse.add(tfNewHwWeight, 1, 2);

		Text newQuizWeight = new Text("New Quiz Weight: ");
		TextField tfNewQuizWeight = new TextField();

		updateCourse.add(newQuizWeight, 0, 3);
		updateCourse.add(tfNewQuizWeight, 1, 3);

		Text newTestWeight = new Text("New Test Weight: ");
		TextField tfNewTestWeight = new TextField();

		updateCourse.add(newTestWeight, 0, 4);
		updateCourse.add(tfNewTestWeight, 1, 4);

		Button btUpdateCourse = new Button("Update Course");

		updateCourse.add(btUpdateCourse, 1, 5);

		// Logic
		
		btUpdateCourse.setOnAction(e -> {
			Course course = cbCourses.getValue();
			if(course == null) {
				message("Error, select a course", Color.RED);
				return;
			}
			
			String courseName = tfNewCourseID.getText();
			double hw = 0;
			double quiz = 0;
			double test = 0;
			double hwPercent = 0;
			double quizPercent = 0;
			double testPercent = 0;
			
			if(courseName.length() < 3) {
				message("Course ID must be at least 3 characters", Color.RED);
				return;
			}
			
			for(Course c : courses) {
				if(c.getCourseID().equals(courseName)) {
					message("Other course already has Course ID " + courseName, Color.RED);
					return;
				}
			}
			
			try {
				hw = Double.parseDouble(tfNewHwWeight.getText());
				quiz = Double.parseDouble(tfNewQuizWeight.getText());
				test = Double.parseDouble(tfNewTestWeight.getText());
			} catch (Exception ex) {
				message("HW Weight, Quiz Weight, and Test Weight must all be numbers", Color.RED);
				return;
			}
						
			double total = hw + quiz + test;
			hwPercent = hw / total;
			quizPercent = quiz / total;
			testPercent = test / total;
			
			course.setCourseID(courseName);
			course.setHomeworkWeight(hwPercent);
			course.setQuizWeight(quizPercent);
			course.setTestWeight(testPercent);
			
			message("Successfully updated Course", Color.GREEN);
			
			
		});

		return updateCourse;
	}

	public GridPane addStudentRecord() {
		GridPane addStudent = new GridPane();
		addStudent.setAlignment(Pos.CENTER);
		addStudent.setHgap(20);
		addStudent.setVgap(20);

		// GUI
		Text courseID = new Text("Course ID: ");
		ComboBox<Course> cbCourses = coursesComboBox();

		addStudent.add(courseID, 0, 0);
		addStudent.add(cbCourses, 1, 0);

		Text studentID = new Text("Student ID: ");
		TextField tfStudentID = new TextField();

		addStudent.add(studentID, 0, 1);
		addStudent.add(tfStudentID, 1, 1);

		Text studentName = new Text("Student Name: ");
		TextField tfStudentName = new TextField();

		addStudent.add(studentName, 0, 2);
		addStudent.add(tfStudentName, 1, 2);

		Button btAddStudentRecord = new Button("Add Student Record");

		addStudent.add(btAddStudentRecord, 1, 3);

		// Logic
		btAddStudentRecord.setOnAction(e -> {
			Course course = cbCourses.getValue();
			if(course == null) {
				message("Error, select a course", Color.RED);
				return;
			}
			
			int id = -1;
			try {
				id = Integer.parseInt(tfStudentID.getText());
			} catch(Exception ex) {
				message("Enter integer for Student ID", Color.RED);
				return;
			}
			String name = tfStudentName.getText();
			
			if(name.equals("")) {
				message("Enter Student Name", Color.RED);
				return;
			}
			
			StudentRecord record = new StudentRecord(name, id);
			if(course.getRecords().contains(record)) {
				message("Course " + course + " already contains student with id " + id, Color.RED);
				return;
			}
			
			course.addRecord(record);
			
			
			
			message("Sucessfully added Student Record to Course " + course, Color.GREEN);
			
			try {
				course.assignAll();
			} catch (CloneNotSupportedException e1) {
				message("Clone Error", Color.PURPLE);
				return;
			}
		});
		
		return addStudent;
	}
	
	public GridPane addAssignment() {
		GridPane addAssignment = new GridPane();
		addAssignment.setAlignment(Pos.CENTER);
		addAssignment.setHgap(20);
		addAssignment.setVgap(20);
		
		// GUI
		
		Text courseID = new Text("Course ID: ");
		ComboBox<Course> cbCourses = coursesComboBox();
		
		addAssignment.add(courseID, 0, 0);
		addAssignment.add(cbCourses, 1, 0);
		
		Text assignmentTypeText = new Text("Assignment Type: ");
		ComboBox<assignmentType> cbAssignmentType = new ComboBox<>();
		
		cbAssignmentType.setMinWidth(170);
		cbAssignmentType.getItems().addAll(assignmentType.HOMEWORK, assignmentType.QUIZ, assignmentType.TEST);
		cbAssignmentType.setValue(assignmentType.HOMEWORK);
		
		addAssignment.add(assignmentTypeText, 0, 1);
		addAssignment.add(cbAssignmentType, 1, 1);
		
		Text assignmentID = new Text("Assignment ID: ");
		TextField tfAssignmentID = new TextField();
		
		addAssignment.add(assignmentID, 0, 2);
		addAssignment.add(tfAssignmentID, 1, 2);
		
		Text assignmentName = new Text("Assignment Name: ");
		TextField tfAssignmentName = new TextField();
		
		addAssignment.add(assignmentName, 0, 3);
		addAssignment.add(tfAssignmentName, 1, 3);
		
		Text assignmentPoints = new Text("Assignment Points: ");
		TextField tfAssignmentPoints = new TextField();
		
		addAssignment.add(assignmentPoints, 0, 4);
		addAssignment.add(tfAssignmentPoints, 1, 4);
		
		Text dueDate = new Text("Due Date: (MM/dd/yyyy) ");
		TextField tfDueDate = new TextField();
		
		addAssignment.add(dueDate, 0, 5);
		addAssignment.add(tfDueDate, 1, 5);
		
		Button btAddAssignment = new Button("Add Assignment");
		
		addAssignment.add(btAddAssignment, 1, 6);
		
		// Logic
		
		btAddAssignment.setOnAction(e -> {
			Course course = cbCourses.getValue();
			if(course == null) {
				message("Error, select a course", Color.RED);
				return;
			}
			
			assignmentType type = cbAssignmentType.getValue();
			
			int id = -1;
			try {
				id = Integer.parseInt(tfAssignmentID.getText());
			} catch(Exception ex) {
				message("Enter integer for Assignment ID", Color.RED);
				return;
			}
			
			String name = tfAssignmentName.getText();
			
			if(name.equals("")) {
				message("Enter Assignment Name", Color.RED);
				return;
			}
			
			int points = -1;
			try {
				points = Integer.parseInt(tfAssignmentPoints.getText());
			} catch (Exception ex) {
				message("Enter positive integer for Assignment Points", Color.RED);
				return;
			}
			
			if(points < 0) {
				message("Enter positive integer for Assignment Points", Color.RED);
				return;
			}
			
			String date = tfDueDate.getText();
			Date due;
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			
			try {
				due = df.parse(date);
			} catch (Exception ex) {
				message("Incorrect Date Format", Color.RED);
				return;
			}
			
			Assignment assignment = new Assignment(id, due, points, name, type);
			if(course.getAssignments().contains(assignment)) {
				message("Course " + course + " already contains an Assignment with ID " + id, Color.RED);
				return;
			}
			
			course.addAssignment(assignment);
			
			
			message("Successfully added Assignment " + name + " to Course " + course, Color.GREEN);
			
			try {
				course.assignAll();
			} catch (CloneNotSupportedException e1) {
				message("Clone Error", Color.PURPLE);
				return;
			}
			
		});
		
		
		return addAssignment;
	}
	
	public GridPane gradeAssignment() {
		GridPane gradeAssignment = new GridPane();
		gradeAssignment.setAlignment(Pos.CENTER);
		gradeAssignment.setHgap(20);
		gradeAssignment.setVgap(20);
		
		// GUI
		Text courseID = new Text("Course ID: ");
		ComboBox<Course> cbCourses = coursesComboBox();
		cbCourses.setPrefWidth(170);
		
		gradeAssignment.add(courseID, 0, 0);
		gradeAssignment.add(cbCourses, 1, 0);
		
		Text assignmentID = new Text("Assignment ID: ");
		ComboBox<Assignment> cbAssignments = new ComboBox<>();
		cbAssignments.setPrefWidth(170);
		
		gradeAssignment.add(assignmentID, 0, 2);
		gradeAssignment.add(cbAssignments, 1, 2);
		
		Text studentRecord = new Text("Student Record: ");
		ComboBox<StudentRecord> cbStudentRecords = new ComboBox<>();
		cbStudentRecords.setPrefWidth(170);
		
		gradeAssignment.add(studentRecord, 0, 1);
		gradeAssignment.add(cbStudentRecords, 1, 1);
		
		Text grade = new Text("Grade: ");
		TextField tfGrade = new TextField();
		
		gradeAssignment.add(grade, 0, 3);
		gradeAssignment.add(tfGrade, 1, 3);
		
		RadioButton rbRaw = new RadioButton("Raw Grade");
		RadioButton rbPercentage = new RadioButton("Percentange");
		
		ToggleGroup tgGrade = new ToggleGroup();
		rbRaw.setToggleGroup(tgGrade);
		rbPercentage.setToggleGroup(tgGrade);
		rbRaw.setSelected(true);
		
		gradeAssignment.add(rbRaw, 0, 4);
		gradeAssignment.add(rbPercentage, 1, 4);
		
		Button btGradeAssignment = new Button("Grade Assignment");
		
		gradeAssignment.add(btGradeAssignment, 1, 5);
		
		// Logic
		
		cbCourses.setOnAction(e -> {
			Course course = cbCourses.getValue();
			if(course == null) {
				message("Error, select a course", Color.RED);
				return;
			}
			
			ArrayList<StudentRecord> records = course.getRecords();
			if(records.size() == 0) {
				message("Error, this course has no Student Records", Color.RED);
				return;
			}
			
			cbStudentRecords.getItems().clear();
			cbStudentRecords.getItems().addAll(records);
			
			message("Course succcessfully chosen, now choose a Student Record", Color.GREEN);
			
		});
		
		cbStudentRecords.setOnAction(e -> {
			StudentRecord record = cbStudentRecords.getValue();
			if(record == null) {
				message("Error, select a Student Record", Color.RED);
				return;
			}
			
			ArrayList<Assignment> assignments = record.getAssignments();
			
			if(assignments.size() == 0) {
				message("Error, this Course has no assignments", Color.RED);
				return;
			}
			
			cbAssignments.getItems().clear();
			cbAssignments.getItems().addAll(assignments);
			
			message("Student Record successfully chosen, now choose an assignment to grade", Color.GREEN);
			
		});
		
		btGradeAssignment.setOnAction(e -> {
			Course course = cbCourses.getValue();
			if(course == null) {
				message("Error, select a course", Color.RED);
				return;
			}
			
			
		});
		
		return gradeAssignment;
	}
	
	public ComboBox<Course> coursesComboBox() {
		ComboBox<Course> cbCourses = new ComboBox<>();
		cbCourses.setPrefWidth(170);
		cbCourses.getItems().addAll(courses);
		
		return cbCourses;
	}
	
	public void message(String message, Color color) {
		this.message.setText(message);
		this.message.setTextFill(color);
	}
	
	public void clear() {
		this.message.setText("");
	}

	public static void main(String[] args) {
		courses.add(new Course("Test", .2, .3, .5));
		Application.launch(args);
	}

}

