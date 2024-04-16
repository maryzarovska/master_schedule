package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Room {
    private int roomID;
    private String roomNumber;
    private Integer capacity;
    private String location;
    private String building;
    private int floor;
    private String type;

    // SQL queries for CRUD operations
    private static final String INSERT_QUERY = "INSERT INTO Room (roomNumber, capacity, location, building, floor, type) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_QUERY = "SELECT * FROM Room WHERE roomID = ?";
    private static final String UPDATE_QUERY = "UPDATE Room SET roomNumber = ?, capacity = ?, location = ?, building = ?, floor = ?, type = ? WHERE roomID = ?";
    private static final String DELETE_QUERY = "DELETE FROM Room WHERE roomID = ?";

    public Room() {
    }

    public Room(int roomID, String roomNumber, Integer capacity, String location, String building, int floor, String type) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.location = location;
        this.building = building;
        this.floor = floor;
        this.type = type;
    }

    // Getters
    public int getRoomID() {
        return roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getLocation() {
        return location;
    }

    public String getBuilding() {
        return building;
    }

    public int getFloor() {
        return floor;
    }

    public String getType() {
        return type;
    }

    // Setters
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Database operations
    public boolean insertRoom(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.roomNumber);
            stmt.setInt(2, this.capacity);
            stmt.setString(3, this.location);
            stmt.setString(4, this.building);
            stmt.setInt(5, this.floor);
            stmt.setString(6, this.type);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.roomID = generatedKeys.getInt(1);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Room getRoom(MySQLDatabase db, int roomID) {
        Room room = null;
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_QUERY)) {
            stmt.setInt(1, roomID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                room = new Room(
                        rs.getInt("roomID"),
                        rs.getString("roomNumber"),
                        rs.getObject("capacity", Integer.class), // Use getObject for nullable Integer
                        rs.getString("location"),
                        rs.getString("building"),
                        rs.getInt("floor"),
                        rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    public boolean updateRoom(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY)) {
            stmt.setString(1, this.roomNumber);
            stmt.setInt(2, this.capacity);
            stmt.setString(3, this.location);
            stmt.setString(4, this.building);
            stmt.setInt(5, this.floor);
            stmt.setString(6, this.type);
            stmt.setInt(7, this.roomID);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRoom(MySQLDatabase db) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {
            stmt.setInt(1, this.roomID);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
