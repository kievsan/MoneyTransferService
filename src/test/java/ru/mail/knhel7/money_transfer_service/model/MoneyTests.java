package ru.mail.knhel7.money_transfer_service.model;

import org.junit.jupiter.api.*;
import ru.mail.knhel7.moneyTransferService.model.Money;

import java.util.Objects;

public class MoneyTests {

    private static long suiteStartTime;
    private long testStartTime;

    private final String currency = "RUR";
    private final Integer value = 10000;

    private Money amount, amount2;

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
        amount = new Money(this.currency, this.value);
    }

    @AfterEach
    public void finishTest() {
        amount = null;
        amount2 = null;
        System.out.println("Current test complete:" + (System.nanoTime() - testStartTime));
    }

    @Test
    @DisplayName("moneyObjectTest")
    public void moneyObjectTest() {
        Assertions.assertEquals(Money.class, amount.getClass());
        Assertions.assertEquals(currency, amount.currency());
        Assertions.assertEquals(value, amount.value());
    }

    @Test
    @DisplayName("moneyObjectEqualTest")
    public void moneyObjectEqualTest() {
        amount2 = new Money(currency, value);

        Assertions.assertEquals(amount2.equals(amount),
                Objects.equals(amount2.getCurrency(), amount.getCurrency()) &&
                        Objects.equals(amount2.getValue(), amount.getValue())
        );
    }

    @Test
    @DisplayName("moneyPercentageTest")
    public void moneyPercentageTest() {
        Money expected = new Money(100);
        Money actual = amount.getPercentage(1);
        Assertions.assertEquals(expected, actual);
    }
}
