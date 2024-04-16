package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Student {
    private int studentID;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private String address;
    private String phone;
    private String major;
    private String year;
    private double gpa;
    private int advisorID;

    // Constructor
    public Student(int studentID, String firstName, String lastName, String email, Date dateOfBirth, String address, String phone, String major, String year, double gpa, int advisorID) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.major = major;
        this.year = year;
        this.gpa = gpa;
        this.advisorID = advisorID;
    }

    // Getter and setter methods (omitted for brevity)

    // Fetch method
    public void fetchP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
        if (values == null || values.size() != 1) {
            System.out.println("Invalid input.");
            return;
        }

        try {
            ArrayList<ArrayList<String>> result = db.getData("SELECT * FROM Student WHERE StudentID = ?", values);
            if (result.size() != 2 || result.get(1).size() != 11) {
                System.out.println("Invalid input.");
                return;
            }

            this.studentID = Integer.parseInt(result.get(1).get(0));
            this.firstName = result.get(1).get(1);
            this.lastName = result.get(1).get(2);
            this.email = result.get(1).get(3);
            this.dateOfBirth = java.sql.Date.valueOf(result.get(1).get(4)); // Assuming the date is in "yyyy-MM-dd" format
            this.address = result.get(1).get(5);
            this.phone = result.get(1).get(6);
            this.major = result.get(1).get(7);
            this.year = result.get(1).get(8);
            this.gpa = Double.parseDouble(result.get(1).get(9));
            this.advisorID = Integer.parseInt(result.get(1).get(10));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
    }

    // Update method
    public boolean putP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
        String query = "UPDATE Student SET FirstName = ?, LastName = ?, Email = ?, DateOfBirth = ?, Address = ?, Phone = ?, Major = ?, Year = ?, GPA = ?, AdvisorID = ? WHERE StudentID = ?";
        return db.setData(query, values);
    }

    // Insert method
    public boolean postP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
        String query = "INSERT INTO Student (FirstName, LastName, Email, DateOfBirth, Address, Phone, Major, Year, GPA, AdvisorID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return db.setData(query, values);
    }

    // Delete method
    public boolean removeP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
        String query = "DELETE FROM Student WHERE StudentID = ?";
        return db.setData(query, values);
    }
}
