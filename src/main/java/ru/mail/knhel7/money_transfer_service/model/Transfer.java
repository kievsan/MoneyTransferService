package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transfer {

    @NotBlank @Size(min = 16, max = 16) String cardFromNumber;
    @NotBlank @Size(min = 5, max = 5) String cardFromValidTill;
    @NotBlank @Size(min = 3, max = 3) String cardFromCVV;
    @NotBlank @Size(min = 16, max = 16) String cardToNumber;

    Money amount;

    @Min(0)
    private Integer id;

    public Transfer() {
        this.id = 0;
    }

    public Transfer(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Money amount) {
        super();
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    public void setId(Integer id) {
        if (this.id == 0) {
            this.id = id;
        }
    }


    @Override
    public String toString() {
        return "Card №" + cardFromNumber + " ==> " + amount + " ==> Card №" + cardToNumber;
    }
}
