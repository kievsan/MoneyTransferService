package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transfer {
    @NotBlank
    @Size(min=16, max=16)
    String cardFromNumber;

    @NotBlank
    @Size(min=5, max=5)
    String cardFromValidTill;

    @NotBlank
    @Size(min=3, max=3)
    String cardFromCVV;

    @NotBlank
    @Size(min=16, max=16)
    String cardToNumber;

    Money amount;

    public Transfer() {
    }

    @Override
    public String toString() {
        return "Card №" + cardFromNumber + " ==> " + amount + " ==> Card №" + cardToNumber;
    }
}
