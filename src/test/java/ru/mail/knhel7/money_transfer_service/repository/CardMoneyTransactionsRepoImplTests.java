package ru.mail.knhel7.money_transfer_service.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.model.Money;
import ru.mail.knhel7.money_transfer_service.model.transaction.Transaction;
import ru.mail.knhel7.money_transfer_service.model.transfer.http_request.Transfer;

public class CardMoneyTransactionsRepoImplTests {

    private static long suiteStartTime;
    private long testStartTime;

    private final Transfer transfer = new Transfer(
            "1234567890123456", "02/34", "231",
            "2345678901234561",
            new Money(167000)
    );

    private Transaction<Transfer> transaction;
    private TransactionsRepo repository;

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
        System.out.println("Starting new test");
        testStartTime = System.nanoTime();
        repository = new CardMoneyTransactionsRepoImpl();
        transaction = new Transaction<>(transfer, 1);
    }

    @AfterEach
    public void finishTest() {
        repository = null;
        transaction = null;
        System.out.println("Current test complete:" + (System.nanoTime() - testStartTime));
    }

    @Test
    @DisplayName("addTransferTransactionTest")
    public void addTransferTransaction() {
        try {
            Transaction<Transfer> actual = repository.addTransferTransaction(transfer);
            Assertions.assertEquals(transaction, actual);
        } catch (OtherTransferEx ex) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("executeTransferTest")
    public void executeTransfer() {
        Transaction<Transfer> transaction1;
        Transaction<?> transaction2;
        try {
            transaction1 = repository.addTransferTransaction(transfer);
            repository.executeTransfer(transaction1);
            transaction2 = repository.getTransactionByID(transaction1.getId()).get();
        } catch (OtherTransferEx NoSuchElementException) {
            Assertions.fail();
            return;
        }

        Assertions.assertEquals(transaction1, transaction2);

        Transfer transfer2 = (Transfer) transaction2.getOperation();
        int actual = transfer2.getCommission().value();

        Assertions.assertEquals(1670, actual);
    }

}
