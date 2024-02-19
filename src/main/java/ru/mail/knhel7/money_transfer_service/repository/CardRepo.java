package ru.mail.knhel7.money_transfer_service.repository;

import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import java.util.Map;
import java.util.Optional;

public interface CardRepo {
    void executeTransfer(Transaction<Transfer> transaction);
    Transaction<Transfer> addTransferTransaction(Transfer transfer);
    Map<Integer, Transaction<?>> getTransactions();
    Optional<Transaction<?>> getTransactionByID(int id);
}
