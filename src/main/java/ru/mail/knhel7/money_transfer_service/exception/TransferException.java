package ru.mail.knhel7.money_transfer_service.exception;

public class TransferException extends RuntimeException {

    public TransferException(String msg) {
        super(msg);
    }
}
