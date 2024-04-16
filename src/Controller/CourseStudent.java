package Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseStudent {
    private int enrollmentID;
    private int studentID;
    private int courseID;
    private double grade;

    public CourseStudent(int enrollmentID, int studentID, int courseID, double grade) {
        this.enrollmentID = enrollmentID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.grade = grade;
    }

    public int getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(int enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void put(Connection connection) {
        String sql = "UPDATE CourseStudent SET StudentID = ?, CourseID = ?, Grade = ? WHERE EnrollmentID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentID);
            statement.setInt(2, courseID);
            statement.setDouble(3, grade);
            statement.setInt(4, enrollmentID);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error updating course student: " + e.getMessage());
        }
    }

    public void post(Connection connection) {
        String sql = "INSERT INTO CourseStudent (StudentID, CourseID, Grade) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentID);
            statement.setInt(2, courseID);
            statement.setDouble(3, grade);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error inserting course student: " + e.getMessage());
        }
    }

    public void remove(Connection connection) {
        String sql = "DELETE FROM CourseStudent WHERE EnrollmentID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, enrollmentID);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error deleting course student: " + e.getMessage());
        }
    }

}