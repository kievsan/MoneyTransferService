package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mail.knhel7.money_transfer_service.model.Transfer;
import ru.mail.knhel7.money_transfer_service.model.TransferResponse;
import ru.mail.knhel7.money_transfer_service.service.CardMoneyTransferService;

import java.util.List;


@RestController
@RequestMapping("/")
public class CardMoneyTransferController {

    private final CardMoneyTransferService service;

    public CardMoneyTransferController(CardMoneyTransferService transferService) {
        this.service = transferService;
    }

    @PostMapping("transfer")
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody @Validated Transfer transfer) {
        System.out.println(transfer);
        return ResponseEntity.ok(new TransferResponse(service.transferMoney(transfer)));
    }

    @GetMapping("transfer")
    public List<Transfer> getAllTransfers() {
        return service.getAllTransfers();
    }
}
