package ru.mail.knhel7.money_transfer_service.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String getDate() {
        return DateTimeFormatter.ofPattern("yy-MM-d").format(LocalDateTime.now());
    }

    public static String getTime() {
        return DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now());
    }

    public static String timestamp() {
        return DateTimeUtil.getDate() + " " + DateTimeUtil.getTime();
    }

}
