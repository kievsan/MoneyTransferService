package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.knhel7.money_transfer_service.model.Card;
import ru.mail.knhel7.money_transfer_service.model.Transfer;
import ru.mail.knhel7.money_transfer_service.service.CardMoneyTransferService;

import java.util.List;

@RestController
@RequestMapping("/")
public class CardMoneyTransferController {

    private final CardMoneyTransferService transferService;

    public CardMoneyTransferController(CardMoneyTransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("transfer")
    public void transferMoney(@Validated Transfer transfer) {
        Integer operationID = transferService.transferMoney(transfer);
    }

    @GetMapping("transfer")
    public List<Transfer> getAllTransfers() {
        return transferService.getAllTransfers();
    }
}
