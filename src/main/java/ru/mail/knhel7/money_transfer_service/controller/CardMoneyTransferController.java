package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mail.knhel7.money_transfer_service.logger.Logger;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.http_response.TransferResponse;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.service.CardMoneyTransferServiceImpl;
import ru.mail.knhel7.money_transfer_service.service.CardMoneyTransferService;

import java.io.IOException;

@CrossOrigin(origins = "${client.url}")
@RestController
@RequestMapping("/")
public class CardMoneyTransferController {

    private final Logger logger = Logger.getLogger();
    private final CardMoneyTransferService service;

    public CardMoneyTransferController(CardMoneyTransferServiceImpl transferService) throws IOException {
        this.service = transferService;
    }

    @PostMapping("transfer")
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody @Validated Transfer transfer) {
        Transaction<Transfer> transaction = service.transferMoney(transfer);
        logger.logTransaction(transaction, "The transfer was received");
        return ResponseEntity.ok(new TransferResponse(transaction.getId()));
    }

//    @GetMapping("transfer")
//    public List<Transfer> getListTransfers() {
//        return service.getTransferList();
//    }

    @PostMapping("confirmOperation")
    public ResponseEntity<TransferResponse> transferConfirm(@RequestBody @Validated TransferConfirm confirm) {
        Transaction<Transfer> transaction = service.transferConfirm(confirm);
        logger.logTransactionTitle(transaction, "The transfer was confirmed");
        return ResponseEntity.ok(new TransferResponse(transaction.getId()));
    }
}
