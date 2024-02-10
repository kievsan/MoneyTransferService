package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotBlank
public class Transfer {
    String cardFromNumber;
    String cardFromValidTill;
    String cardFromCVV;
    String cardToNumber;
    Money amount;

    public Transfer() {
    }

    @Override
    public String toString() {
        return "Card №" + cardFromNumber + " ==> " + amount + " ==> Card №" + cardToNumber;
    }
}
