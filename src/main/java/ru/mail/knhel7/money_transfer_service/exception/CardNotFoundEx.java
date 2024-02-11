package ru.mail.knhel7.money_transfer_service.exception;

public class CardNotFoundEx extends RuntimeException {

    public CardNotFoundEx(String msg) {
        super(msg);
    }
}
