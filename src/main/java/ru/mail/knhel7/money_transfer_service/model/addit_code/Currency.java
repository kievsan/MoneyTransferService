package ru.mail.knhel7.money_transfer_service.model.addit_code;

public enum Currency {

    CNY ("CNY"), EUR ("EUR"), RUR ("RUR"), USD ("USD");

    private final String title;

    Currency(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
