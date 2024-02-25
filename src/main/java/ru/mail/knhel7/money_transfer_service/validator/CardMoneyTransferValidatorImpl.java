package ru.mail.knhel7.money_transfer_service.validator;

import org.springframework.stereotype.Service;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.TransferConfirm;

import java.time.LocalDateTime;

@Service
public class CardMoneyTransferValidatorImpl implements TransferValidator {

    @Override
    public void validateTransferConfirm(TransferConfirm confirm) {
        if (isBadTransferConfirm(confirm)) {
            throw new TransferException(confirm + " не подтвержден: неизвестный код (" + confirm.getCode() + ")...");
        }
    }

    @Override
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

    public static boolean isBadTransferConfirm(TransferConfirm confirm) {
        return confirm == null ||
                confirm.getCode() == null || confirm.getCode().length() != 4 ||
                confirm.getOperationId() == null;
    }
}
