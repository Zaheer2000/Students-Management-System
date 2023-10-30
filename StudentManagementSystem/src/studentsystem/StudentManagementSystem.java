package studentsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentManagementSystem {
    
    public static void main(String[] args) {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");  
    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "Zaheer$989084");
            Statement stmt = con.createStatement(); 
            
            // Create the students table if it doesn't exist
            stmt.execute("CREATE TABLE IF NOT EXISTS students (" +
                    "id INT PRIMARY KEY , " +
                    "name VARCHAR(255) NOT NULL, " +
                    "age INT, " +
                    "grade DOUBLE, " +
                    "attendance BOOLEAN)");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nStudent Management System");
                System.out.println("===========================");
                System.out.println("1. Add Student");
                System.out.println("2. Update Student");
                System.out.println("3. Delete Student");
                System.out.println("4. Generate Report");
                System.out.println("5. Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println("===================");

                switch (choice) {
                    case 1:
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter student age: ");
                        int age = scanner.nextInt();
                        System.out.print("Enter student grade: ");
                        double grade = scanner.nextDouble();
                        System.out.print("Is student present? (true/false): ");
                        boolean attendance = scanner.nextBoolean();

                        String insertSql = "INSERT INTO students (name, age, grade, attendance) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement pstmt = con.prepareStatement(insertSql)) {
                            pstmt.setString(1, name);
                            pstmt.setInt(2, age);
                            pstmt.setDouble(3, grade);
                            pstmt.setBoolean(4, attendance);
                            pstmt.executeUpdate();
                            System.out.println("Student added successfully.");
                            System.out.println("===========================");
                        }
                        break;

                    case 2:
                        System.out.print("Enter student ID to update: ");
                        int studentId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter updated name: ");
                        String updatedName = scanner.nextLine();
                        System.out.print("Enter updated age: ");
                        int updatedAge = scanner.nextInt();
                        System.out.print("Enter updated grade: ");
                        double updatedGrade = scanner.nextDouble();
                        System.out.print("Is student present? (true/false): ");
                        boolean updatedAttendance = scanner.nextBoolean();

                        String updateSql = "UPDATE students SET name = ?, age = ?, grade = ?, attendance = ? WHERE id = ?";
                        try (PreparedStatement pstmt = con.prepareStatement(updateSql)) {
                            pstmt.setString(1, updatedName);
                            pstmt.setInt(2, updatedAge);
                            pstmt.setDouble(3, updatedGrade);
                            pstmt.setBoolean(4, updatedAttendance);
                            pstmt.setInt(5, studentId);
                            int rowsAffected = pstmt.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Student updated successfully.");
                                System.out.println("============================");
                            } else {
                                System.out.println("No student found with the given ID.");
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter student ID to delete: ");
                        int idToDelete = scanner.nextInt();

                        String deleteSql = "DELETE FROM students WHERE id = ?";
                        try (PreparedStatement pstmt = con.prepareStatement(deleteSql)) {
                            pstmt.setInt(1, idToDelete);
                            int rowsAffected = pstmt.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Student deleted successfully.");
                                System.out.println("============================");
                            } else {
                                System.out.println("No student found with the given ID.");
                            }
                        }
                        break;

                    case 4:
                        String selectSql = "SELECT * FROM students";
                        try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                            System.out.println("\nStudent Records:");
                            System.out.println("===============");
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                String studentName = resultSet.getString("name");
                                int studentAge = resultSet.getInt("age");
                                double studentGrade = resultSet.getDouble("grade");
                                boolean studentAttendance = resultSet.getBoolean("attendance");

                                System.out.println("ID: " + id + ", Name: " + studentName + ", Age: " + studentAge +
                                        ", Grade: " + studentGrade + ", Attendance: " + studentAttendance);
                            }
                        }
                        break;

                    case 5:
                    	System.out.println("************************************");
                        System.out.println("       Good Bye.Thank You...!");
                        System.out.println("************************************");
                        scanner.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}