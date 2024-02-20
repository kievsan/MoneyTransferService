package ru.mail.knhel7.money_transfer_service.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.util.DateTimeUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CardMoneyTransactionsRepoImpl implements TransactionsRepo {

    @Getter
    private final static Map<Integer, Transaction<?>> transactions = new ConcurrentHashMap<>();
    private final static Map<String, String> msg = new ConcurrentHashMap<>();
    static {
        msg.put("Accept", "Подтверждена операция");
        msg.put("TransferFail", "Транзакция не завершена. Ошибка записи данных");
    }

    @Getter
    private final AtomicInteger id = new AtomicInteger(1);
    private final Integer ID = id.getAndIncrement();

    @Override
    public void executeTransfer(Transaction<Transfer> transaction) {
        transaction.getOperation().setCommission();
        transaction.setComment(msg.get("Accept") + " " + DateTimeUtil.timestamp());
        try {
            transactions.put(transaction.getId(), transaction);
        } catch (Exception ex) {
            throw new OtherTransferEx(msg.get("TransferFail"));
        }
    }

    @Override
    public Transaction<Transfer> addTransferTransaction(Transfer transfer) {
        Transaction<Transfer> transaction = new Transaction<>(transfer, ID);
        try{
            transactions.put(transaction.getId(), transaction);
        } catch (Exception ex) {
            throw new OtherTransferEx(msg.get("TransferFail"));
        }
        return transaction;
    }

    @Override
    public Optional<Transaction<?>> getTransactionByID(int id) {
        return Optional.ofNullable(transactions.get(id));
    }
}
