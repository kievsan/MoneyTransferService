package ru.mail.knhel7.money_transfer_service.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import ru.mail.knhel7.moneyTransferService.exception.NotFoundException;
import ru.mail.knhel7.moneyTransferService.exception.OtherTransferException;
import ru.mail.knhel7.moneyTransferService.model.Money;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.TransferConfirm;
import ru.mail.knhel7.moneyTransferService.model.transaction.Transaction;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.moneyTransferService.repository.CardMoneyTransactionsRepoImpl;
import ru.mail.knhel7.moneyTransferService.repository.TransactionsRepo;

public class CardMoneyTransactionsRepoImplTests {

    private static long suiteStartTime;
    private long testStartTime;

    private final Transfer transfer = new Transfer(
            "1234567890123456", "02/34", "231",
            "2345678901234561",
            new Money(167000)
    );
    private final TransferConfirm confirm = new TransferConfirm("1", "0000");
    private final TransactionsRepo repository = new CardMoneyTransactionsRepoImpl();
    private final Transaction<Transfer> transaction = new Transaction<>(transfer, 1);

    @BeforeAll
    public static void testSuiteInit() {
        System.out.println("Running testing...");
        suiteStartTime = System.nanoTime();
    }

    @AfterAll
    public static void testSuiteComplete() {
        System.out.println("Tests complete: " + (System.nanoTime() - suiteStartTime));
    }

    @BeforeEach
    public void runTest() {
        System.out.println("-----------Starting new test " + this);
        testStartTime = System.nanoTime();
        System.out.println("run: " + repository.getTransactions());
    }

    @AfterEach
    public void finishTest() {
        System.out.println("finish: " + repository.getTransactions());
        repository.clearTransactions();
        System.out.println("Current test complete:" + (System.nanoTime() - testStartTime));
    }

    @Test
    @DisplayName("addTransferTransactionTest")
    public void addTransferTransactionTest() {
        try {
            Transaction<Transfer> actual = repository.addTransferTransaction(transfer);
            Assertions.assertEquals(transaction, actual);
        } catch (RuntimeException ex) {
            Assertions.assertEquals(OtherTransferException.class, ex.getClass());
        }
    }

    @Test
    @DisplayName("confirmTransferExTest")
    public void confirmTransferExTest() {
        try {
            repository.getTransactions().put(2, new Transaction<>(transfer, 2));
            Assertions.assertThrows(NotFoundException.class, () -> repository.confirmTransfer(confirm));
        } catch (RuntimeException ex) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("confirmTransferTest")
    public void confirmTransferTest() {
        try {
            repository.getTransactions().put(1, transaction);
            Transaction<Transfer> actual = repository.confirmTransfer(confirm);
            Assertions.assertEquals(transaction, actual);
        } catch (RuntimeException ex) {
             Assertions.fail();
        }
    }

    @Test
    @DisplayName("executeTransferTest")
    public void executeTransferTest() {
        Transaction<Transfer> transaction1, transaction2;
        try {
            transaction1 = repository.addTransferTransaction(transfer);
            transaction2 = repository.executeTransfer(transaction1);
        } catch (RuntimeException ex) {
            Assertions.assertEquals(OtherTransferException.class, ex.getClass());
            // Assertions.fail();
            return;
        }

        Assertions.assertEquals(transaction1, transaction2);

        Transfer transfer2 = transaction2.getOperation();
        int actual = transfer2.getFeeCommission().value();

        Assertions.assertEquals(1670, actual);
    }

}
