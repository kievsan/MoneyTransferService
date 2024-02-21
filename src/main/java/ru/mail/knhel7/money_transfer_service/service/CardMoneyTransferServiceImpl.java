package ru.mail.knhel7.money_transfer_service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.http_response.TransferResponse;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.repository.CardMoneyTransactionsRepoImpl;
import ru.mail.knhel7.money_transfer_service.repository.TransactionsRepo;
import ru.mail.knhel7.money_transfer_service.validation.Validator;

import java.util.List;

@Service
public class CardMoneyTransferServiceImpl implements TransferService {

    private final TransactionsRepo repo;
    private final Validator validator;

    public CardMoneyTransferServiceImpl(CardMoneyTransactionsRepoImpl repo) {
        this.repo = repo;
        this.validator = new Validator(repo);
    }

    @Override
    public ResponseEntity<TransferResponse> transferConfirm(TransferConfirm confirm) {
        Transaction<Transfer> transaction = validator.validateTransferConfirm(confirm);
        validator.validateTransferMoney(transaction.getOperation());
        repo.executeTransfer(transaction);
        return ResponseEntity.ok(new TransferResponse(transaction.getId()));
    }

    @Override
    public ResponseEntity<TransferResponse> transferMoney(Transfer transfer) {
        validator.validateTransferMoney(transfer);
        Transaction<Transfer> transaction = repo.addTransferTransaction(transfer);
        return ResponseEntity.ok(new TransferResponse(transaction.getId()));
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
