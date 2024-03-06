package ru.mail.knhel7.moneyTransferService.validator;

import org.springframework.stereotype.Service;
import ru.mail.knhel7.moneyTransferService.exception.TransferException;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.TransferConfirm;

import java.time.LocalDateTime;

@Service
public class CardMoneyTransferValidatorImpl implements TransferValidator {

    private final static int YEAR2000 = 2000;
    private final static int minMONTH = 1;
    private final static int maxMONTH = 12;

    @Override
    public void validateTransferConfirm(TransferConfirm confirm) {
        if (isBadTransferConfirm(confirm)) {
            throw new TransferException(confirm + " не подтвержден: неизвестный код (" + confirm.getCode() + ")...");
        }
    }

    public static boolean isBadTransferConfirm(TransferConfirm confirm) {
        return confirm == null || confirm.getCode().length() != 4 || confirm.getOperationId().isEmpty();
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
        return YEAR2000 + convert(d.substring(3, 5)) < now.getYear() ||
                YEAR2000 + convert(d.substring(3, 5)) == now.getYear() && convert(d) < now.getMonthValue();
    }

    private boolean isBadDate(String d) {
        return d.length() != 5 || d.indexOf("/") != 2 ||
                convert(d) < minMONTH || convert(d) > maxMONTH ||
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
