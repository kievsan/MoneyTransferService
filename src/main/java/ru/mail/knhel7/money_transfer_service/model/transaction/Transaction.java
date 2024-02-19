package ru.mail.knhel7.money_transfer_service.model.transaction;

import lombok.Getter;
import lombok.Setter;
import ru.mail.knhel7.money_transfer_service.util.DateTimeUtil;

import java.util.concurrent.atomic.AtomicInteger;


@Getter
public class Transaction<T> {

    private static final AtomicInteger Counter = new AtomicInteger(1);

    private final Integer ID = Counter.getAndIncrement();
    private final String date = DateTimeUtil.getDate();
    private final String time = DateTimeUtil.getTime();

    @Setter
    private  String comment = "Зарегистрирована новая операция" + date + " " + time;

    private final T operation;

    public Transaction(T operation) {
        this.operation = operation;
    }
}
