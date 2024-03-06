package ru.mail.knhel7.moneyTransferService.repository;

import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.TransferConfirm;
import ru.mail.knhel7.moneyTransferService.model.transaction.Transaction;

import java.util.Map;
import java.util.Optional;

public interface TransactionsRepo {
    Transaction<Transfer> executeTransfer(Transaction<Transfer> transaction);
    Transaction<Transfer> confirmTransfer(TransferConfirm confirm);
    Transaction<Transfer> addTransferTransaction(Transfer transfer);
    Optional<Transaction<?>> getTransactionByID(int id);
    Map<Integer, Transaction<?>> getTransactions();
    void clearTransactions();
}
