package ru.mail.knhel7.money_transfer_service.model.transfer.http_request;

import lombok.Getter;
import lombok.Setter;
import ru.mail.knhel7.money_transfer_service.model.Money;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return getFeePercent() == transfer.getFeePercent() &&
                Objects.equals(getCardFromNumber(), transfer.getCardFromNumber()) &&
                Objects.equals(getCardFromValidTill(), transfer.getCardFromValidTill()) &&
                Objects.equals(getCardFromCVV(), transfer.getCardFromCVV()) &&
                Objects.equals(getCardToNumber(), transfer.getCardToNumber()) &&
                Objects.equals(getAmount(), transfer.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getCardFromNumber(), getCardFromValidTill(), getCardFromCVV(),
                getCardToNumber(), getAmount(), getFeePercent());
    }
}
