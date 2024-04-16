package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRoom {
    private int courseRoomID;
    private int courseID;
    private int roomID;

    // SQL queries for database operations
    private static final String INSERT_QUERY = "INSERT INTO CourseRoom (courseID, roomID) VALUES (?, ?)";
    private static final String GET_QUERY = "SELECT * FROM CourseRoom WHERE courseRoomID = ?";
    private static final String UPDATE_QUERY = "UPDATE CourseRoom SET courseID = ?, roomID = ? WHERE courseRoomID = ?";
    private static final String DELETE_QUERY = "DELETE FROM CourseRoom WHERE courseRoomID = ?";

    public CourseRoom() {
    }

    public CourseRoom(int courseRoomID, int courseID, int roomID) {
        this.courseRoomID = courseRoomID;
        this.courseID = courseID;
        this.roomID = roomID;
    }

    public int getCourseRoomID() {
        return courseRoomID;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getRoomID() {
        return roomID;
    }

    // Setters
    public void setCourseRoomID(int courseRoomID) {
        this.courseRoomID = courseRoomID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    // Database operations
    public boolean insertCourseRoom(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.courseID);
            stmt.setInt(2, this.roomID);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.courseRoomID = generatedKeys.getInt(1);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCourseRoom(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY)) {
            stmt.setInt(1, this.courseID);
            stmt.setInt(2, this.roomID);
            stmt.setInt(3, this.courseRoomID);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static CourseRoom getCourseRoom(MySQLDatabase db, int courseRoomID) {
        CourseRoom courseRoom = null;
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_QUERY)) {
            stmt.setInt(1, courseRoomID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                courseRoom = new CourseRoom(
                        rs.getInt("courseRoomID"),
                        rs.getInt("courseID"),
                        rs.getInt("roomID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseRoom;
    }

    public boolean deleteCourseRoom(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {
            stmt.setInt(1, this.courseRoomID);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
