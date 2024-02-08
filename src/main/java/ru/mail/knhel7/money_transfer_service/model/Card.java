package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NotBlank
public class Card {

    private String number;
    private String CVV;
    private String validTill;
    private Map<String, Integer> amount;

    public Card() {}

    public Card(String number, String CVV, String validTill, Money amount) {
        this.number = number;
        this.CVV = CVV;
        this.validTill = validTill;
        this.amount = new HashMap<>();
        putMoney(amount);
    }

    public void putMoney(Money amount) {
        if (getAmount().containsKey(amount.currency())) {
            Integer availableMoney = this.amount.get(amount.currency());
            this.amount.put(amount.currency(), availableMoney + amount.amount());
        } else {
            this.amount.put(amount.currency(), amount.amount());
        }
    }

    public String spendMoney(Money money) {
        if (getAmount().containsKey(money.currency())) {
            Integer availableMoney = this.amount.get(money.currency());
            if (availableMoney < money.amount()) {
                return "so little money '" + money.currency() + "'";
            }
            this.amount.put(money.currency(), availableMoney - money.amount());
            return "ok";
        }
        return "no money '" + money.currency() + "'";
    }

    public Money getAmountPercentage(Money money, double percentage) {
        return new Money(money.currency(), Math.toIntExact(Math.round(money.amount() * percentage / 100)));
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
