package ru.mail.knhel7.money_transfer_service.service;

import ru.mail.knhel7.money_transfer_service.model.Transfer;

import java.math.BigInteger;

public interface ICardMoneyTransferService {
    Integer transferMoney(Transfer transfer);
}
