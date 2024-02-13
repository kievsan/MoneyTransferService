package ru.mail.knhel7.money_transfer_service.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateTimeTool {

    public static String getDate() {
        return DateTimeFormatter.ofPattern("yy-MM-d").format(LocalDateTime.now());
    }

    public static String getTime() {
        return DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now());
    }

}
