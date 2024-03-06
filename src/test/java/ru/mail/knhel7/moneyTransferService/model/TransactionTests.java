package ru.mail.knhel7.moneyTransferService.model;

import org.junit.jupiter.api.*;
import ru.mail.knhel7.moneyTransferService.model.transaction.Transaction;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.Transfer;

import java.util.Objects;

public class TransactionTests {

    private static long suiteStartTime;
    private long testStartTime;

    private final Transfer operation = new Transfer(
            "1234567890123456", "02/34", "231",
            "2345678901234561",
            new Money(167000)
    );
    private Transaction<Transfer> transaction;
    private Transaction<?> transaction2;

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
        System.out.println("Starting new test " + this);
        testStartTime = System.nanoTime();
        transaction = new Transaction<>(operation, 1);
    }

    @AfterEach
    public void finishTest() {
        transaction = null;
        transaction2 = null;
        System.out.println("Current test complete:" + (System.nanoTime() - testStartTime));
    }

    @Test
    @DisplayName("transactionObjectTest")
    public void transactionObjectTest() {
        Assertions.assertEquals(Transaction.class, transaction.getClass());
        Assertions.assertEquals(operation, transaction.getOperation());
        Assertions.assertEquals(1, transaction.getId());
    }

    @Test
    @DisplayName("transactionObjectEqualTest")
    public void transactionObjectEqualTest() {
        transaction2 = new Transaction<>(operation, 1);

        Assertions.assertEquals(transaction2.equals(transaction),
                transaction2.getClass() == transaction.getClass() &&
                        Objects.equals(transaction2.getOperation(), transaction.getOperation()) &&
                        Objects.equals(transaction2.getId(), transaction.getId()) &&
                        Objects.equals(transaction2.getCode(), transaction.getCode())
        );
    }

    @Test
    @DisplayName("transactionIdTest")
    public void transactionIdTest() {
        Assertions.assertEquals(transaction.getId().getClass(), Integer.class);
        Assertions.assertTrue(transaction.getId() > 0);
    }
}
