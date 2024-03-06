package ru.mail.knhel7.moneyTransferService.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.mail.knhel7.moneyTransferService.exception.NotFoundException;
import ru.mail.knhel7.moneyTransferService.exception.OtherTransferException;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.TransferConfirm;
import ru.mail.knhel7.moneyTransferService.model.transaction.Transaction;
import ru.mail.knhel7.moneyTransferService.util.DateTimeUtil;
import ru.mail.knhel7.moneyTransferService.util.LoggerAssistant;

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
        msg.put("Accept", "Перевод подтвержден");
        msg.put("TransferFail", "Транзакция не завершена. Ошибка записи данных");
    }

    private final AtomicInteger id = new AtomicInteger(1);
    private final AtomicInteger logHttpId = new AtomicInteger(1);
    final LoggerAssistant logger = new LoggerAssistant();

    @Value("${server.port}")
    private String port;

    public CardMoneyTransactionsRepoImpl() {
    }

    private Integer nextID() {
        return id.getAndIncrement();
    }

    private Integer nextLogHttpID() {
        return logHttpId.getAndIncrement();
    }

    @Override
    public Transaction<Transfer> executeTransfer(Transaction<Transfer> transaction) {
        transaction.getOperation().setFeeCommission();
        try {
            transactions.put(transaction.getId(), transaction);
            log.info("[{}: HTTP {}] {}", port, nextLogHttpID(),
                    logger.logTransaction(transaction, "The transfer was confirmed"));
        } catch (RuntimeException ex) {
            throw new OtherTransferException("HTTP " + port + ": " + msg.get("TransferFail"));
        }
        return transaction;
    }

    @Override
    public Transaction<Transfer> confirmTransfer(TransferConfirm confirm) {
        try {
            int ID = Integer.parseInt(confirm.getOperationId());
            Transaction<?> transaction = getTransactionByID(ID).orElseThrow();
            if (Transfer.class != transaction.getOperation().getClass()) {
                throw new ClassNotFoundException();
            }
            transaction.setCode(confirm.getCode());
            transaction.setComment(msg.get("Accept") + " " + DateTimeUtil.timestamp());
            return (Transaction<Transfer>) transaction;
//        } catch (NumberFormatException | NoSuchElementException | ClassNotFoundException ex) {
        } catch (RuntimeException | ClassNotFoundException ex) {
            throw new NotFoundException(confirm + " не подтвержден: не найден...");
        }
    }

    @Override
    public Transaction<Transfer> addTransferTransaction(Transfer transfer) {
        Transaction<Transfer> transaction = new Transaction<>(transfer, nextID());
        try{
            transactions.put(transaction.getId(), transaction);
            log.info("[{}: HTTP {}] {}", port, nextLogHttpID(),
                    logger.logTransaction(transaction, "The transfer was received"));
        } catch (RuntimeException ex) {
            throw new OtherTransferException(msg.get("TransferFail"));
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

    @Override
    public void clearTransactions() {
        for (int id: transactions.keySet().stream().toList()) {
            transactions.remove(id);
        }
    }
}
