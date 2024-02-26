package ru.mail.knhel7.money_transfer_service.model;

import org.junit.jupiter.api.*;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.Transfer;

import java.util.Objects;

public class TransferTests {

    private static long suiteStartTime;
    private long testStartTime;

    private final String cardFromNumber = "1234567890123456";
    private final String cardFromValidTill = "02/34";
    private final String cardFromCVV = "231";
    private final String cardToNumber = "2345678901234561";
    private final Money amount = new Money(167000);

    private Transfer transfer, transfer2;

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
        transfer = new Transfer(cardFromNumber, cardFromValidTill, cardFromCVV, cardToNumber, amount);
    }

    @AfterEach
    public void finishTest() {
        transfer = null;
        transfer2 = null;
        System.out.println("Current test complete:" + (System.nanoTime() - testStartTime));
    }

    @Test
    @DisplayName("transferObjectTest")
    public void transferObjectTest() {
        Assertions.assertEquals(Transfer.class, transfer.getClass());

        Assertions.assertEquals(cardFromNumber, transfer.getCardFromNumber());
        Assertions.assertEquals(cardToNumber, transfer.getCardToNumber());
        Assertions.assertEquals(amount, transfer.getAmount());
        Assertions.assertEquals(1, transfer.getFeePercent());
    }

    @Test
    @DisplayName("transferObjectEqualTest")
    public void transferObjectEqualTest() {
        transfer2 = new Transfer(
                this.cardFromNumber, this.cardFromValidTill, this.cardFromCVV, this.cardToNumber, this.amount);

        Assertions.assertEquals(transfer2.equals(transfer),
                transfer2.getClass() == transfer.getClass() &&
                        Objects.equals(transfer2.getCardFromNumber(), transfer.getCardFromNumber()) &&
                        Objects.equals(transfer2.getCardToNumber(), transfer.getCardToNumber()) &&
                        Objects.equals(transfer2.getAmount(), transfer.getAmount()) &&
                        Objects.equals(transfer2.getFeePercent(), transfer.getFeePercent())
        );
    }

    @Test
    @DisplayName("transferCommissionTest")
    public void transferCommissionEqualTest() {
        Assertions.assertEquals(1670, transfer.setFeeCommission().value());
    }
}

