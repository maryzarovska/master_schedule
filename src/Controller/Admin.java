package Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Admin {
    private int adminID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String address;

    public Admin(int adminID, String firstName, String lastName, String email, String phone, Date dateOfBirth, String address) {
        this.adminID = adminID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void put(Connection connection) {
        String sql = "UPDATE Admin SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, DateOfBirth = ?, Address = ? WHERE AdminID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setDate(5, new java.sql.Date(dateOfBirth.getTime()));
            statement.setString(6, address);
            statement.setInt(7, adminID);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
        }
    }

    public void post(Connection connection) {
        String sql = "INSERT INTO Admin (FirstName, LastName, Email, Phone, DateOfBirth, Address) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setDate(5, new java.sql.Date(dateOfBirth.getTime()));
            statement.setString(6, address);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error inserting admin: " + e.getMessage());
        }
    }

    public void remove(Connection connection) {
        String sql = "DELETE FROM Admin WHERE AdminID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, adminID);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
        }
    }


}
