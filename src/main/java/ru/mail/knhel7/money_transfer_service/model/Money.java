package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.Min;

public record Money(String currency , @Min(value = 0, message = "should not be negative") Integer value) {

    public Money() {
        this(0);
    }

    public Money(Integer value) {
        this("RUR", value);
    }

    public Money(String currency, @Min(value = 0, message = "should not be negative") Integer value) {
        this.currency = "RUR";
        this.value = value;
    }

    public Money getPercentage(double percentage) {
        return new Money(currency, Math.toIntExact(Math.round(value * percentage / 100)));
    }

    @Override
    public String toString() {
        return "(" + (double)value/100 + " " + currency + ")";
    }
}
