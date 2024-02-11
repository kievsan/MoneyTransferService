package ru.mail.knhel7.money_transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.mail.knhel7.money_transfer_service.model.*;
import ru.mail.knhel7.money_transfer_service.model.Currency;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardRepo implements ICardRepo{

    private final static HashMap<String, Card> cards = new HashMap<>();
    static {
        cards.put("1234567890123456",
                new Card("1234567890123456", "231", "02/34", Currency.RUR, 167000));
        cards.put("2345678901234561",
                new Card("2345678901234561", "523", "01/25", Currency.RUR, 20000));
        cards.put("3456789012345612",
                new Card("3456789012345612", "777", "11/24", Currency.RUR, 33300));
    }

    private final static HashMap<Integer, Transfer> transfers = new HashMap<>();
    private static Integer transferID = 1;

    public Integer executeTransfer(Transfer transfer, Card sender, Card receiver) {
        try {
            cards.put(sender.getNumber(), sender);
            cards.put(receiver.getNumber(), receiver);
        } catch (Exception ex) {
            return 0;
        }
        transfers.put(transferID, transfer);
        return transferID++;
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return transfers.values().stream().toList();
    }

    @Override
    public Card putMoney(String cardNumber, Money money) {
        // может выбросить ошибку - NoSuchElementException (extends RuntimeException):
        Card card = getCardByNumber(cardNumber).get();

        if (card.getCurrency() != money.currency()) {
            throw new RuntimeException("не задана или неподходящая валюта на карте " + cardNumber);
        }
        card.setAmount(card.getAmount() + money.value());
//        cards.put(card.getNumber(), card);
        return card;
    }

    @Override
    public Card spendMoney(String cardNumber, Money money) {
        // может выбросить ошибку - NoSuchElementException (extends RuntimeException):
        Card card = getCardByNumber(cardNumber).get();

        if (card.getCurrency() != money.currency()) {
            throw new RuntimeException("не задана или неподходящая валюта на карте " + cardNumber);
        }

        Integer requiredSum = money.value() + money.getPercentage(1).value();

        if (card.getAmount() < requiredSum) {
            throw new RuntimeException("недостаточно средств на карте " + cardNumber);
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
