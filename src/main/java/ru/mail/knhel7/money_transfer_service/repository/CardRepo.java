package ru.mail.knhel7.money_transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.*;
import ru.mail.knhel7.money_transfer_service.model.addit_code.Currency;
import ru.mail.knhel7.money_transfer_service.model.addit_code.TransactionStatus;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardRepo implements ICardRepo{

    private final static Map<String, Card> cards = new ConcurrentHashMap<>();
    static {
        cards.put("1234567890123456",
                new Card("1234567890123456", "231", "02/34", Currency.RUR, 167000));
        cards.put("2345678901234561",
                new Card("2345678901234561", "523", "01/25", Currency.RUR, 20000));
        cards.put("3456789012345612",
                new Card("3456789012345612", "777", "11/24", Currency.RUR, 33300));
    }

    private final static Map<Integer, Transaction<?>> transactions = new ConcurrentHashMap<>();


    public void executeTransfer(Transaction<Transfer> transaction, Card sender, Card receiver) {
        try {
            cards.put(sender.getNumber(), sender);
            cards.put(receiver.getNumber(), receiver);
            transaction.setStatus(TransactionStatus.EXECUTED);
            transactions.put(transaction.getID(), transaction);
        } catch (Exception ex) {
            throw new OtherTransferEx("Ошибка сервера: возможно нарушена целостность данных");
        }
    }

    public Transaction<Transfer> addTransferTransaction(Transfer transfer) {
        Transaction<Transfer> transaction = new Transaction<>(transfer);
        try{
            transactions.put(transaction.getID(), transaction);
        } catch (Exception ex) {
            throw new OtherTransferEx("Ошибка сервера: возможно нарушена целостность данных");
        }
        return transaction;
    }

    public Transaction<?> delTransactionById(Integer id) {
        return transactions.remove(id);
    }

    @Override
    public Optional<Transaction<?>> getTransactionByID(int id) {
        return Optional.ofNullable(transactions.get(id));
    }

    public Map<Integer, Transaction<?>> getTransactions() {
        return transactions;
    }

    @Override
    public List<Transaction<?>> getTransactionList() {
        return transactions.values().stream().toList();
    }

    @Override
    public Card putMoney(String cardNumber, Money money) {
        // может выбросить ошибку - NoSuchElementException (extends RuntimeException):
         Card card = getCardByNumber(cardNumber).get();

        if (card.getCurrency() != money.currency()) {
            throw new TransferException("не задана или неподходящая валюта на карте №" + cardNumber);
        }
        card.setAmount(card.getAmount() + money.value());
//        cards.put(card.getNumber(), card);
        return card;
    }

    @Override
    public Card spendMoney(Transfer transfer) {
        String cardNumber = transfer.getCardFromNumber();
        Money money = transfer.getAmount();

        // может выбросить ошибку - NoSuchElementException (extends RuntimeException):
         Card card = getCardByNumber(cardNumber).get();

        if (card.getCurrency() != money.currency()) {
            throw new TransferException("не задана или неподходящая валюта на карте №" + cardNumber);
        }

        Integer requiredSum = money.value() + transfer.setCommission().value();

        if (card.getAmount() < requiredSum) {
            throw new TransferException("недостаточно средств (" + card.getAmount()/100 + " " + card.getCurrency() +
                    ") на карте №" + cardNumber);
        }

        card.setAmount(card.getAmount() - requiredSum);
//        cards.put(card.getNumber(), card);
        return card;
    }

    @Override
    public Optional<Card> getCardByNumber(String number) {
        return Optional.ofNullable(cards.get(number));
    }

    @Override
    public List<Card> getAllCards() {
        return cards.values().stream().toList();
    }

    @Override
    public List<String> getAllCardNumbers() {
        return cards.keySet().stream().sorted().toList();
    }
}
