package Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Course {
    private int courseID;
    private String courseName;
    private int professorID;
    private String description;
    private String major;
    private double credits;

    public Course(int courseID, String courseName, int professorID, String description, String major, double credits) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.professorID = professorID;
        this.description = description;
        this.major = major;
        this.credits = credits;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getProfessorID() {
        return professorID;
    }

    public void setProfessorID(int professorID) {
        this.professorID = professorID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }



    public void put(Connection connection) {
        String sql = "UPDATE Course SET CourseName = ?, ProfessorID = ?, Description = ?, Major = ?, Credits = ? WHERE CourseID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, courseName);
            statement.setInt(2, professorID);
            statement.setString(3, description);
            statement.setString(4, major);
            statement.setDouble(5, credits);
            statement.setInt(6, courseID);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error updating course: " + e.getMessage());
        }

    }

    public void post(Connection connection) {
        String sql = "INSERT INTO Course (CourseName, ProfessorID, Description, Major, Credits) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, courseName);
            statement.setInt(2, professorID);
            statement.setString(3, description);
            statement.setString(4, major);
            statement.setDouble(5, credits);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error inserting course: " + e.getMessage());
        }
    }

    public void remove(Connection connection) {
        String sql = "DELETE FROM Course WHERE CourseID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, courseID);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error deleting course: " + e.getMessage());
        }
    }

}