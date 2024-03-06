package ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferResponse {

    private String operationId;

    public TransferResponse(Integer id) {
        operationId = String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferResponse that = (TransferResponse) o;
        return Objects.equals(getOperationId(), that.getOperationId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperationId());
    }
}
