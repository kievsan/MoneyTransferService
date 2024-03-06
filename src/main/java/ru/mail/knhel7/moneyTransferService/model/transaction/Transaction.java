package ru.mail.knhel7.moneyTransferService.model.transaction;

import lombok.Getter;
import lombok.Setter;
import ru.mail.knhel7.moneyTransferService.util.DateTimeUtil;

import java.util.Objects;

@Getter
public class Transaction<T> {

    private final String date = DateTimeUtil.getDate();
    private final String time = DateTimeUtil.getTime();
    private final Integer id;
    private final T operation;
    @Setter
    private  String comment = "Зарегистрирована новая операция " + DateTimeUtil.timestamp();
    @Setter
    private String code = null;

    public Transaction(T operation, Integer id) {
        this.id = id;
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction<?> that = (Transaction<?>) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getOperation(), that.getOperation()) &&
                Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOperation(), getCode());
    }
}
