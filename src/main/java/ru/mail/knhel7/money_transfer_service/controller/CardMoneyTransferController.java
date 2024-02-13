package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.http_response.TransferResponse;
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
        return ResponseEntity.ok(new TransferResponse(operationId));
    }

    @GetMapping("transfer")
    public List<Transfer> getListTransfers() {
        return service.getTransferList();
    }

    @PostMapping("confirmOperation")
    public ResponseEntity<?> transferConfirm(@RequestBody @Validated TransferConfirm confirm) {
        System.out.println("Confirmed transfer №" + confirm.getOperationId());
        Integer operationId = service.transferConfirm(confirm);
        return ResponseEntity.ok(new TransferResponse(operationId));
    }
}
