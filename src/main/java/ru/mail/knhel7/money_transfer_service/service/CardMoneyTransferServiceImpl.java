package ru.mail.knhel7.money_transfer_service.service;

import org.springframework.stereotype.Service;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.repository.CardMoneyTransactionsRepoImpl;
import ru.mail.knhel7.money_transfer_service.repository.TransactionsRepo;
import ru.mail.knhel7.money_transfer_service.validation.Validator;

import java.util.List;

@Service
public class CardMoneyTransferServiceImpl implements CardMoneyTransferService {

    private final TransactionsRepo repo;
    private final Validator validator;

    public CardMoneyTransferServiceImpl(CardMoneyTransactionsRepoImpl repo) {
        this.repo = repo;
        this.validator = new Validator(repo);
    }

    @Override
    public Transaction<Transfer> transferConfirm(TransferConfirm confirm) {
        Transaction<Transfer> transaction = validator.validateTransferID(confirm);
        validator.validateAmount(transaction.getOperation());
        repo.executeTransfer(transaction);
        return transaction;
    }

    @Override
    public Transaction<Transfer> transferMoney(Transfer transfer) {
        validator.validateAmount(transfer);
        return repo.addTransferTransaction(transfer);
    }

    @Override
    public List<Transfer> getTransferList() {
        return getTransferTransactionList().stream().map(Transaction::getOperation).toList();
    }

    @Override
    public List<Transaction<Transfer>> getTransferTransactionList() {
        return repo.getTransactions().values().stream()
                .filter(transaction -> transaction.getOperation().getClass() == Transfer.class)
                .map(transaction -> (Transaction<Transfer>) transaction)
                .toList();
    }
}
