package helper.common;

import helper.formatter.LogMessageFormatter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

        createLoggerFile();

        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(fileHandler);

        return logger;
    }

    private static void createLoggerFile() {
        if (fileHandler == null) {
            synchronized (ApplicationLogger.class) {
                if (fileHandler == null) {
                    try {
                        URI applicationDirectory = ApplicationLogger.class.getProtectionDomain().getCodeSource().getLocation().toURI();


                        if (Paths.get(applicationDirectory).getParent().toString().equals("/")) {
                            applicationDirectory = URI.create(System.getProperty("java.home"));
                        }

                        Path logPath = Paths.get(applicationDirectory.getPath() + "/logs", String.format("%s.log", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

                        if (Files.notExists(logPath.getParent())) {
                            Files.createDirectory(logPath.getParent());
                        }

                        fileHandler = new FileHandler(logPath.toString(), true);
                        fileHandler.setLevel(Level.ALL);
                        fileHandler.setFormatter(new LogMessageFormatter());
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
