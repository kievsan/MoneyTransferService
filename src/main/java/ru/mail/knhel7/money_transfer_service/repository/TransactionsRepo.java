package ru.mail.knhel7.money_transfer_service.repository;

import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import java.util.Map;
import java.util.Optional;

public interface TransactionsRepo {
    void executeTransfer(Transaction<Transfer> transaction);
    Transaction<Transfer> addTransferTransaction(Transfer transfer);
    Optional<Transaction<?>> getTransactionByID(int id);
    Map<Integer, Transaction<?>> getTransactions();
}
