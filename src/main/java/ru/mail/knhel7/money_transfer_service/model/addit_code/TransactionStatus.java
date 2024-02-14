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

    public String description() {
        String description = switch (this) {
            case NEW -> "NEW";
            case REFUSED -> "REFUSED";
            case DELETED -> "DELETED";
            case ACCEPTED -> "ACCEPTED";
            case EXECUTED -> "EXECUTED";
            case REFUNDED -> "REFUNDED";
            case COMPLETED -> "COMPLETED";
            case ARCHIVED -> "ARCHIVED";
        };
        return description;
    }

    @Override
    public String toString() {
        return title;
    }
}
