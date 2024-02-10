package ru.mail.knhel7.money_transfer_service.service;

import org.springframework.stereotype.Service;
import ru.mail.knhel7.money_transfer_service.model.Card;
import ru.mail.knhel7.money_transfer_service.model.Transfer;
import ru.mail.knhel7.money_transfer_service.repository.CardRepo;

import java.util.Objects;

@Service
public class CardMoneyTransferService implements ICardMoneyTransferService {

    private CardRepo cardRepo;

    public CardMoneyTransferService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Override
    public Integer transferMoney(Transfer transfer) {
        Integer noTransferID = 0;
        Card sender, receiver;
        try {
            sender = cardRepo.spendMoney(transfer.getCardFromNumber(), transfer.getAmount());
        } catch (RuntimeException ex) {
            // logger...
            return noTransferID;
        }
        // logger...
        try {
            receiver = cardRepo.putMoney(transfer.getCardFromNumber(), transfer.getAmount());
        } catch (RuntimeException ex) {
            // logger...
            return noTransferID;
        }
        // logger...

        Integer transferID = cardRepo.executeTransfer(transfer, sender, receiver);

        if (Objects.equals(transferID, noTransferID)) {
            // logger...
        } else {
            // logger...
        }

        return transferID;
    }
}
