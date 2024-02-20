package ru.mail.knhel7.money_transfer_service.util;

import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoggerAssistant {

    public String getLogTransactionTitle(int id, Transaction<?> transaction, String title) {
        return (title.isEmpty() ? title : title + ": №") + transaction.getId();
    }

    public String logTransaction(Integer id, Transaction<?> transaction, String title) {
        return getLogTransactionTitle(id, transaction, title) + " ==> " + transaction.getOperation();
    }
}
