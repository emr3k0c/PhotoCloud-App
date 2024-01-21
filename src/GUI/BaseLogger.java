package GUI;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * BaseLogger is a class for logging application info and error messages into respective text files.
 * It provides two inner static classes for info and error logging.
 */
public class BaseLogger {
	
	private static final String TO_WRITE_LOG_INFO = "application_info.txt";
    private static final String TO_WRITE_LOG_ERROR = "application_error.txt";
    
    
    public static class Info {
        public static void log(String message) {
            writeToTxt(TO_WRITE_LOG_INFO, "[INFO]", message);
        }
    }

    public static class Error {
        public static void log(String message) {
            writeToTxt(TO_WRITE_LOG_ERROR, "[ERROR]", message);
        }
    }
    
    private static void writeToTxt(String fileName, String logType, String message) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            LocalDateTime dateTime = LocalDateTime.now();
            ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
            String formattedDateTime = zonedDateTime.format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy"));
            out.println("[" + formattedDateTime + "]" + logType + " " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}


