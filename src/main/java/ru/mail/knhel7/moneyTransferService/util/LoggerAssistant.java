package ru.mail.knhel7.moneyTransferService.util;

import ru.mail.knhel7.moneyTransferService.model.transaction.Transaction;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoggerAssistant {

    public String getLogTransactionTitle(Transaction<?> transaction, String title) {
        return (title.isEmpty() ? title : title + ": №") + transaction.getId();
    }

    public String logTransaction(Transaction<?> transaction, String title) {
        return getLogTransactionTitle(transaction, title) + " ==> " + transaction.getOperation();
    }
}
