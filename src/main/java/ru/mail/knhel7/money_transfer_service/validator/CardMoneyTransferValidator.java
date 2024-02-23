package ru.mail.knhel7.money_transfer_service.validator;

import ru.mail.knhel7.money_transfer_service.exception.NotFoundEx;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.transfer.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.transfer.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.repository.TransactionsRepo;
import ru.mail.knhel7.money_transfer_service.util.DateTimeUtil;

import java.time.LocalDateTime;

public class CardMoneyTransferValidator {

    private final TransactionsRepo transactionsRepo;

    public CardMoneyTransferValidator(TransactionsRepo transactionsRepo) {
        this.transactionsRepo = transactionsRepo;
    }

    public Transaction<Transfer> validateTransferConfirm(TransferConfirm confirm) {
        String title = "Перевод №" + confirm.getOperationId();
        if (isBadTransferConfirm(confirm)) {
            throw new TransferException(title + " не подтвержден: неизвестный код (" + confirm.getCode() + ")...");
        }
        try {
            int ID = Integer.parseInt(confirm.getOperationId());
            Transaction<Transfer> transaction = (Transaction<Transfer>) transactionsRepo.getTransactionByID(ID).get();
            transaction.setComment(title + " подтвержден " + DateTimeUtil.timestamp());
            transaction.setCode(confirm.getCode());
            return transaction;
        } catch (NumberFormatException NoSuchElementException) {
            throw new NotFoundEx(title + " не подтвержден: не найден...");
        }
    }

    public static boolean isBadTransferConfirm(TransferConfirm confirm) {
        return confirm == null ||
                confirm.getCode() == null || confirm.getCode().length() != 4 ||
                confirm.getOperationId() == null;
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
            throw new TransferException("Ошибка в коде CVV карты отправителя: ожидается 3 цифры!");
        }
        if (isBadDate(transfer.getCardFromValidTill())) {
            throw new TransferException("Некорректная дата! Ожидается в формате 'mm/YY'");
        }
        if (isOverdueDate(transfer.getCardFromValidTill())) {
            throw new TransferException("Карта отправителя просрочена!");
        }
    }

    private boolean isOverdueDate(String d) {
        LocalDateTime now = LocalDateTime.now();
        return 2000 + convert(d.substring(3, 5)) < now.getYear() ||
                2000 + convert(d.substring(3, 5)) == now.getYear() && convert(d) < now.getMonthValue();
    }

    private boolean isBadDate(String d) {
        return d.length() != 5 || d.indexOf("/") != 2 ||
                convert(d) < 1 || convert(d) > 12 ||
                convert(d.substring(3, 5)) < 0;
    }

    private Integer convert(String num2Char) {
        try {
            return Integer.parseInt(num2Char.trim().substring(0, 2));
        } catch (Exception ex) {
            throw new TransferException("Ошибка в сроке действия карты отправителя: месяц или год - не числа!");
        }
    }
}
