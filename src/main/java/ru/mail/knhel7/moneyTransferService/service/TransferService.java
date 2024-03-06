package ru.mail.knhel7.moneyTransferService.service;

import org.springframework.http.ResponseEntity;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_response.TransferResponse;
import ru.mail.knhel7.moneyTransferService.model.transaction.Transaction;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.TransferConfirm;

import java.util.List;

public interface TransferService {
    ResponseEntity<TransferResponse> transferMoney(Transfer transfer);
    ResponseEntity<TransferResponse> transferConfirm(TransferConfirm confirm);

    Transaction<Transfer> getTransferTransactionByID(Integer id);

    List<Transaction<Transfer>> getTransferTransactionList();
    List<Transfer> getTransferList();
}
