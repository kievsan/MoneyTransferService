package ru.mail.knhel7.money_transfer_service.logger;

import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import java.util.Date;


public class Logger {
    private static Logger instance = null;
    private int num = 1;

    private Logger() {}

    public static Logger getLogger() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    public void logTransaction(Transaction<?> transaction, String title) {
        // вывод журнала организовать в файл!
        System.out.println(logID() + " " +
                (title.isEmpty() ? title : title + ": №") +
                transaction.getID() + "-" +
                transaction.getStatus().description() + " ==> " +
                transaction.getOperation()
        );
    }

    public void logTitle(Transaction<?> transaction, String title) {
        // вывод журнала организовать в файл!
        System.out.println(logID() + " " +
                (title.isEmpty() ? title : title + ": №") +
                transaction.getID() + "-" +
                transaction.getStatus().description());
    }

    public void logMessage(String msg) {
        // вывод журнала организовать в файл!
        System.out.println(logID() + " " + msg);
    }

    public String logID() {
        return "[" +
                String.format("%1$td.%1$tm.%1$tY %tT", new Date()) +
                ", " +
                num++ +
                "]";
    }
}
