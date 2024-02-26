package ru.mail.knhel7.money_transfer_service.validator;

import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.TransferConfirm;

public interface TransferValidator {

    void validateTransferConfirm(TransferConfirm confirm);
    void validateTransferMoney(Transfer transfer);

}
