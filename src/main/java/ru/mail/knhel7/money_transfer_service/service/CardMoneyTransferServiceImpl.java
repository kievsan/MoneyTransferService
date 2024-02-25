package ru.mail.knhel7.money_transfer_service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_response.TransferResponse;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.repository.TransactionsRepo;
import ru.mail.knhel7.money_transfer_service.validator.CardMoneyTransferValidatorImpl;
import ru.mail.knhel7.money_transfer_service.validator.TransferValidator;

import java.util.List;

@Service
public class CardMoneyTransferServiceImpl implements TransferService {

    private final TransactionsRepo repo;
    private final TransferValidator validator = new CardMoneyTransferValidatorImpl();

    public CardMoneyTransferServiceImpl(TransactionsRepo repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<TransferResponse> transferConfirm(TransferConfirm confirm) {
        validator.validateTransferConfirm(confirm);
        Transaction<Transfer> transaction = repo.executeTransfer(repo.confirmTransfer(confirm));
        return ResponseEntity.ok(new TransferResponse(transaction.getId()));
    }

    @Override
    public ResponseEntity<TransferResponse> transferMoney(Transfer transfer) {
        validator.validateTransferMoney(transfer);
        Transaction<Transfer> transaction = repo.addTransferTransaction(transfer);
        return ResponseEntity.ok(new TransferResponse(transaction.getId()));
    }

    @Override
    public Transaction<Transfer> getTransferTransactionByID(Integer id) {
        Transaction<?> transaction = repo.getTransactionByID(id).orElse(null);
        return transaction == null
                ? null
                : Transfer.class == transaction.getOperation().getClass() ? (Transaction<Transfer>) transaction : null;
    }

    @Override
    public List<Transaction<Transfer>> getTransferTransactionList() {
        return repo.getTransactions().values().stream()
                .filter(transaction -> transaction.getOperation().getClass() == Transfer.class)
                .map(transaction -> (Transaction<Transfer>) transaction)
                .toList();
    }

    @Override
    public List<Transfer> getTransferList() {
        return getTransferTransactionList().stream().map(Transaction::getOperation).toList();
    }
}
