package project;

import java.util.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;

public class FinalProject extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		CoursePane pane = new CoursePane();
		
		Scene scene = new Scene(pane, 600, 600);
		primaryStage.setTitle("Courses");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}

class CoursePane extends BorderPane {
	String title;
	ArrayList<Course> courses;
	HBox courseButtons;
	FlowPane courseLinks;
	TextField addCourseTF;
	
	CoursePane() {
		title = "Courses";
		courses = new ArrayList<Course>();
		
		courseButtons = new HBox(10);
		courseButtons.setAlignment(Pos.CENTER);
		
		Text addCourseText = new Text("Add Course: ");
		addCourseTF = new TextField("Course ID");
		addCourseTF.setOnAction(e -> addCourse());
		
		courseButtons.getChildren().addAll(addCourseText, addCourseTF);
		
		courseLinks = new FlowPane();
		courseLinks.setPadding(new Insets(10, 10, 10, 10));
		courseLinks.setHgap(50);
		
		courseLinks.getChildren().add(new Text("No Courses"));
		
		setCenter(courseLinks);
		setBottom(courseButtons);
	}
	
	void addCourse() {
		String courseID = addCourseTF.getText();
		
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
	
	void error(String error) {
		System.out.println(error);
	}
}
