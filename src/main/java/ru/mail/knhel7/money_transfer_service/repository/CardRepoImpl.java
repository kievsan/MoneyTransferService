package ru.mail.knhel7.money_transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardRepoImpl implements CardRepo {

    private final static Map<Integer, Transaction<?>> transactions = new ConcurrentHashMap<>();


    public void executeTransfer(Transaction<Transfer> transaction) {
        transaction.getOperation().setCommission();
        try {
            transactions.put(transaction.getID(), transaction);
        } catch (Exception ex) {
            throw new OtherTransferEx("Ошибка записи данных");
        }
    }

    public Transaction<Transfer> addTransferTransaction(Transfer transfer) {
        Transaction<Transfer> transaction = new Transaction<>(transfer);
        try{
            transactions.put(transaction.getID(), transaction);
        } catch (Exception ex) {
            throw new OtherTransferEx("Ошибка записи данных");
        }
        return transaction;
    }

    @Override
    public Optional<Transaction<?>> getTransactionByID(int id) {
        return Optional.ofNullable(transactions.get(id));
    }

    public Map<Integer, Transaction<?>> getTransactions() {
        return transactions;
    }
}
