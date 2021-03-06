package ru.glance.helper.formatter;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Объект форматтер для сообщений лога.
 * <p>
 * project: glcmtx
 * author:  kostrovik
 * date:    19/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class LogMessageFormatter extends SimpleFormatter {
    @Override
    public String format(LogRecord record) {
        String result = super.format(record);
        result = result + "\n";
        return result;
    }
}
