package ru.mail.knhel7.moneyTransferService.validator;

import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.TransferConfirm;

public interface TransferValidator {

    void validateTransferConfirm(TransferConfirm confirm);
    void validateTransferMoney(Transfer transfer);

}
