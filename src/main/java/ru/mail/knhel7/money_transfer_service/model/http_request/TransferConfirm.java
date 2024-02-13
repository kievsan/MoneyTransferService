package ru.mail.knhel7.money_transfer_service.model.http_request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransferConfirm {

    @NotBlank String operationId;
    @NotBlank String code;

    public TransferConfirm() {}


    @Override
    public String toString() {
        return "The transfer №" + operationId + " has been confirmed!";
    }
}
