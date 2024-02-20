package ru.mail.knhel7.money_transfer_service.service;

import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import java.util.List;

public interface TransferService {
    Transaction<Transfer> transferMoney(Transfer transfer);
    Transaction<Transfer> transferConfirm(TransferConfirm confirm);
    public List<Transaction<Transfer>> getTransferTransactionList();
    public List<Transfer> getTransferList();
}
