import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizManagementSystem extends Application {

    private Map<String, Teacher> teachers;
    private Map<String, Integer> studentGrades;
    private Teacher selectedTeacher;
    private String selectedQuiz;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        teachers = new HashMap<>();
        studentGrades = new HashMap<>();

        primaryStage.setTitle("Quiz Management System");
        showRoleSelection(primaryStage);
        primaryStage.show();
    }

    private void showRoleSelection(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Label titleLabel = new Label("Role Selection");
        titleLabel.setStyle("-fx-font-size: 20px;");

        Button adminButton = new Button("Admin");
        adminButton.setOnAction(e -> {
            showAdminDashboard(primaryStage);
        });

        Button teacherButton = new Button("Teacher");
        teacherButton.setOnAction(e -> {
            showTeacherDashboard(primaryStage);
        });

        Button studentButton = new Button("Student");
        studentButton.setOnAction(e -> {
            showStudentDashboard(primaryStage);
        });

        vbox.getChildren().addAll(titleLabel, adminButton, teacherButton, studentButton);

        primaryStage.setScene(new Scene(vbox, 400, 300));
    }

    private void showAdminDashboard(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setStyle("-fx-font-size: 20px;");
		TextField teacherNameField = new TextField();
		TextField teacherPasswordField = new TextField();
        Button addTeacherButton = new Button("Add Teacher");
        HashMap<String, String> teacherData = new HashMap<>();

		Button addTeacherButton = new Button("Add Teacher");
		addTeacherButton.setOnAction(e -> {
			String teacherName = teacherNameField.getText();
			String teacherPassword = teacherPasswordField.getText();

		if (teacherName.isEmpty()) {
			System.out.println("Please enter a teacher name");
			return;
		}

		if (teacherPassword.isEmpty()) {
			System.out.println("Please enter a teacher password");
			return;
		}

		if (teacherData.containsKey(teacherName)) {
			System.out.println("Teacher already exists");
			return;
		}

		teacherData.put(teacherName, teacherPassword);

		teacherNameField.clear();
		teacherPasswordField.clear();

		System.out.println("Teacher added successfully");
	});


        Button viewTeachersButton = new Button("View Teachers");
	    viewTeachersButton.setOnAction(e -> {
		// Create a new stage
		Stage stage = new Stage();
		stage.setTitle("Teachers");

		TableView<Teacher> tableView = new TableView<>();

		TableColumn<Teacher, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Teacher, String> passwordColumn = new TableColumn<>("Password");
		passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

		ObservableList<Teacher> teacherList = FXCollections.observableArrayList();

		for (Map.Entry<String, String> entry : teacherData.entrySet()) {
			String teacherName = entry.getKey();
			String teacherPassword = entry.getValue();

			Teacher teacher = new Teacher(teacherName, teacherPassword);
			teacherList.add(teacher);
		}

		tableView.setItems(teacherList);
		tableView.getColumns().addAll(nameColumn, passwordColumn);

		VBox vbox = new VBox(tableView);

		Scene scene = new Scene(vbox);

		stage.setScene(scene);
		stage.show();
	});


        Button deleteTeacherButton = new Button("Delete Teacher");
	    deleteTeacherButton.setOnAction(e -> {
		String teacherName = teacherNameField.getText();

		if (teacherData.containsKey(teacherName)) {
			teacherData.remove(teacherName);
			System.out.println("Teacher deleted successfully");
		} else {
			System.out.println("Teacher not found");
		}

		teacherNameField.clear();
		});


        Button viewStudentsButton = new Button("View Students");
		viewStudentsButton.setOnAction(e -> {
		System.out.println("List of Students:");
		for (String studentName : studentData.keySet()) {
			System.out.println(studentName);
		}
		});


        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            showRoleSelection(primaryStage);
        });

        vbox.getChildren().addAll(titleLabel, addTeacherButton, viewTeachersButton, deleteTeacherButton, viewStudentsButton, backButton);

        primaryStage.setScene(new Scene(vbox, 400, 300));
    }

    private void showTeacherDashboard(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Label titleLabel = new Label("Teacher Dashboard");
        titleLabel.setStyle("-fx-font-size: 20px;");

        HashMap<String, String> studentData = new HashMap<>();

		Button addStudentButton = new Button("Add Student");
		addStudentButton.setOnAction(e -> {
		String studentName = studentNameField.getText();
		String studentId = studentIdField.getText();

		if (studentName.isEmpty()) {
			System.out.println("Please enter a student name");
			return;
		}

		if (studentId.isEmpty()) {
			System.out.println("Please enter a student ID");
			return;
		}

		// Store the student data in the HashMap
		studentData.put(studentName, studentId);

		// Clear the input fields
		studentNameField.clear();
		studentIdField.clear();

		System.out.println("Student added successfully");
	    });

		Button deleteStudentButton = new Button("Delete Student");
		deleteStudentButton.setOnAction(e -> {
			String studentName = studentNameField.getText();

			if (studentName.isEmpty()) {
				System.out.println("Please enter a student name");
				return;
			}

			if (studentData.containsKey(studentName)) {
				// Remove the student entry from the HashMap
				studentData.remove(studentName);
				System.out.println("Student deleted successfully");
			} else {
				System.out.println("Student not found");
			}

			// Clear the input field
			studentNameField.clear();
		});


        Button createQuizButton = new Button("Create Quiz");
		createQuizButton.setOnAction(e -> {
			// Prompt the teacher to enter the quiz details
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Create Quiz");
			dialog.setHeaderText("Enter the quiz details");
			dialog.setContentText("Number of questions:");

			Optional<String> result = dialog.showAndWait();

			// Process the entered quiz details
			result.ifPresent(numQuestions -> {
				int num = Integer.parseInt(numQuestions);

				// Create separate lists to store questions and options
				List<String> questions = new ArrayList<>();
				List<List<String>> options = new ArrayList<>();

				for (int i = 0; i < num; i++) {
					// Prompt the teacher to enter each question
					TextInputDialog questionDialog = new TextInputDialog();
					questionDialog.setTitle("Create Quiz");
					questionDialog.setHeaderText("Enter Question " + (i + 1));
					questionDialog.setContentText("Question:");

					Optional<String> questionResult = questionDialog.showAndWait();

					// Process the entered question
					questionResult.ifPresent(question -> {
						List<String> questionOptions = new ArrayList<>();

						for (int j = 0; j < 4; j++) {
							// Prompt the teacher to enter each option for the question
							TextInputDialog optionDialog = new TextInputDialog();
							optionDialog.setTitle("Create Quiz");
							optionDialog.setHeaderText("Enter Option " + (j + 1) + " for Question " + (i + 1));
							optionDialog.setContentText("Option:");

							Optional<String> optionResult = optionDialog.showAndWait();

							// Process the entered option
							optionResult.ifPresent(option -> {
								questionOptions.add(option);
							});
						}

						// Add the question and its options to the respective lists
						questions.add(question);
						options.add(questionOptions);
					});
				}

				// Print the questions and options (for testing purposes)
				System.out.println("Quiz Questions:");
				for (int i = 0; i < questions.size(); i++) {
					System.out.println("Question " + (i + 1) + ": " + questions.get(i));
					System.out.println("Options: " + options.get(i));
				}
			});
		});


        Button viewAnswersButton = new Button("View Answers");
		viewAnswersButton.setOnAction(e -> {
			// Check if a quiz has been created
			if (questions.isEmpty()) {
				System.out.println("No quiz has been created");
				return;
			}

			// Check if answers have been submitted
			if (studentAnswers.isEmpty()) {
				System.out.println("No answers have been submitted yet");
				return;
			}

			// Display the answers for each question
			for (int i = 0; i < questions.size(); i++) {
				String question = questions.get(i);
				List<String> options = options.get(i);
				String correctAnswer = correctAnswers.get(i);
				String studentAnswer = studentAnswers.get(i);

				System.out.println("Question " + (i + 1) + ": " + question);
				System.out.println("Options: " + options);
				System.out.println("Correct Answer: " + correctAnswer);
				System.out.println("Student Answer: " + studentAnswer);
				System.out.println();
			}
		});

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            showRoleSelection(primaryStage);
        });

        vbox.getChildren().addAll(titleLabel, addStudentButton, deleteStudentButton, createQuizButton, viewAnswersButton, backButton);

        primaryStage.setScene(new Scene(vbox, 400, 300));
    }

    private void showStudentDashboard(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Label titleLabel = new Label("Student Dashboard");
        titleLabel.setStyle("-fx-font-size: 20px;");

        Button takeQuizButton = new Button("Take Quiz");
		takeQuizButton.setOnAction(e -> {
			// Implement logic for taking a quiz

			// Get the quiz questions and options from your data source
			List<String> questions = getQuizQuestions(); // Replace with your logic to retrieve quiz questions
			Map<String, List<String>> options = getQuizOptions(); // Replace with your logic to retrieve quiz options

			// Create a VBox to hold the quiz questions and options
			VBox quizContainer = new VBox();
			quizContainer.setSpacing(10);

			// Create and display the quiz questions and options
			for (int i = 0; i < questions.size(); i++) {
				String question = questions.get(i);
				List<String> questionOptions = options.get(question);

				// Create a Label for the question
				Label questionLabel = new Label("Question " + (i + 1) + ": " + question);

				// Create a ToggleGroup for the question options
				ToggleGroup optionGroup = new ToggleGroup();

				// Create a VBox to hold the question options
				VBox optionsContainer = new VBox();
				optionsContainer.setSpacing(5);

				// Create a RadioButton for each question option
				for (String option : questionOptions) {
					RadioButton radioButton = new RadioButton(option);
					radioButton.setToggleGroup(optionGroup);
					optionsContainer.getChildren().add(radioButton);
				}

				// Add the question label and options container to the quiz container
				quizContainer.getChildren().addAll(questionLabel, optionsContainer);
			}

			// Create a Submit button for submitting the quiz
			Button submitButton = new Button("Submit");
			submitButton.setOnAction(event -> {
				// Process the submitted quiz answers

				// Retrieve the selected options for each question
				List<String> selectedOptions = new ArrayList<>();
				for (Node node : quizContainer.getChildren()) {
					if (node instanceof VBox) {
						VBox optionsContainer = (VBox) node;
						RadioButton selectedOption = (RadioButton) optionGroup.getSelectedToggle();
						if (selectedOption != null) {
							String selectedOptionText = selectedOption.getText();
							selectedOptions.add(selectedOptionText);
						}
					}
				}

				// TODO: Implement your logic to process the quiz answers
				// You can compare the selectedOptions with the correct answers, calculate the score, etc.

				// Display the quiz results or perform any other actions
				System.out.println("Quiz submitted");
			});

			// Create a VBox to hold the quiz container and submit button
			VBox quizLayout = new VBox();
			quizLayout.setSpacing(10);
			quizLayout.getChildren().addAll(quizContainer, submitButton);

			// TODO: Add quizLayout to your UI or display it in a separate window/dialog

			// You can customize this code to fit your specific requirements and integrate it with the rest of your application logic
		});

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            showRoleSelection(primaryStage);
        });

        vbox.getChildren().addAll(titleLabel, takeQuizButton, viewGradeButton, backButton);

        primaryStage.setScene(new Scene(vbox, 400, 300));
    }

    private void showAddTeacherForm(Stage primaryStage) {
    // Create UI components for adding a teacher
    Label nameLabel = new Label("Teacher Name:");
    TextField nameField = new TextField();
    Label passwordLabel = new Label("Password:");
    PasswordField passwordField = new PasswordField();
    Button addButton = new Button("Add");

    // Define the layout for the form
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(10));
    layout.getChildren().addAll(nameLabel, nameField, passwordLabel, passwordField, addButton);

    // Set up the action for the add button
    addButton.setOnAction(e -> {
        String teacherName = nameField.getText();
        String teacherPassword = passwordField.getText();

        // Add teacher to the teacherData map
        teacherData.put(teacherName, teacherPassword);

        // Clear the input fields
        nameField.clear();
        passwordField.clear();

        System.out.println("Teacher added successfully");
    });

    // Show the form
    Scene scene = new Scene(layout, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Add Teacher");
    primaryStage.show();
}

private void showViewTeachers(Stage primaryStage) {
    // Create UI components for displaying the list of teachers
    ListView<String> teacherListView = new ListView<>();
    teacherListView.getItems().addAll(teacherData.keySet());

    // Show the list of teachers
    Scene scene = new Scene(teacherListView, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.setTitle("View Teachers");
    primaryStage.show();
}

private void showDeleteTeacherForm(Stage primaryStage) {
    // Create UI components for deleting a teacher
    Label nameLabel = new Label("Teacher Name:");
    TextField nameField = new TextField();
    Button deleteButton = new Button("Delete");

    // Define the layout for the form
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(10));
    layout.getChildren().addAll(nameLabel, nameField, deleteButton);

    // Set up the action for the delete button
    deleteButton.setOnAction(e -> {
        String teacherName = nameField.getText();

        // Remove teacher from the teacherData map
        teacherData.remove(teacherName);

        // Clear the input field
        nameField.clear();

        System.out.println("Teacher deleted successfully");
    });

    // Show the form
    Scene scene = new Scene(layout, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Delete Teacher");
    primaryStage.show();
}

// Implement the other methods similarly for other functionalities

// Example usage:
public void start(Stage primaryStage) {
    Button addTeacherButton = new Button("Add Teacher");
    addTeacherButton.setOnAction(e -> showAddTeacherForm(primaryStage));

    Button viewTeachersButton = new Button("View Teachers");
    viewTeachersButton.setOnAction(e -> showViewTeachers(primaryStage));

    Button deleteTeacherButton = new Button("Delete Teacher");
    deleteTeacherButton.setOnAction(e -> showDeleteTeacherForm(primaryStage));

    // Create buttons for other functionalities and set their actions

    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(10));
    layout.getChildren().addAll(addTeacherButton, viewTeachersButton, deleteTeacherButton);

    Scene scene = new Scene(layout, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Quiz Application");
    primaryStage.show();
}

    private void showAddStudentForm(Stage primaryStage) {
    // Create UI components for adding a student
    Label nameLabel = new Label("Student Name:");
    TextField nameField = new TextField();
    Button addButton = new Button("Add");

    // Define the layout for the form
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(10));
    layout.getChildren().addAll(nameLabel, nameField, addButton);

    // Set up the action for the add button
    addButton.setOnAction(e -> {
        String studentName = nameField.getText();

        // Add student to the studentData map
        studentData.add(studentName);

        // Clear the input field
        nameField.clear();

        System.out.println("Student added successfully");
    });

    // Show the form
    Scene scene = new Scene(layout, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Add Student");
    primaryStage.show();
}

private void showDeleteStudentForm(Stage primaryStage) {
    // Create UI components for deleting a student
    Label nameLabel = new Label("Student Name:");
    TextField nameField = new TextField();
    Button deleteButton = new Button("Delete");

    // Define the layout for the form
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(10));
    layout.getChildren().addAll(nameLabel, nameField, deleteButton);

    // Set up the action for the delete button
    deleteButton.setOnAction(e -> {
        String studentName = nameField.getText();

        // Remove student from the studentData map
        studentData.remove(studentName);

        // Clear the input field
        nameField.clear();

        System.out.println("Student deleted successfully");
    });

    // Show the form
    Scene scene = new Scene(layout, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Delete Student");
    primaryStage.show();
}

private void showCreateQuizForm(Stage primaryStage) {
    // Create UI components for creating a quiz
    Label questionLabel = new Label("Question:");
    TextField questionField = new TextField();
    Label optionsLabel = new Label("Options (Separate with commas):");
    TextField optionsField = new TextField();
    Button createButton = new Button("Create");

    // Define the layout for the form
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(10));
    layout.getChildren().addAll(questionLabel, questionField, optionsLabel, optionsField, createButton);

    // Set up the action for the create button
    createButton.setOnAction(e -> {
        String question = questionField.getText();
        String optionsString = optionsField.getText();

        // Split the options by commas and store them in a list
        List<String> options = Arrays.asList(optionsString.split(","));

        // Add the question and options to the quizData list
        QuizQuestion quizQuestion = new QuizQuestion(question, options);
        quizData.add(quizQuestion);

        // Clear the input fields
        questionField.clear();
        optionsField.clear();

        System.out.println("Quiz question created successfully");
    });

    // Show the form
    Scene scene = new Scene(layout, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Create Quiz");
    primaryStage.show();
}

private void showViewAnswers(Stage primaryStage) {
    // Create UI components for displaying the answers of a quiz
    ListView<String> answersListView = new ListView<>();
    answersListView.getItems().addAll(quizAnswers);

    // Show the answers
    Scene scene = new Scene(answersListView, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.setTitle("View Answers");
    primaryStage.show();
}

private void showTakeQuizForm(Stage primaryStage) {
    // Create UI components for taking a quiz
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(10));

    // Iterate over the quiz questions and create UI components for each question
    for (int i = 0; i < quizData.size(); i++) {
        QuizQuestion question = quizData.get(i);
        Label questionLabel = new Label("Question " + (i + 1) + ": " + question.getQuestion());

        // Create radio buttons for each option
        ToggleGroup optionsGroup = new ToggleGroup();
        VBox optionsLayout = new VBox(5);
        for (String option : question.getOptions()) {
            RadioButton radioButton = new RadioButton(option);
            radioButton.setToggleGroup(optionsGroup);
            optionsLayout.getChildren().add(radioButton);
        }

        layout.getChildren().addAll(questionLabel, optionsLayout);
    }

    // Create button for submitting the quiz
    Button submitButton = new Button("Submit");
    layout.getChildren().add(submitButton);

    // Set up the action for the submit button
    submitButton.setOnAction(e -> {
        // Get the selected option for each question
        List<String> selectedOptions = new ArrayList<>();
        for (int i = 0; i < quizData.size(); i++) {
            RadioButton selectedRadioButton = (RadioButton) optionsGroup.getSelectedToggle();
            String selectedOption = selectedRadioButton.getText();
            selectedOptions.add(selectedOption);
        }

        // Store the selected options as the quiz answers
        quizAnswers = selectedOptions;

        System.out.println("Quiz submitted successfully");
    });

    // Show the form
    Scene scene = new Scene(layout, 400, 300);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Take Quiz");
    primaryStage.show();
}

	
    // Placeholder classes for Teacher and Quiz

    private class Teacher {
        private String name;
        private List<String> students;
        private List<Quiz> quizzes;

        public Teacher(String name) {
            this.name = name;
            this.students = new ArrayList<>();
            this.quizzes = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public List<String> getStudents() {
            return students;
        }

        public void addStudent(String studentName) {
            students.add(studentName);
        }

        public void deleteStudent(String studentName) {
            students.remove(studentName);
        }

        public List<Quiz> getQuizzes() {
            return quizzes;
        }

        public void addQuiz(Quiz quiz) {
            quizzes.add(quiz);
        }

        public void deleteQuiz(Quiz quiz) {
            quizzes.remove(quiz);
        }
    }

    private class Quiz {
        private String question;
        private List<String> options;
        private String correctAnswer;

        public Quiz(String question, List<String> options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public List<String> getOptions() {
            return options;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public boolean isCorrectAnswer(String selectedOption) {
            return selectedOption.equals(correctAnswer);
        }
    }
}
