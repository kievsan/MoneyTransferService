package ru.mail.knhel7.money_transfer_service.validation;

import ru.mail.knhel7.money_transfer_service.exception.NotFoundEx;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.repository.CardRepo;

public class Validator {

    private final CardRepo cardRepo;

    public Validator(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    public Transaction<Transfer> validateTransferID(TransferConfirm confirm) {
        String err = "Перевод №" + confirm.getOperationId() + " не подтвержден: не найден...";
        try {
            int ID = Integer.parseInt(confirm.getOperationId());
            return (Transaction<Transfer>) cardRepo.getTransactionByID(ID).get();
        } catch (NumberFormatException NoSuchElementException) {
            throw new NotFoundEx(err);
        }
    }

    public void validateAmount(Transfer transfer) {
        if (transfer.getAmount().value() <= 0) {
            throw new TransferException("Перевод отрицательной/нулевой суммы не предусмотрен!");
        }
    }

}
