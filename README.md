# Quiz Management System

## Overview
The **Quiz Management System** is a Java-based application designed to facilitate quiz creation, management, and participation. It includes distinct roles for administrators, teachers, and students, each offering specific functionalities. This system is ideal for educational purposes, promoting streamlined quiz management.

## Features

### Admin Role
- **Add Teacher**: Add new teachers by entering their name and password.
- **View Teachers**: Display a list of all registered teachers.
- **Delete Teacher**: Remove a teacher by their name.
- **View Students** (Planned): Display all registered students (not fully implemented).

### Teacher Role
- **Add Student**: Register a new student by entering their name.
- **Delete Student**: Remove a student by their name.
- **Create Quiz**: Create quizzes with multiple questions and answer options.
- **View Answers** (Planned): View submitted answers for quizzes (not fully implemented).

### Student Role
- **Take Quiz**: Participate in quizzes and submit answers.

## Project Structure

src  ├── models/ 
      # Core entities: Teacher, Student, Quiz, Question 
      ├── services/ # Application logic for different roles 
      ├── data/ # Data management (in-memory or database integration) 
      ├── ui/ # Console-based user interface for Admin, Teacher, and Student roles 
      └── Main.java # Entry point for the application
