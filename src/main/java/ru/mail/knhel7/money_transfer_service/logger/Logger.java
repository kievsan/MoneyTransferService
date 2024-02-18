package ru.mail.knhel7.money_transfer_service.logger;

import lombok.Getter;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import ru.mail.knhel7.money_transfer_service.model.http_response.TransferExResp;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;


public class Logger {

    private static Logger instance = null;
    private int num = 1;
    private boolean isFileMode = true;
    @Getter
    private String filePath = "transfer.log";
    private File logFile;


    private Logger() {}


    public static Logger getLogger() {
        if (instance == null) {
            instance = new Logger();
            instance.logFile = new File("./" + instance.filePath);
            try {
                if (instance.logFile.createNewFile()) {
                    instance.logMessage("Создан " + instance.filePath);
                } else {
                    instance.logMessage("Найден " + instance.filePath);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        return instance;
    }

    public String getLogID() {
        return "[" +
                String.format("%1$td.%1$tm.%1$tY %tT", new Date()) +
                ", " +
                num++ +
                "]";
    }

    public String getLogTransactionTitle(Transaction<?> transaction, String title) {
        return getLogID() + " " +
                (title.isEmpty() ? title : title + ": №") +
                transaction.getID() + "-" +
                transaction.getStatus().description();
    }

    public void logTransaction(Transaction<?> transaction, String title) {
        // вывод журнала организовать в файл!
        String msg = getLogTransactionTitle(transaction, title) + " ==> " + transaction.getOperation();
        if (isFileMode) {
            toFile(msg);
        } else {
            System.out.println(msg);
        }
    }

    public void logTransactionTitle(Transaction<?> transaction, String title) {
        // вывод журнала организовать в файл!
        String msg = getLogTransactionTitle(transaction, title);
        if (isFileMode) {
            toFile(msg);
        } else {
            System.out.println(msg);
        }
    }

    public void logMessage(String message) {
        // вывод журнала организовать в файл!
        String msg = getLogID() + " " + message;
        if (isFileMode) {
            toFile(msg);
        } else {
            System.out.println(msg);
        }
    }

    public void logTransactionErr(TransferExResp ex) {
        // вывод журнала организовать в файл!
        String msg = getLogID() + " " + ex;
        if (isFileMode) {
            toFile(msg);
        } else {
            System.out.println(msg);
        }
    }


    public boolean isFileMode() {
        return isFileMode;
    }

    public boolean setTerminalMode() {
        isFileMode = false;
        return isFileMode;
    }

    public boolean setFileMode() {
        isFileMode = true;
        return true;
    }

    public String setFilePath(String filePath) {
        this.filePath = filePath;
        isFileMode = true;
        logFile = new File("./" + filePath);
        // ...
        return this.filePath;
    }


    public void toFile(String log) {
        if (logFile == null) {
            return;
        }
        if (logFile.canWrite()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
                writer.append(log).append("\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
