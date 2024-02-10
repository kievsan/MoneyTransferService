package ru.mail.knhel7.money_transfer_service.service;

import ru.mail.knhel7.money_transfer_service.model.Card;
import ru.mail.knhel7.money_transfer_service.model.Transfer;

import java.math.BigInteger;
import java.util.List;

public interface ICardMoneyTransferService {
    Integer transferMoney(Transfer transfer);
    public List<Transfer> getAllTransfers();
    public List<Card> getAllCards();
    public List<String> getAllCardNumbers();
}
