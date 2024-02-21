package ru.mail.knhel7.money_transfer_service.model.transfer.http_request;

import lombok.Getter;
import lombok.Setter;
import ru.mail.knhel7.money_transfer_service.model.Money;

@Getter
@Setter
public class Transfer {

//    @NotBlank @Size(min = 16, max = 16) String cardFromNumber;
//    @NotBlank @Size(min = 5, max = 5) String cardFromValidTill;
//    @NotBlank @Size(min = 3, max = 3) String cardFromCVV;
//    @NotBlank @Size(min = 16, max = 16) String cardToNumber;
//
//    @NotNull Money amount;

    String cardFromNumber;
    String cardFromValidTill;
    String cardFromCVV;
    String cardToNumber;

    Money amount;

    int feePercent=1;
    Money commission;

    public Transfer() {
    }

    public Transfer(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Money amount) {
        super();
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    public Money setCommission() {
        this.commission = amount.getPercentage(feePercent);
        return this.commission;
    }

    @Override
    public String toString() {
        return "Card №" + cardFromNumber + " ==> " +
                amount + " + " + setCommission() + ":" + feePercent + "%" +
                " ==> Card №" + cardToNumber;
    }
}