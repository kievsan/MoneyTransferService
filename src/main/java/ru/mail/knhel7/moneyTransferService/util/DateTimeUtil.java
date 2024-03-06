package ru.mail.knhel7.moneyTransferService.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {

    public static String getDate() {
        return DateTimeFormatter.ofPattern("yy-MM-d").format(LocalDateTime.now());
    }

    public static String getTime() {
        return DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now());
    }

    public static String timestamp() {
        return getDate() + " " + getTime();
    }

    public String timestampExample2() {
        return String.format("%1$td.%1$tm.%1$tY %tT", new Date());
    }

    public String timestampExample3() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
}
