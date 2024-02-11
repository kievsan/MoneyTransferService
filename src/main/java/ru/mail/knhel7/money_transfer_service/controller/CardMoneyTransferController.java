package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public Integer transferMoney(@RequestBody @Validated Transfer transfer) {
        System.out.println(transfer);
        return transferService.transferMoney(transfer);
    }

    @GetMapping("transfer")
    public List<Transfer> getAllTransfers() {
        return transferService.getAllTransfers();
    }
}
