package helper.common;

import helper.formatter.LogMessageFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    19/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ApplicationLogger extends Logger {
    private static FileHandler fileHandler;

    private ApplicationLogger() {
        super(null, null);
    }

    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        try {
            Path logPath = Paths.get(System.getProperty("java.home") + "/logs", String.format("%s.log", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            String filepath = logPath.toString();

            if (Files.notExists(logPath.getParent())) {
                Files.createDirectory(logPath.getParent());
            }

            fileHandler = new FileHandler(filepath, true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new LogMessageFormatter());

            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logger;
    }
}
