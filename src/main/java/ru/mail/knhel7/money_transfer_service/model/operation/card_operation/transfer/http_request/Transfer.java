package ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request;

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

    String cardFromNumber, cardFromValidTill, cardFromCVV, cardToNumber;
    Money amount, feeCommission;
    int feeAmount, feePercent = 1;

    public Transfer() {
    }

    public Transfer(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber,
                    Money amount, int feeAmount, int feePercent) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
        this.feeAmount = feeAmount;
        this.feePercent = feePercent;
    }

    public Transfer(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Money amount) {
        this(cardFromNumber, cardFromValidTill, cardFromCVV, cardToNumber, amount, 0, 1);
    }

    public Money setFeeCommission() {
        feeCommission = amount.getPercentage(feePercent);
        feeCommission = feeCommission.value() < feeAmount ? new Money(feeAmount) : feeCommission;
        return feeCommission;
    }

    public Money setFeeCommission(int feeAmount) {
        this.feeAmount = feeAmount;
        return setFeeCommission();
    }

    public Money setFeeCommission(int feeAmount, int feePercent) {
        this.feeAmount = feeAmount;
        this.feePercent = feePercent;
        return setFeeCommission();
    }

    @Override
    public String toString() {
        return "Card №" + cardFromNumber + " ==> " +
                amount + " + " + setFeeCommission() + ":" + feePercent + "%" +
                " ==> Card №" + cardToNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return getFeeAmount() == transfer.getFeeAmount() && getFeePercent() == transfer.getFeePercent() &&
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
                getCardToNumber(), getAmount(), getFeeAmount(), getFeePercent());
    }
}
