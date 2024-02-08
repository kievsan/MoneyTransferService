package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public record Money(@NotBlank String currency, @Size(min = 0) Integer amount) {

    public Money(String currency, Integer amount) {
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(currency, money.currency) && Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
