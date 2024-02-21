package ru.mail.knhel7.money_transfer_service.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.model.transfer.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.util.DateTimeUtil;
import ru.mail.knhel7.money_transfer_service.util.LoggerAssistant;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Repository
public class CardMoneyTransactionsRepoImpl implements TransactionsRepo {

    private final static Map<Integer, Transaction<?>> transactions = new ConcurrentHashMap<>();
    private final static Map<String, String> msg = new ConcurrentHashMap<>();
    static {
        msg.put("Accept", "Подтверждена операция");
        msg.put("TransferFail", "Транзакция не завершена. Ошибка записи данных");
    }

    private final AtomicInteger id = new AtomicInteger(1);
    private final AtomicInteger logHttpId = new AtomicInteger(1);

    @Value("${server.port}")
    private String port;

    final LoggerAssistant logger = new LoggerAssistant();

    private Integer nextID() {
        return id.getAndIncrement();
    }

    private Integer nextLogHttpID() {
        return logHttpId.getAndIncrement();
    }

    @Override
    public void executeTransfer(Transaction<Transfer> transaction) {
        transaction.getOperation().setCommission();
        transaction.setComment(msg.get("Accept") + " " + DateTimeUtil.timestamp());
        try {
            transactions.put(transaction.getId(), transaction);
            log.info("[{}: HTTP {}] {}", port, nextLogHttpID(),
                    logger.logTransaction(transaction, "The transfer was confirmed"));
        } catch (Exception ex) {
            throw new OtherTransferEx("HTTP " + port + ": " + msg.get("TransferFail"));
        }
    }

    @Override
    public Transaction<Transfer> addTransferTransaction(Transfer transfer) {
        Transaction<Transfer> transaction = new Transaction<>(transfer, nextID());
        try{
            transactions.put(transaction.getId(), transaction);
            log.info("[{}: HTTP {}] {}", port, nextLogHttpID(),
                    logger.logTransaction(transaction, "The transfer was received"));
        } catch (Exception ex) {
            throw new OtherTransferEx(msg.get("TransferFail"));
        }
        return transaction;
    }

    @Override
    public Optional<Transaction<?>> getTransactionByID(int id) {
        return Optional.ofNullable(transactions.get(id));
    }

    @Override
    public Map<Integer, Transaction<?>> getTransactions() {
        return transactions;
    }
}
