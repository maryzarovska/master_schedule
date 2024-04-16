package Controller;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.*;

public class DLException extends Exception {

    /**
     * attributes
     */
    private String message;
    private int errorCode;
    private String sqlState;
    private ArrayList<String> info;

    /**
     * constants
     */
    private static final String INITIAL_MESSAGE = "Unable to complete operation. Please contact the administrator.";
    private final Logger logger = Logger.getLogger("DLException.java");

    /**
     * constructor
     * 
     * @param exception
     */
    public DLException(Exception exception) {

        super(INITIAL_MESSAGE, exception);
        getExceptionInfo(exception);
        this.info = new ArrayList<>();

        log();
    }

    /**
     * constructor with additional information
     * 
     * @param exception
     * @param information
     */
    public DLException(Exception exception, String... information) {
        super(INITIAL_MESSAGE, exception);

        getExceptionInfo(exception);
        this.info = new ArrayList<>();
        for (String string : information) {
            this.info.add(string);
        }

        log();
    }

    /**
     * method to get the information from exception
     * 
     * @param exception
     */
    private void getExceptionInfo(Exception exception) {
        /**
         * checking if exception is sqlexception and getting all info from exception
         */
        if (exception instanceof SQLException) {
            SQLException sqlException = (SQLException) exception;

            this.message = sqlException.getMessage();
            this.errorCode = sqlException.getErrorCode();
            this.sqlState = sqlException.getSQLState();

        } else {
            this.message = exception.getMessage();
        }
    }

    /**
     * method for returning message of the exception
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * method for returning error code of the exception
     * 
     * @return error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * method for returning sql state of the error
     * 
     * @return sql state
     */
    public String getSqlState() {
        return sqlState;
    }

    /**
     * method for returning array list where all additional information will be
     * stored
     * 
     * @return info
     */
    public ArrayList<String> getInfo() {
        return info;
    }

    /**
     * log method to write the exception details into the exceptions.log file
     */
    private void log() {

        /**
         * taking the date (time) when the log method called
         */
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timeStamp = date.format(new Date());

        try {
            /**
             * creating a file exceptions.log to store information about all exceptions
             */
            FileHandler fileHandler = new FileHandler("exceptions.log", true);
            this.logger.addHandler(fileHandler);

            /**
             * writting all info that we have about exception into the string
             */
            String loggerMessage = String.format("\nTimestamp: %s\nMessage: %s\nSQL State: %s\nError Code: %d\n",
                    timeStamp, this.getMessage(), this.getSqlState(), this.getErrorCode());

            /**
             * taking additional info from array list with for each loop to add that to our
             * message
             */
            if (!info.isEmpty()) {
                loggerMessage += "Additional Information: ";
                for (String string : info) {
                    loggerMessage += String.format("\n%s\n", string);
                }
            }

            /**
             * output of message and closing file
             */
            logger.log(Level.WARNING, loggerMessage);
            fileHandler.close();

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
