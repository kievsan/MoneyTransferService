package ru.mail.knhel7.money_transfer_service.model.http_request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferConfirm {

    @NotBlank String operationId;
    @NotBlank String code;

    @Override
    public String toString() {
        return "The transfer №" + operationId + " has been confirmed!";
    }
}
