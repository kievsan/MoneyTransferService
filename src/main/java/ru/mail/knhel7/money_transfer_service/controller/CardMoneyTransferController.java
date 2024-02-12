package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mail.knhel7.money_transfer_service.model.Transfer;
import ru.mail.knhel7.money_transfer_service.model.response.TransferExResp;
import ru.mail.knhel7.money_transfer_service.model.response.TransferResponse;
import ru.mail.knhel7.money_transfer_service.service.CardMoneyTransferService;

import java.util.List;


@CrossOrigin(origins = "${client.url}")
@RestController
@RequestMapping(value = "/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class CardMoneyTransferController {

    private final CardMoneyTransferService service;

    public CardMoneyTransferController(CardMoneyTransferService transferService) {
        this.service = transferService;
    }

    @PostMapping("transfer")
    public ResponseEntity<?> transferMoney(@RequestBody @Validated Transfer transfer) {
        System.out.println(transfer);
        Integer operationId = service.transferMoney(transfer);
        return operationId != 0
                ? ResponseEntity.ok(new TransferResponse(operationId))
                : new ResponseEntity<>(new TransferExResp("Transfer: fail"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("transfer")
    public List<Transfer> getAllTransfers() {
        return service.getAllTransfers();
    }
}
