package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

@Getter
@Setter
@NotBlank
public class Card {

    private String number;
    private String codeCVV;
    private String validMonthYear;
    private Currency currency;
    private Integer amount;

    public Card() {}

    public Card(String cardNumber, String codeCVV, String validMonthYear, Currency currency, Integer amount) {
        this.number = cardNumber;
        this.codeCVV = codeCVV;
        this.validMonthYear = validMonthYear;
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(getNumber(), card.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber());
    }
}
