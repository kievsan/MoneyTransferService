package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transfer {
    @NotBlank
    String cardFromNumber;
    @NotBlank
    String cardFromValidTill;
    @NotBlank
    String cardFromCVV;
    @NotBlank
    String cardToNumber;
    @NotBlank
    Money amount;

    public Transfer() {
    }

    @Override
    public String toString() {
        return "Card №" + cardFromNumber + " ==> " + amount + " ==> Card №" + cardToNumber;
    }
}
