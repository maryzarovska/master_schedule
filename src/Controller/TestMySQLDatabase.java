package Controller;



import java.util.Scanner;

public class TestMySQLDatabase {
    public static void main(String[] args) {

        boolean exist = true;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your information to connect");

        System.out.print("Database Type: ");
        String dbType = in.nextLine();

        System.out.print("Server: ");
        String server = in.nextLine();

        System.out.print("Port: ");
        int port = in.nextInt();
        in.nextLine();

        System.out.print("Database Name: ");
        String dbName = in.nextLine();

        System.out.print("Username: ");
        String username = in.nextLine();

        System.out.print("Password: ");
        String password = in.nextLine();

        MySQLDatabase db = new MySQLDatabase(dbType, server, port, dbName, username, password);

        if (db.connect()) {
            System.out.println("\nSuccessfully connected to the database");

        } else {
            System.out.println("\nFailed to connect to the database");
            exist = false;
        }

        System.out.print("Enter SQL SELECT Statement: ");
        String SQLStatement = in.nextLine();

        System.out.print("Do you want to see the name of columns? (y/n): ");
        String choice = in.nextLine();
        boolean includeColumnNames = true;

        if (choice.equals("n")) {
            includeColumnNames = false;
        }

        db.fetch(SQLStatement, includeColumnNames);

        db.printDatabaseInfo();
        db.printResultInfo(SQLStatement);

        System.out.print("Write table name: ");
        String tableName = in.nextLine();
        db.printTableInfo(tableName);

        in.close();

        if (exist) {
            if (db.close()) {
                System.out.println("\nSuccessfully closed the connection");
            } else
                System.out.println("\nFailed to close the connection");
        }
    }
}
