package ru.mail.knhel7.money_transfer_service.model.transaction;

import ru.mail.knhel7.money_transfer_service.model.addit_code.TransactionStatus;

public interface ITransaction<T> {
    Integer getID();
    String getDate();
    String getTime();
    TransactionStatus getStatus();
    void setStatus(TransactionStatus status);
    String getComment();
    void setComment(String comment);
    T getOperation();
}
