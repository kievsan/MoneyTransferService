package ru.mail.knhel7.money_transfer_service.service;

import org.springframework.stereotype.Service;
import ru.mail.knhel7.money_transfer_service.exception.CardNotFoundEx;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.Card;
import ru.mail.knhel7.money_transfer_service.model.Transfer;
import ru.mail.knhel7.money_transfer_service.repository.CardRepo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class CardMoneyTransferService implements ICardMoneyTransferService {

    private final CardRepo cardRepo;

    public CardMoneyTransferService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Override
    public Integer transferMoney(Transfer transfer) {
        if (transfer.getAmount().value() <= 0) {
            throw new TransferException("Перевод отрицательной/нулевой суммы не предусмотрен!");
        }

        Card sender, receiver;

        try {
            sender = cardRepo.spendMoney(transfer.getCardFromNumber(), transfer.getAmount());
        } catch (NoSuchElementException ex) {
            throw new CardNotFoundEx("Карта отправителя №" + transfer.getCardFromNumber() + " не найдена...");
        } catch (TransferException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OtherTransferEx(transfer + " (" + ex + ")");
        }
        // logger...

        try {
            receiver = cardRepo.putMoney(transfer.getCardToNumber(), transfer.getAmount());
        } catch (NoSuchElementException ex) {
            throw new CardNotFoundEx("Карта получателя №" + transfer.getCardFromNumber() + " не найдена...");
        } catch (TransferException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OtherTransferEx(transfer + " (" + ex + ")");
        }
        // logger...

        Integer transferID = cardRepo.executeTransfer(transfer, sender, receiver);

        if (transferID == 0) {
            // logger...
        } else {
            // logger...
        }

        return transferID;
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return cardRepo.getAllTransfers();
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepo.getAllCards();
    }

    @Override
    public List<String> getAllCardNumbers() {
        return cardRepo.getAllCardNumbers();
    }
}
