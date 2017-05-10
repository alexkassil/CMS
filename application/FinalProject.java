package project;

import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
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
	Label message = new Label("");
	private Statement stmt;

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
		cb.getItems().addAll("Create Course", "Update Course", "Add Student Record", "Add Assignment", 
				"Grade Assignment", "View Grades");

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
			} else if (cb.getValue().equals("View Grades")) {
				pane.setCenter(viewGrades());
			}
			clear();
		});

		pickPane.getChildren().addAll(select, cb);

		// Message formatting
		HBox hbMessage = new HBox(10);
		hbMessage.setAlignment(Pos.CENTER);
		hbMessage.getChildren().add(message);
		message.setPadding(new Insets(5, 10, 10, 5));
		message.setId("the-label");
		message.setWrapText(true);


		pane.setTop(pickPane);
		pane.setBottom(hbMessage);
		pane.setId("text");
		
		// SQL Connection
		initializeDB();
		
		
		// Setting up GUI
		Scene scene = new Scene(pane, 750, 750);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(FinalProject.class.getResource("FinalProject.css").toExternalForm());
		primaryStage.setTitle("Kassil's Course Management Service");
		primaryStage.show();
		message("Welcome to the Course Management Serivice!", Color.BLUE);
		primaryStage.setOnCloseRequest(e -> {
			primaryStage.close();
			for(Course c : courses) {
				System.out.println(c.courseInfo());
				System.out.println(c.weights());
				System.out.println("---------------");
				for(StudentRecord r : c.getRecords()) {
					System.out.println(r.studentRecordInfo());
					r.printAssignments();
					System.out.println(c.getGrade(r) * 100);
					System.out.println("----------");
				}
			}
		});
	}
	
	public void initializeDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
			
			Connection connection = DriverManager.getConnection
					("jdbc:mysql://localhost/finalproject", "root", "qwerty");
			System.out.println("Database connected");
			
			stmt = connection.createStatement();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		courses.clear();
		getCoursesDB();
		getStudentRecordsDB();
		getAssignmentsDB();
		getGradesDB();
	}
	
	public void getCoursesDB() {
		try {
			String getCourses = "SELECT * FROM Courses";
			ResultSet rset = stmt.executeQuery(getCourses);
			
			while(rset.next()) {
				String courseID = rset.getString(1);
				double hwWeight = rset.getDouble(2);
				double quizWeight = rset.getDouble(3);
				double testWeight = rset.getDouble(4);
				
				System.out.println(courseID + " " + hwWeight + " " + quizWeight + " " + testWeight);
				Course course = new Course(courseID, hwWeight, quizWeight, testWeight);
				courses.add(course);
			}
			
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void getStudentRecordsDB() {
		try {
			String getStudentRecords = "SELECT * FROM StudentRecords";
			ResultSet rset = stmt.executeQuery(getStudentRecords);
			
			while(rset.next()) {
				int studentRecordID = rset.getInt(1);
				String courseID = rset.getString(2);
				String studentName = rset.getString(3);
				
				//System.out.println(studentRecordID + " " + courseID + " " + studentName);
				Course currentCourse = getCourse(courseID);

				if(currentCourse == null)
					continue;
				
				StudentRecord currentRecord = new StudentRecord(studentName, studentRecordID);
				
				if(currentCourse.getRecords().contains(currentRecord))
					continue;
				
				currentCourse.addRecord(currentRecord);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void getAssignmentsDB() {
		try {
			String getAssignments = "SELECT * FROM Assignments";
			ResultSet rset = stmt.executeQuery(getAssignments);
			
			while(rset.next()) {
				int id = rset.getInt(1);
				String courseID = rset.getString(2);
				String dueDate = rset.getString(3);
				int points = rset.getInt(4);
				String name = rset.getString(5);
				String assignmentTypeString = rset.getString(6);
				
				//System.out.printf("%d %s %s %d %s %s\n", id, courseID, dueDate, points,
				//		name, assignmentTypeString);
				
				Course currentCourse = getCourse(courseID);
				
				if(currentCourse == null)
					continue;
				
				
				assignmentType type;
				
				if(assignmentTypeString.equals("HWRK"))
					type = assignmentType.HOMEWORK;
				else if(assignmentTypeString.equals("QUIZ"))
					type = assignmentType.QUIZ;
				else
					type = assignmentType.TEST;
				
				
				Date due;
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				
				
				due = df.parse(dueDate);
				

				Assignment currentAssignment = new Assignment(id, due, points, name, type);
				
				if(currentCourse.getAssignments().contains(currentAssignment))
					continue;
				
				currentCourse.addAssignment(currentAssignment);
				
				currentCourse.assignAll();
			}
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getGradesDB() {
		try {
		String getGrades = "SELECT * FROM Grades";
		ResultSet rset = stmt.executeQuery(getGrades);
		
		while(rset.next()) {
			String courseID = rset.getString(1);
			int studentRecordID = rset.getInt(2);
			int id = rset.getInt(3);
			double grade = rset.getDouble(4);
			
			//System.out.printf("%s %d %d %.4f\n", courseID, studentRecordID, id, grade);
			
			Course course = getCourse(courseID);
			
			if(course == null)
				continue;
			
			StudentRecord record = getRecord(course, studentRecordID);
			
			if(record == null)
				continue;
			
			course.gradeAssignmentPercent(record, id, grade);
		}
		
		
		
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
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
			
			String addCourse = "INSERT INTO Courses VALUES('" + courseName +
					"', " + hwPercent + ", " + quizPercent + ", " + testPercent + ")";
						
			try {
				stmt.executeUpdate(addCourse);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
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
			
			
			String name = tfStudentName.getText();
			
			if(name.equals("")) {
				message("Enter Student Name", Color.RED);
				return;
			}
			
			
			
			
			String addRecord = "INSERT INTO StudentRecords VALUES(null, '" +
					course.getCourseID() + "', '" + name + "')";
			
			System.out.println(addRecord);
			
			try {
				int id = stmt.executeUpdate(addRecord, Statement.RETURN_GENERATED_KEYS); 
				
				StudentRecord record = new StudentRecord(name, id);
				course.addRecord(record);
				
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
				
			

			
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
		
		Text assignmentName = new Text("Assignment Name: ");
		TextField tfAssignmentName = new TextField();
		
		addAssignment.add(assignmentName, 0, 3);
		addAssignment.add(tfAssignmentName, 1, 3);
		
		Text assignmentPoints = new Text("Assignment Points: ");
		TextField tfAssignmentPoints = new TextField();
		
		addAssignment.add(assignmentPoints, 0, 4);
		addAssignment.add(tfAssignmentPoints, 1, 4);
		
		Text dueDate = new Text("Due Date: (MM-dd-yyyy) ");
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
			
			String typeString;
			assignmentType type = cbAssignmentType.getValue();
			if(type.name().equals("HOMEWORK"))
				typeString = "HWRK";
			else
				typeString = type.name();
			
			
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
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			
			try {
				due = df.parse(date);
			} catch (Exception ex) {
				message("Incorrect Date Format", Color.RED);
				return;
			}
			
			java.sql.Date sqlDate = new java.sql.Date(due.getTime());
			
			System.out.println(date + "\n" + due);
			
			
			try {
				String addAssignmentSQL = "INSERT INTO Assignments VALUES(null, '" +
						course.getCourseID() + "', '" + sqlDate + "', " + points + ", '" +
						name + "', '" + typeString + "')";
				
				System.out.println(addAssignmentSQL);
				
				int id = stmt.executeUpdate(addAssignmentSQL, Statement.RETURN_GENERATED_KEYS);
				
				Assignment assignment = new Assignment(id, due, points, name, type);
				course.addAssignment(assignment);
				message("Successfully added Assignment " + name + " to Course " + course, Color.GREEN);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			
			
			
			
			
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
			
			StudentRecord record = cbStudentRecords.getValue();
			if(record == null) {
				message("Error, select a Student Record", Color.RED);
				return;
			}
			
			Assignment assignment = cbAssignments.getValue();
			if(assignment == null) {
				message("Error, select an Assignment", Color.RED);
				return;
			}
			
			double givenGrade = 0;
			
			try {
				givenGrade = Double.parseDouble(tfGrade.getText());
			} catch (Exception ex) {
				message("Error, enter correct a double for grade", Color.RED);
				return;
			}
			
			if(rbRaw.isSelected()) {
				course.gradeAssignment(record, assignment.getID(), givenGrade);
			} else if (rbPercentage.isSelected()) {
				course.gradeAssignment(record, assignment.getID(), (givenGrade/100.0) * assignment.getPoints());
			}
			
			message("Assignment " + assignment.getName() + " Successfully graded!", Color.GREEN);
			
			
			
			
		});
		
		return gradeAssignment;
	}
	
	public GridPane viewGrades() {
		GridPane viewGrade = new GridPane();
		viewGrade.setAlignment(Pos.CENTER);
		viewGrade.setHgap(20);
		viewGrade.setVgap(20);
		
		// GUI
		Text courseID = new Text("Course ID: ");
		ComboBox<Course> cbCourses = coursesComboBox();
		cbCourses.setPrefWidth(170);
		
		viewGrade.add(courseID, 0, 0);
		viewGrade.add(cbCourses, 1, 0);
		
		Text studentRecord = new Text("Student Record: ");
		ComboBox<StudentRecord> cbStudentRecords = new ComboBox<>();
		cbStudentRecords.setPrefWidth(170);
		
		viewGrade.add(studentRecord, 0, 1);
		viewGrade.add(cbStudentRecords, 1, 1);
		
		Text gradeText = new Text("Grade (% Percent): ");
		Text grade = new Text();
		
		viewGrade.add(gradeText, 0, 2);
		viewGrade.add(grade, 1, 2);
		
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
			
			Course course = cbCourses.getValue();
			if(course == null) {
				message("Error, select a course", Color.RED);
				return;
			}
			
			double rawGrade = course.getGrade(record);
			
			String formattedGrade = String.format("%.2f", rawGrade * 100);
			
			grade.setText(formattedGrade);
			
			
			message("This is " + record.getName() + "'s grade in the class " + course.getCourseID(), Color.GREEN);
			
		});
		
		return viewGrade;
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
	
	public Course getCourse(String courseID) {
		for(Course c: courses) {
			if(c.getCourseID().equals(courseID)) 
				return c;
		}
		return null;
	}
	
	public StudentRecord getRecord(Course course, int id) {
		for(StudentRecord r : course.getRecords()) {
			if(r.getID() == id) {
				return r;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}

