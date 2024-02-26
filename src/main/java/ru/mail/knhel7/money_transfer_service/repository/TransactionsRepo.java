package ru.mail.knhel7.money_transfer_service.repository;

import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import java.util.Map;
import java.util.Optional;

public interface TransactionsRepo {
    Transaction<Transfer>  executeTransfer(Transaction<Transfer> transaction);
    Transaction<Transfer> confirmTransfer(TransferConfirm confirm);
    Transaction<Transfer> addTransferTransaction(Transfer transfer);
    Optional<Transaction<?>> getTransactionByID(int id);
    Map<Integer, Transaction<?>> getTransactions();
    void clearTransactions();
}
