package project;

import java.util.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;

public class FinalProject extends Application {
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
		cb.getItems().addAll("Create Course", "Update Course", "Add Student", "Add Assignment", "Grade Assignment");
		
		// Selecting options
		cb.setOnAction(e -> {
			if(cb.getValue().equals("Create Course")) {
				System.out.println("1");
			} else if (cb.getValue().equals("Update Course")) {
				System.out.println("2");
			} else if (cb.getValue().equals("Add Student")) {
				System.out.println("3");
			} else if (cb.getValue().equals("Add Assignment")) {
				System.out.println("4");
			} else if (cb.getValue().equals("Grade Assignment")) {
				System.out.println("5");
			}
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
		
		courseButtons.getChildren().addAll(addCourseText, addCourseTF, 
				removeCourseText, removeCourseTF);
		
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
		
		if(courseID.length() < 3) {
			error("Course ID must be at least 3 characters");
			return;
		}
		
		System.out.println(courses.size());
		if(courses.size() == 0) {
			courseLinks.getChildren().clear();
		}
		Course newCourse = new Course(courseID);
		Button newCourseBT = new Button(courseID);
		
		if(courses.contains(newCourse)) {
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
		
		if(!courses.contains(newCourse)) {
			error("Course ID does not match");
			return;
		}
		
		
		
		for(int i = 0; i < courses.size(); i++) {
			Button currentButton = (Button)courseLinks.getChildren().get(i);
			if(currentButton.getText().equals(courseID)) {
				courseLinks.getChildren().remove(i);
				courses.remove(newCourse);
			}
		}
		
		if(courses.size() == 0) {
			courseLinks.getChildren().add(new Text("No Courses"));
		}
		
		
	}
	
	void error(String error) {
		System.out.println(error);
	}
}


