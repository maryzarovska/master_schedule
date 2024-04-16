package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Schedule {
    private int scheduleID;
    private Integer courseID;
    private Integer roomID;
    private Integer timeslotID;
    private Integer dateID;

    // SQL queries for CRUD operations
    private static final String INSERT_QUERY = "INSERT INTO Schedule (courseID, roomID, timeslotID, dateID) VALUES (?, ?, ?, ?)";
    private static final String GET_QUERY = "SELECT * FROM Schedule WHERE scheduleID = ?";
    private static final String UPDATE_QUERY = "UPDATE Schedule SET courseID = ?, roomID = ?, timeslotID = ?, dateID = ? WHERE scheduleID = ?";
    private static final String DELETE_QUERY = "DELETE FROM Schedule WHERE scheduleID = ?";

    public Schedule() {
    }

    public Schedule(int scheduleID, Integer courseID, Integer roomID, Integer timeslotID, Integer dateID) {
        this.scheduleID = scheduleID;
        this.courseID = courseID;
        this.roomID = roomID;
        this.timeslotID = timeslotID;
        this.dateID = dateID;
    }

    // Getters
    public int getScheduleID() {
        return scheduleID;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public Integer getTimeslotID() {
        return timeslotID;
    }

    public Integer getDateID() {
        return dateID;
    }

    // Setters
    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public void setTimeslotID(Integer timeslotID) {
        this.timeslotID = timeslotID;
    }

    public void setDateID(Integer dateID) {
        this.dateID = dateID;
    }

    // Database operations
    public boolean insertSchedule(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.courseID);
            stmt.setInt(2, this.roomID);
            stmt.setInt(3, this.timeslotID);
            stmt.setInt(4, this.dateID);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.scheduleID = generatedKeys.getInt(1);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Schedule getSchedule(MySQLDatabase db, int scheduleID) {
        Schedule schedule = null;
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_QUERY)) {
            stmt.setInt(1, scheduleID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                schedule = new Schedule(
                        rs.getInt("scheduleID"),
                        rs.getInt("courseID"),
                        rs.getInt("roomID"),
                        rs.getInt("timeslotID"),
                        rs.getInt("dateID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    public boolean updateSchedule(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY)) {
            stmt.setInt(1, this.courseID);
            stmt.setInt(2, this.roomID);
            stmt.setInt(3, this.timeslotID);
            stmt.setInt(4, this.dateID);
            stmt.setInt(5, this.scheduleID);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSchedule(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {
            stmt.setInt(1, this.scheduleID);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
