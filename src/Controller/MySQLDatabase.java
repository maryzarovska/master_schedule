package Controller;

import java.sql.*;
import java.util.ArrayList;

public class MySQLDatabase {

    /**
     * Attributes
     */
    private String dbType;
    private String server;
    private int port;
    private String dbName;
    private String username;
    private String password;
    private Connection conn;

    /**
     * Class constructor
     * 
     * @param dbType
     * @param server
     * @param port
     * @param dbName
     * @param username
     * @param password
     */
    public MySQLDatabase(String dbType, String server, int port, String dbName, String username, String password) {
        this.dbType = dbType;
        this.server = server;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    /**
     * method to return database type
     * 
     * @return dbtype
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * method to set database type
     * 
     * @param dbType
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * method to get server
     * 
     * @return server
     */
    public String getServer() {
        return server;
    }

    /**
     * method to set server
     * 
     * @param server
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * method to get port
     * 
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * method to set port
     * 
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * method to get database name
     * 
     * @return dbname
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * mehtod to set database name
     * 
     * @param dbName
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * Method to return the username
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to set the username
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to get the password
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method to set the password
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Methos to check the connection to the database
     * 
     * @return true if the database is connected and false if not or if the exeption
     *         is caught
     */
    public boolean connect() {
        String link = String.format("jdbc:%s://%s:%d/%s", this.getDbType(), this.getServer(), this.getPort(),
                this.getDbName());
        try {
            this.conn = DriverManager.getConnection(link, username, password);
            return true;

        } catch (SQLException e) {
            new DLException(e);
            return false;
        }
    }

    /**
     * Method to check if connection is closed
     * 
     * @return true if connection is closed and false if not or if the exeption is
     *         caught
     */
    public boolean close() {
        try {

            if (!conn.isClosed() && conn != null) {
                conn.close();
                return true;
            } else
                return false;

        } catch (SQLException e) {

            new DLException(e);
            return false;
        }
    }

    /**
     * Method to get the data that was the result of a SELECT statement
     * 
     * @param SQLStatement
     * @return 2D ArrayList of the result from SELECT statement
     */
    public ArrayList<ArrayList<String>> getData(String SQLStatement) {

        // initialize an ArrayList named result, that will store the needed data
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try {
            // Object of class Statement that is used to execute the SQLStatement using the
            // connection to the table
            Statement statement = conn.createStatement();

            // Object of class ResultSet. Usage of executeQuery() to execute the SELECT
            // statement, that will be written in the SQLStatement
            ResultSet resultSet = statement.executeQuery(SQLStatement);

            ResultSetMetaData metaData = resultSet.getMetaData();

            // get the number of columns
            int columnNum = metaData.getColumnCount();

            // loop until there are rows
            while (resultSet.next()) {

                // ArrayList for the rows
                ArrayList<String> row = new ArrayList<>();

                // loop to get over each column
                for (int i = 1; i <= columnNum; i++) {

                    // gets the value of a column with index i
                    row.add(resultSet.getString(i));
                }

                // after finishing with each row, the result is adding to the result 2D
                // ArrayList
                result.add(row);
            }

        } catch (SQLException e) {
            new DLException(e);
        }

        return result;
    }

    /**
     * method exclusively for the new getData method
     * 
     * @param SQLStatement
     * @param includeColumnNames
     * @return
     */
    public void fetch(String SQLStatement, boolean includeColumnNames) {

        for (ArrayList<String> row : this.getData(SQLStatement, includeColumnNames)) {
            for (String element : row) {
                System.out.print(element + " ");
            }

            System.out.println();
        }
    }

    /**
     * Method to get the data that was the result of a SELECT statement
     * 
     * @param SQLStatement
     * @param includeColumnNames
     * @return 2D ArrayList of the result from SELECT statement with column names if
     *         includeColumnNames is true
     */
    public ArrayList<ArrayList<String>> getData(String SQLStatement, boolean includeColumnNames) {

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        if (includeColumnNames) {
            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(SQLStatement);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnNum = metaData.getColumnCount();

                // array list to put the column name as a first row
                ArrayList<String> columnNames = new ArrayList<>();

                // adding to array list the names of rows using metadata to get column names
                for (int i = 1; i <= columnNum; i++) {
                    columnNames.add(metaData.getColumnName(i));
                }

                // adding column names to the result, 2d array list
                result.add(columnNames);

                // loop until there are rows
                while (resultSet.next()) {
                    ArrayList<String> row = new ArrayList<>();

                    for (int i = 1; i <= columnNum; i++) {
                        row.add(resultSet.getString(i));
                    }

                    result.add(row);
                }

            } catch (SQLException e) {
                new DLException(e);
            }

        } else {
            result = getData(SQLStatement);
        }

        return result;
    }

    /**
     * Method that accepts an SQL statement as a string and an arraylist of string
     * values, convert the ResultSet into a simple 2D array of strings with the
     * first row being the column names
     * 
     * @param SQLStatement
     * @param values
     * @return
     */
    public ArrayList<ArrayList<String>> getData(String SQLStatement, ArrayList<String> values) {
        try {
            PreparedStatement preparedStatement = prepare(SQLStatement, values);

            // execute prepared statement
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            ArrayList<ArrayList<String>> result = new ArrayList<>();

            // writting column names as a first row
            ArrayList<String> columnNames = new ArrayList<>();

            for (int i = 1; i <= columnCount; i++) {

                columnNames.add(metaData.getColumnName(i));
            }

            result.add(columnNames);

            // write the rest of the data while that data is available (exist)
            while (resultSet.next()) {

                ArrayList<String> row = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }

                result.add(row);
            }

            return result;

        } catch (SQLException e) {
            new DLException(e);
            return null;
        }
    }

    /**
     * Method for all the UPDATE, DELETE, and INSER operations. If the statement
     * correct - return true, if not or there is an exception - false
     * 
     * @param SQLStatement
     * @return true if at least one row affected, otherwise false
     */
    public boolean setData(String SQLStatement) {
        try {
            // Object of class Statement that is used to execute the SQLStatement using the
            // connection to the table
            Statement statement = conn.createStatement();

            // store the number of affected rows via method executeUpdate() of the
            // SQLStatement
            int affectedRowNum = statement.executeUpdate(SQLStatement);

            // true if at least one row id affected
            if (affectedRowNum > 0)
                return true;
            else
                return false;

        } catch (SQLException e) {
            new DLException(e);
            return false;
        }
    }

    /**
     * Method call the prepare method, execute the statement, and return a Boolean
     * indicating success or failure of the query execution
     * 
     * @param SQLStatement
     * @param values
     * @return true/false
     */
    public boolean setData(String SQLStatement, ArrayList<String> values) {

        try {
            boolean affected = false;
            PreparedStatement preparedStatement = prepare(SQLStatement, values);

            int affectedRows = preparedStatement.executeUpdate();

            // return true if at least one row was affected
            if (affectedRows > 0) {
                affected = true;
            }

            return affected;

        } catch (SQLException e) {
            new DLException(e);
            return false;
        }
    }

    /**
     * Method for printing the database metadata information to the
     * standard output
     */
    public void printDatabaseInfo() {
        // checking if database is connected
        if (conn == null) {
            System.out.println("No connection to the database");

        } else {
            try {
                DatabaseMetaData metaData = conn.getMetaData();

                // printing product name and version, driver name and version
                System.out.printf(
                        "\n\nDatabase Product Name: %s\nDatabase Product Version: %s\nDriver Name: %s\nDriver Version: %s",
                        metaData.getDatabaseProductName(), metaData.getDatabaseProductVersion(),
                        metaData.getDriverName(), metaData.getDriverVersion());

                ResultSet tables = metaData.getTables(null, null, "%", null);

                System.out.println("Tables: ");

                // printing list of all table names with their types
                while (tables.next()) {
                    System.out.println(tables.getString("TABLE_NAME") + " (" + tables.getString("TABLE_TYPE") + ")");
                }

                // printing support for specific features: group by, outer joins, and statement
                // pooling
                System.out.printf(
                        "Support for specific features:\nGroup By: %s\nOuter Joins:  %s\nStatement Pooling: %s",
                        metaData.supportsGroupBy(), metaData.supportsOuterJoins(), metaData.supportsStatementPooling());

            } catch (SQLException e) {
                new DLException(e);
            }
        }
    }

    /**
     * Method for printing the table structure details to the standard output
     * 
     * @param tableName
     */
    public void printTableInfo(String tableName) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();

            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            ResultSetMetaData metaDataRes = columns.getMetaData();
            int columnCount = metaDataRes.getColumnCount();

            // printing column count, column names, and column types
            System.out.printf("\n\nTable: %s\nColumn Count: %s\nColumns: ", tableName, columnCount);

            while (columns.next()) {
                System.out.print(columns.getString("COLUMN_NAME") + " (" + columns.getString("TYPE_NAME") + "), ");
            }

            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
            System.out.print("\nPrimary Keys: ");

            // printing primary keys
            while (primaryKeys.next()) {
                System.out.print(primaryKeys.getString("COLUMN_NAME"));
            }

            // printing the number of rows
            System.out.println("\nRow Count: " + getRowCount(columns));

        } catch (SQLException e) {
            new DLException(e);
        }
    }

    /**
     * Method for printing the result set information to the standard output
     * 
     * @param query
     */
    public void printResultInfo(String query) {
        try {
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // printing query, column count, all column names, column types
            System.out.printf("\n\nQuery: %s\nColumn Count: %d\nColumns: \n", query, columnCount);

            for (int i = 1; i <= columnCount; i++) {
                System.out.println(metaData.getColumnName(i) + " (" + metaData.getColumnTypeName(i) + ") ");
            }

        } catch (SQLException e) {
            new DLException(e);
        }
    }

    /**
     * Method to get the number of rows
     * 
     * @param resultSet
     * @return count
     */
    private int getRowCount(ResultSet resultSet) {
        try {

            int count = 0;

            if (resultSet != null) {
                // we are moving to the last row that is in the ResultSet
                resultSet.last();

                // and we are getting the row number. Since we are now at the last row, so this
                // row
                // represents the number of rows
                count = resultSet.getRow();
            }

            // returning the number of rows
            return count;

        } catch (SQLException e) {
            new DLException(e);
            return -1;
        }
    }

    /**
     * Method which prepares the SQL statement, binds the values, and returns a
     * prepared statement
     * 
     * @param SQLStatement
     * @param values
     * @return prepared statement
     */
    public PreparedStatement prepare(String SQLStatement, ArrayList<String> values) {

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQLStatement);

            // binding the values
            for (int i = 0; i < values.size(); i++) {

                // i+1 as index, because in prepared statements index starts from 1, not 0
                preparedStatement.setString(i + 1, values.get(i));
            }

            return preparedStatement;

        } catch (SQLException e) {
            new DLException(e);
            return null;
        }
    }

    /**
     * Method accepts a string and an arraylist of string values, which will be used
     * to call and execute a stored procedure
     * 
     * @param storedProcedureName
     * @param values
     * @return
     */
    public int executeProc(String storedProcedureName, ArrayList<String> values) {
        try {
            String call = "{CALL " + storedProcedureName + "(";

            for (int i = 0; i < values.size(); i++) {
                if (i > 0) {
                    call += ", ";
                }
                call += "?";
            }

            call += ")}";

            CallableStatement callableStatement = conn.prepareCall(call);

            // bind values
            for (int i = 0; i < values.size(); i++) {
                callableStatement.setString(i + 1, values.get(i));
            }

            callableStatement.execute();

            // get the result
            int result = callableStatement.getInt(values.size() + 1);

            return result;

        } catch (SQLException e) {
            new DLException(e);
            return -1;
        }
    }

    /**
     * method to start a transaction
     */
    public void startTrans() {
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            new DLException(e);
        }
    }

    /**
     * method to end the transaction
     */
    public void endTrans() {
        try {
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            new DLException(e);
        }
    }

    /**
     * method to roll back the transaction
     */
    public void rollbackTrans() {
        try {
            conn.rollback();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            new DLException(e);
        }
    }

    public Connection getConnection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConnection'");
    }
}
