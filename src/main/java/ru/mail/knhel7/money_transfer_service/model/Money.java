package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Money(@NotBlank String currency, @Size(min = 0) Integer amount) {

    public Money(String currency, Integer amount) {
        this.currency = currency;
        this.amount = amount;
    }
}
