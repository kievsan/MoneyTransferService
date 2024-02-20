package ru.mail.knhel7.money_transfer_service.model.transaction;

import lombok.Getter;
import lombok.Setter;
import ru.mail.knhel7.money_transfer_service.util.DateTimeUtil;

import java.util.concurrent.atomic.AtomicInteger;


@Getter
public class Transaction<T> {

    private final String date = DateTimeUtil.getDate();
    private final String time = DateTimeUtil.getTime();
    private final Integer id;
    private final T operation;
    @Setter
    private  String comment = "Зарегистрирована новая операция " + DateTimeUtil.timestamp();

    public Transaction(T operation, Integer id) {
        this.id = id;
        this.operation = operation;
    }
}
