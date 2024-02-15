package ru.mail.knhel7.money_transfer_service.model.transaction;

import lombok.Getter;
import lombok.Setter;
import ru.mail.knhel7.money_transfer_service.model.addit_code.TransactionStatus;
import ru.mail.knhel7.money_transfer_service.tools.DateTimeTool;


@Getter
public class Transaction<T> implements ITransaction<T>{

    private static Integer Counter = 1;

    private final Integer ID = Counter++;
    private final String date = DateTimeTool.getDate();
    private final String time = DateTimeTool.getTime();

    @Setter
    private TransactionStatus status = TransactionStatus.NEW;
    @Setter
    private  String comment = "Зарегистрирована новая операция" + date + " " + time;

    private final T operation;

    public Transaction(T operation) {
        this.operation = operation;
    }
}
