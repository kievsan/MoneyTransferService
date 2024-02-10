package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public record Money(@NotBlank Currency currency, @Size(min = 0) Integer value) {

    public Money(Currency currency, Integer value) {
        this.currency = currency;
        this.value = value;
    }

    public Money getPercentage(double percentage) {
        return new Money(currency, Math.toIntExact(Math.round(value * percentage / 100)));
    }
}
