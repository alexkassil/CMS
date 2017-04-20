package project;

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
	ArrayList<Course> courses = new ArrayList<Course>();
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

		// message formatting
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
		TextField tfCourseID = new TextField();
		
		addAssignment.add(courseID, 0, 0);
		addAssignment.add(tfCourseID, 1, 0);
		
		Text assignmentType = new Text("Assignment Type: ");
		ComboBox cbAssignmentType = new ComboBox();
		
		cbAssignmentType.setMinWidth(170);
		cbAssignmentType.getItems().addAll("Homework", "Quiz", "Test");
		cbAssignmentType.setValue("Homework");
		
		addAssignment.add(assignmentType, 0, 1);
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
		
		Text dueDate = new Text("Due Date: ");
		TextField tfDueDate = new TextField();
		
		addAssignment.add(dueDate, 0, 5);
		addAssignment.add(tfDueDate, 1, 5);
		
		Button btAddAssignment = new Button("Add Assignment");
		
		addAssignment.add(btAddAssignment, 1, 6);
		
		return addAssignment;
	}
	
	public GridPane gradeAssignment() {
		GridPane gradeAssignment = new GridPane();
		gradeAssignment.setAlignment(Pos.CENTER);
		gradeAssignment.setHgap(20);
		gradeAssignment.setVgap(20);
		
		// GUI
		Text courseID = new Text("Course ID: ");
		TextField tfCourseID = new TextField();
		
		gradeAssignment.add(courseID, 0, 0);
		gradeAssignment.add(tfCourseID, 1, 0);
		
		Text assignmentID = new Text("Assignment ID: ");
		TextField tfAssignmentID = new TextField();
		
		gradeAssignment.add(assignmentID, 0, 1);
		gradeAssignment.add(tfAssignmentID, 1, 1);
		
		Text grade = new Text("Grade: ");
		TextField tfGrade = new TextField();
		
		gradeAssignment.add(grade, 0, 2);
		gradeAssignment.add(tfGrade, 1, 2);
		
		RadioButton rbRaw = new RadioButton("Raw Grade");
		RadioButton rbPercentage = new RadioButton("Percentange");
		
		ToggleGroup tgGrade = new ToggleGroup();
		rbRaw.setToggleGroup(tgGrade);
		rbPercentage.setToggleGroup(tgGrade);
		rbRaw.setSelected(true);
		
		gradeAssignment.add(rbRaw, 0, 3);
		gradeAssignment.add(rbPercentage, 1, 3);
		
		Button btGradeAssignment = new Button("Grade Assignment");
		
		gradeAssignment.add(btGradeAssignment, 1, 4);
		
		// Logic
		
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
		Application.launch(args);
	}

}

class CoursePane extends BorderPane {
	String title;
	ArrayList<Course> courses;
	HBox courseButtons;
	FlowPane courseLinks;
	TextField addCourseTF;
	TextField removeCourseTF;

	CoursePane() {
		title = "Courses";
		courses = new ArrayList<Course>();

		courseButtons = new HBox(10);
		courseButtons.setAlignment(Pos.CENTER);

		Text addCourseText = new Text("Add Course: ");
		addCourseTF = new TextField("Course ID");
		addCourseTF.setOnAction(e -> addCourse());

		Text removeCourseText = new Text("Remove Course: ");
		removeCourseTF = new TextField("Course ID");
		removeCourseTF.setOnAction(e -> removeCourse());

		courseButtons.getChildren().addAll(addCourseText, addCourseTF, removeCourseText, removeCourseTF);

		courseLinks = new FlowPane();
		courseLinks.setPadding(new Insets(10, 10, 10, 10));
		courseLinks.setHgap(50);

		courseLinks.getChildren().add(new Text("No Courses"));

		setCenter(courseLinks);
		setBottom(courseButtons);
	}

	void addCourse() {
		String courseID = addCourseTF.getText();
		addCourseTF.setText("");

		if (courseID.length() < 3) {
			error("Course ID must be at least 3 characters");
			return;
		}

		System.out.println(courses.size());
		if (courses.size() == 0) {
			courseLinks.getChildren().clear();
		}
		Course newCourse = new Course(courseID);
		Button newCourseBT = new Button(courseID);

		if (courses.contains(newCourse)) {
			error(courseID + " is already a course");
			return;
		}

		courses.add(newCourse);
		courseLinks.getChildren().add(newCourseBT);

	}

	void removeCourse() {
		String courseID = removeCourseTF.getText();
		removeCourseTF.setText("");

		Course newCourse = new Course(courseID);

		if (!courses.contains(newCourse)) {
			error("Course ID does not match");
			return;
		}

		for (int i = 0; i < courses.size(); i++) {
			Button currentButton = (Button) courseLinks.getChildren().get(i);
			if (currentButton.getText().equals(courseID)) {
				courseLinks.getChildren().remove(i);
				courses.remove(newCourse);
			}
		}

		if (courses.size() == 0) {
			courseLinks.getChildren().add(new Text("No Courses"));
		}

	}

	void error(String error) {
		System.out.println(error);
	}
}
