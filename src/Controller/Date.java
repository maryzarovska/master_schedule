package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Date {
    private int dateID;
    private java.sql.Date date;
    private String dayOfWeek;

    // SQL queries for CRUD operations
    private static final String INSERT_QUERY = "INSERT INTO Date (date, dayOfWeek) VALUES (?, ?)";
    private static final String GET_QUERY = "SELECT * FROM Date WHERE dateID = ?";
    private static final String UPDATE_QUERY = "UPDATE Date SET date = ?, dayOfWeek = ? WHERE dateID = ?";
    private static final String DELETE_QUERY = "DELETE FROM Date WHERE dateID = ?";

    public Date() {
    }

    public Date(int dateID, java.sql.Date date, String dayOfWeek) {
        this.dateID = dateID;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
    }

    // Getters
    public int getDateID() {
        return dateID;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    // Setters
    public void setDateID(int dateID) {
        this.dateID = dateID;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    // Database operations
    public boolean insertDate(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, this.date);
            stmt.setString(2, this.dayOfWeek);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.dateID = generatedKeys.getInt(1);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Date getDate(MySQLDatabase db, int dateID) {
        Date date = null;
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_QUERY)) {
            stmt.setInt(1, dateID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                date = new Date(
                        rs.getInt("dateID"),
                        rs.getDate("date"),
                        rs.getString("dayOfWeek"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return date;
    }

    public boolean updateDate(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY)) {
            stmt.setDate(1, this.date);
            stmt.setString(2, this.dayOfWeek);
            stmt.setInt(3, this.dateID);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDate(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {
            stmt.setInt(1, this.dateID);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
