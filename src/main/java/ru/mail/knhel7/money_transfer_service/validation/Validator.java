package ru.mail.knhel7.money_transfer_service.validation;

import ru.mail.knhel7.money_transfer_service.exception.NotFoundEx;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.repository.TransactionsRepo;

public class Validator {

    private final TransactionsRepo transactionsRepo;

    public Validator(TransactionsRepo transactionsRepo) {
        this.transactionsRepo = transactionsRepo;
    }

    public Transaction<Transfer> validateTransferConfirm(TransferConfirm confirm) {
        String err = "Перевод №" + confirm.getOperationId() + " не подтвержден: не найден...";
        if (isValidTransferConfirm(confirm)) {
            throw new NotFoundEx(err);
        }
        try {
            int ID = Integer.parseInt(confirm.getOperationId());
            return (Transaction<Transfer>) transactionsRepo.getTransactionByID(ID).get();
        } catch (NumberFormatException NoSuchElementException) {
            throw new NotFoundEx(err);
        }
    }

    public static boolean isValidTransferConfirm(TransferConfirm confirm) {
        if (confirm == null) {
            return false;
        }
        if (confirm.getCode() == null || confirm.getCode().length() != 4) {
            return false;
        }
        return confirm.getOperationId() != null;
    }

    public void validateTransferMoney(Transfer transfer) {
        if (transfer.getAmount().value() <= 0) {
            throw new TransferException("Перевод отрицательной/нулевой суммы не предусмотрен!");
        }

    }
}
