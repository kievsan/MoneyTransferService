package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mail.knhel7.money_transfer_service.model.Transfer;
import ru.mail.knhel7.money_transfer_service.model.response.TransferExResp;
import ru.mail.knhel7.money_transfer_service.model.response.TransferResponse;
import ru.mail.knhel7.money_transfer_service.service.CardMoneyTransferService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @PostMapping(value = "transfer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> transferMoney(@RequestBody @Validated Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)));

        HttpStatus status;
        Object response;

        System.out.println(transfer);
        Integer operationId = service.transferMoney(transfer);

        if (operationId != 0) {
            status = HttpStatus.OK;
            response = new TransferResponse(operationId);
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response = new TransferExResp("Transfer: fail");
        }

        return new ResponseEntity<>(response, headers, status);
    }

    @GetMapping("transfer")
    public List<Transfer> getAllTransfers() {
        return service.getAllTransfers();
    }
}
