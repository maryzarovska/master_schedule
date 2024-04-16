package Controller;


    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.Date;
    
    public class Advisor {
        private int advisorID;
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
        public Advisor(int advisorID, String firstName, String lastName, String email, String department, Date dateOfBirth, String address, String phone, String officeNumber, String officeHours) {
            this.advisorID = advisorID;
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
    
       
        public void fetchP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
            if (values == null || values.size() != 1) {
                System.out.println("Invalid input.");
                return;
            }
    
            try {
                ArrayList<ArrayList<String>> result = db.getData("SELECT * FROM Advisor WHERE AdvisorID = ?", values);
                if (result.size() != 2 || result.get(1).size() != 10) {
                    System.out.println("Invalid input.");
                    return;
                }
    
                this.advisorID = Integer.parseInt(result.get(1).get(0));
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


        public boolean putP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
          
            String query = "UPDATE Advisor SET FirstName = ?, LastName = ?, Email = ?, Department = ?, DateOfBirth = ?, Address = ?, Phone = ?, OfficeNumber = ?, OfficeHours = ? WHERE AdvisorID = ?";
            boolean result = db.setData(query, values);
            return result;
        }
    
        public boolean postP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
           
    
            String query = "INSERT INTO Advisor (FirstName, LastName, Email, Department, DateOfBirth, Address, Phone, OfficeNumber, OfficeHours) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            boolean result = db.setData(query, values);
            return result;
        }
    
        public boolean removeP(MySQLDatabase db, ArrayList<String> values) throws SQLException {
        
            String query = "DELETE FROM Advisor WHERE AdvisorID = ?";
            boolean result = db.setData(query, values);
            return result;
        }
    }
