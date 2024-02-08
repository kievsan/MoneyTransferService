package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public Integer getAmountPercentage(Money money, double percentage) {
        return Math.toIntExact(Math.round(money.amount() * percentage / 100));
    }
}
