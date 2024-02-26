package ru.mail.knhel7.money_transfer_service.service;

import org.springframework.http.ResponseEntity;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_response.TransferResponse;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;

import java.util.List;

public interface TransferService {
    ResponseEntity<TransferResponse> transferMoney(Transfer transfer);
    ResponseEntity<TransferResponse> transferConfirm(TransferConfirm confirm);

    Transaction<Transfer> getTransferTransactionByID(Integer id);

    List<Transaction<Transfer>> getTransferTransactionList();
    List<Transfer> getTransferList();
}
