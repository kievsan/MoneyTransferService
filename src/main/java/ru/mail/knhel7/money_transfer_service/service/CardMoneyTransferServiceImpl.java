package ru.mail.knhel7.money_transfer_service.service;

import org.springframework.stereotype.Service;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.repository.CardRepoImpl;
import ru.mail.knhel7.money_transfer_service.repository.CardRepo;
import ru.mail.knhel7.money_transfer_service.validation.Validator;

import java.util.List;

@Service
public class CardMoneyTransferServiceImpl implements CardMoneyTransferService {

    private final CardRepo cardRepo;
    private final Validator validator;

    public CardMoneyTransferServiceImpl(CardRepoImpl cardRepoImpl) {
        this.cardRepo = cardRepoImpl;
        this.validator = new Validator(cardRepo);
    }

    @Override
    public Transaction<Transfer> transferConfirm(TransferConfirm confirm) {
        Transaction<Transfer> transaction = validator.validateTransferID(confirm);
        Transfer transfer = transaction.getOperation();
        validator.validateAmount(transfer);
        cardRepo.executeTransfer(transaction);
        return transaction;
    }

    @Override
    public Transaction<Transfer> transferMoney(Transfer transfer) {
        validator.validateAmount(transfer);
        return cardRepo.addTransferTransaction(transfer);
    }

    @Override
    public List<Transfer> getTransferList() {
        return getTransferTransactionList().stream().map(Transaction::getOperation).toList();
    }

    @Override
    public List<Transaction<Transfer>> getTransferTransactionList() {
        return cardRepo.getTransactions().values().stream()
                .filter(transaction -> transaction.getOperation().getClass() == Transfer.class)
                .map(transaction -> (Transaction<Transfer>) transaction)
                .toList();
    }
}
