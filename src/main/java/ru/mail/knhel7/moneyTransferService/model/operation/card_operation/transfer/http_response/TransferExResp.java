package ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransferExResp {

    private String message;
    private final Integer id = 0;

    public TransferExResp(String message) {
        this.message = "Transfer fail: " + message;
    }

    @Override
    public String toString() {
        return message + " (id=" + id + ")";
    }
}
