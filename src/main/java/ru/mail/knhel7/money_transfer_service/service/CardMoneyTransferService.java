package ru.mail.knhel7.money_transfer_service.service;

import org.springframework.stereotype.Service;
import ru.mail.knhel7.money_transfer_service.exception.NotFoundEx;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.Card;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_request.TransferConfirm;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.repository.CardRepo;
import ru.mail.knhel7.money_transfer_service.repository.ICardRepo;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CardMoneyTransferService implements ICardMoneyTransferService {

    private final ICardRepo cardRepo;

    public CardMoneyTransferService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Override
    public Transaction<Transfer> transferConfirm(TransferConfirm confirm) {
        Transaction<Transfer> transaction = validateTransferID(confirm);
        Transfer transfer = transaction.getOperation();

        validateAmount(transfer);

        Card sender = validateSender(transfer);
        Card receiver = validateReceiver(transfer);
        cardRepo.executeTransfer(transaction, sender, receiver);

        return transaction;
    }

    @Override
    public Transaction<Transfer> transferMoney(Transfer transfer) {
        validateAmount(transfer);
        validateSender(transfer);
        validateReceiver(transfer);
        return cardRepo.addTransferTransaction(transfer);
    }

    public Transaction<Transfer> validateTransferID(TransferConfirm confirm) {
        String err = "Перевод №" + confirm.getOperationId() + " не подтвержден: не найден...";
        try {
            int ID = Integer.parseInt(confirm.getOperationId());
            return (Transaction<Transfer>) cardRepo.getTransactionByID(ID).get();
        } catch (NumberFormatException NoSuchElementException) {
            throw new NotFoundEx(err);
        }
    }

    public Card validateSender(Transfer transfer) {
        Card sender;
        try {
            sender = cardRepo.spendMoney(transfer);
            // logger...
            return sender;
        } catch (NoSuchElementException ex) {
            throw new NotFoundEx("Карта отправителя №" + transfer.getCardFromNumber() + " не найдена...");
        } catch (TransferException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OtherTransferEx(transfer + " (" + ex + ")");
        }
    }

    public Card validateReceiver(Transfer transfer) {
        Card receiver;
        try {
            receiver = cardRepo.putMoney(transfer.getCardToNumber(), transfer.getAmount());
            // logger...
            return receiver;
        } catch (NoSuchElementException ex) {
            throw new NotFoundEx("Карта получателя №" + transfer.getCardFromNumber() + " не найдена...");
        } catch (TransferException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OtherTransferEx(transfer + " (" + ex + ")");
        }
    }

    public void validateAmount(Transfer transfer) {
        if (transfer.getAmount().value() <= 0) {
            throw new TransferException("Перевод отрицательной/нулевой суммы не предусмотрен!");
        }
    }

    @Override
    public List<Transfer> getTransferList() {
        return getTransferTransactionList().stream().map(Transaction::getOperation).toList();
    }

    @Override
    public List<Transaction<Transfer>> getTransferTransactionList() {
        return cardRepo.getTransactions().values().stream()
                .filter(transaction -> transaction.getOperation().getClass() == Transfer.class)
                .map(transaction -> (Transaction<Transfer>) transaction)
                .toList();
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
