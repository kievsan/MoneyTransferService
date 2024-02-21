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
        String err = "Перевод №" + confirm.getOperationId() + " не подтвержден";
        if (isValidTransferConfirm(confirm)) {
            throw new NotFoundEx(err);
        }
        try {
            int ID = Integer.parseInt(confirm.getOperationId());
            return (Transaction<Transfer>) transactionsRepo.getTransactionByID(ID).get();
        } catch (NumberFormatException NoSuchElementException) {
            throw new NotFoundEx(err + ": не найден...");
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
        if (transfer == null) {
            throw new TransferException("Пустой перевод не предусмотрен!");
        }
        if (transfer.getAmount().value() <= 0) {
            throw new TransferException("Перевод отрицательной/нулевой суммы не предусмотрен!");
        }
        if (transfer.getCardFromNumber() == null || transfer.getCardFromNumber().length() != 16) {
            throw new TransferException("Ошибка в номере карты отправителя!");
        }
        if (transfer.getCardToNumber() == null || transfer.getCardToNumber().length() != 16) {
            throw new TransferException("Ошибка в номере карты получателя!");
        }
        if (transfer.getCardFromCVV() == null || transfer.getCardFromCVV().length() != 3) {
            throw new TransferException("Ошибка в коде CVV карты отправителя: должно быть 3 цифры!");
        }
        if (transfer.getCardFromValidTill() == null || transfer.getCardFromValidTill().length() != 5) {
            throw new TransferException("Ошибка в дате: должно быть 5 знаков!");
        }
        if (isOverdueDate(transfer.getCardFromValidTill())) {
            throw new TransferException("Карта отправителя просрочена!");
        }
    }

    private boolean isOverdueDate(String d) {
        return false;
    }
}
