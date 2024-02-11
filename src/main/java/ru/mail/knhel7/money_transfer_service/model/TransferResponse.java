package ru.mail.knhel7.money_transfer_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferResponse {

    private String operationId;

    public TransferResponse() {}

    public TransferResponse(String id) {
        operationId = id;
    }

    public TransferResponse(Integer id) {
        operationId = String.valueOf(id);
    }
}
