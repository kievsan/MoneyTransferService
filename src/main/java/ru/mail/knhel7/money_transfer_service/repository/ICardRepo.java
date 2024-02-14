package ru.mail.knhel7.money_transfer_service.repository;

import ru.mail.knhel7.money_transfer_service.model.*;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICardRepo {
    Card putMoney(String cardNumber, Money money);
    Card spendMoney(Transfer transfer);
    Optional<Card> getCardByNumber(String number);
    List<Card> getAllCards();
    List<String> getAllCardNumbers();
    Map<Integer, Transaction<?>> getTransactions();
    List<Transaction<?>> getTransactionList();
    Optional<Transaction<?>> getTransactionByID(int id);
    Transaction<Transfer> addTransferTransaction(Transfer transfer);
    void executeTransfer(Transaction<Transfer> transaction, Card sender, Card receiver);
}
