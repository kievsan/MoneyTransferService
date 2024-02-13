package ru.mail.knhel7.money_transfer_service.model.addit_code;

public enum TransactionStatus {

    NEW             ("00"),
    REFUSED         ("01"),
    DELETED         ("02"),
    ACCEPTED        ("03"),
    EXECUTED        ("06"),
    REFUNDED        ("07"),
    COMPLETED       ("08"),
    ARCHIVED        ("09");

    private final String title;

    TransactionStatus(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
