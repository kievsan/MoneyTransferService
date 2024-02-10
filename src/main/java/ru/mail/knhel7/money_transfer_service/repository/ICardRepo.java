package ru.mail.knhel7.money_transfer_service.repository;

import ru.mail.knhel7.money_transfer_service.model.*;

import java.util.List;
import java.util.Optional;

public interface ICardRepo {
    public Card putMoney(String cardNumber, Money money);
    public Card spendMoney(String cardNumber, Money money);
    public Optional<Card> getCardByNumber(String number);
    public List<Card> getAllCards();
    public List<String> getAllCardNumbers();
    public List<Transfer> getAllTransfers();
}
