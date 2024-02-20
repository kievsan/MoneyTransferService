package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.http_response.TransferResponse;
import ru.mail.knhel7.money_transfer_service.service.TransferService;

@CrossOrigin(origins = "${client.url}")
@RestController
@RequestMapping("/")
public class CardMoneyTransferController {
    private final TransferService service;

    public CardMoneyTransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping("transfer")
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody @Validated Transfer transfer) {
        return service.transferMoney(transfer);
    }

    @PostMapping("confirmOperation")
    public ResponseEntity<TransferResponse> transferConfirm(@RequestBody @Validated TransferConfirm confirm) {
        return service.transferConfirm(confirm);
    }
}
