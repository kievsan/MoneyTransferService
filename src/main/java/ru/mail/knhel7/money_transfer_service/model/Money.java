package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.Min;
import ru.mail.knhel7.money_transfer_service.model.addit_code.Currency;

public record Money(Currency currency, @Min(value = 0, message = "should not be negative") Integer value) {

    public Money(Currency currency, Integer value) {
        this.currency = currency;
        this.value = value;
    }

    public Money getPercentage(double percentage) {
//        return new Money(currency, Math.toIntExact(Math.round(value * percentage / 100)));
        return new Money(currency, Math.toIntExact(Math.round(value * percentage / 100)));
    }

    @Override
    public String toString() {
        return "(" + (double)value/100 + " " + currency + ")";
    }
}
