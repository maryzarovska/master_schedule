package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Professor {
    private int professorID;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private Date dateOfBirth;
    private String address;
    private String phone;
    private String officeNumber;
    private String officeHours;

    // Constructor
    public Professor(int professorID, String firstName, String lastName, String email, String department, Date dateOfBirth, String address, String phone, String officeNumber, String officeHours) {
        this.professorID = professorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.officeNumber = officeNumber;
        this.officeHours = officeHours;
    }

    // Getter and setter methods (omitted for brevity)

    // Fetch method
    public void fetchP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
        if (values == null || values.size() != 1) {
            System.out.println("Invalid input.");
            return;
        }

        try {
            ArrayList<ArrayList<String>> result = db.getData("SELECT * FROM Professor WHERE ProfessorID = ?", values);
            if (result.size() != 2 || result.get(1).size() != 10) {
                System.out.println("Invalid input.");
                return;
            }

            this.professorID = Integer.parseInt(result.get(1).get(0));
            this.firstName = result.get(1).get(1);
            this.lastName = result.get(1).get(2);
            this.email = result.get(1).get(3);
            this.department = result.get(1).get(4);
            this.dateOfBirth = java.sql.Date.valueOf(result.get(1).get(5)); // Assuming the date is in "yyyy-MM-dd" format
            this.address = result.get(1).get(6);
            this.phone = result.get(1).get(7);
            this.officeNumber = result.get(1).get(8);
            this.officeHours = result.get(1).get(9);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
    }

    // Update method
    public boolean putP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
        String query = "UPDATE Professor SET FirstName = ?, LastName = ?, Email = ?, Department = ?, DateOfBirth = ?, Address = ?, Phone = ?, OfficeNumber = ?, OfficeHours = ? WHERE ProfessorID = ?";
        return db.setData(query, values);
    }

    // Insert method
    public boolean postP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
        String query = "INSERT INTO Professor (FirstName, LastName, Email, Department, DateOfBirth, Address, Phone, OfficeNumber, OfficeHours) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return db.setData(query, values);
    }

    // Delete method
    public boolean removeP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
        String query = "DELETE FROM Professor WHERE ProfessorID = ?";
        return db.setData(query, values);
    }
}
