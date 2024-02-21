package ru.mail.knhel7.money_transfer_service.model.transfer.http_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferResponse {

    private String operationId;

    public TransferResponse(Integer id) {
        operationId = String.valueOf(id);
    }
}
