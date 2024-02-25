package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.util.Objects;



public record Money(@Getter String currency ,
                    @Getter @Min(value = 0, message = "should not be negative"
                    ) Integer value) {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(currency, money.currency) && Objects.equals(value, money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, value);
    }
}
