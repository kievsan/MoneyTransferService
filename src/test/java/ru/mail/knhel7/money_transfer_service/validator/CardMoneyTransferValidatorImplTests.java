package ru.mail.knhel7.money_transfer_service.validator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.Money;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.operation.card_operation.transfer.http_request.TransferConfirm;

import java.util.stream.Stream;

public class CardMoneyTransferValidatorImplTests {

    private static long suiteStartTime;
    private long testStartTime;

    private final TransferValidator validator = new CardMoneyTransferValidatorImpl();

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
        System.out.print("Starting new test");
        testStartTime = System.nanoTime();
    }

    @AfterEach
    public void finishTest() {
        System.out.println("Current test complete:" + (System.nanoTime() - testStartTime));
    }

    @ParameterizedTest(name = "{index} - {0} throw the TransferException")
    @NullSource
    @DisplayName("validateNulTransferConfirmExTests")
    public void validateNullTransferConfirmExTests(TransferConfirm confirm) {
        System.out.println(": null-подтверждение с выбросом исключения TransferException\t" + this);
        Assertions.assertNull(confirm);
    }

    @ParameterizedTest(name = "{index} - {0} throw the TransferException")
    @MethodSource
    @DisplayName("validateTransferConfirmExTests")
    public void validateTransferConfirmExTests(TransferConfirm confirm) {
        System.out.println(": невалидное подтверждение с выбросом исключения TransferException\t" + this);
        Assertions.assertThrows(TransferException.class, () -> validator.validateTransferConfirm(confirm));
    }

    static Stream<TransferConfirm> validateTransferConfirmExTests() {
        return Stream.of(
                new TransferConfirm("", "0000"),
                new TransferConfirm("1", ""),
                new TransferConfirm("11", "012"));
    }

    @Test
    @DisplayName("validateTransferConfirmTest")
    public void validateTransferConfirmTest() {
        System.out.println(": валидное подтверждение без выброса исключений\t" + this);
        TransferConfirm confirm = new TransferConfirm("1", "0000");
        Assertions.assertDoesNotThrow(() -> validator.validateTransferConfirm(confirm));
    }

    @ParameterizedTest(name = "{index} - {0} throw the TransferException")
    @NullSource
    @DisplayName("validateNullTransferMoneyExTest")
    public void validateNullTransferMoneyExTest(Transfer transfer) {
        System.out.println(": null-перевод с выбросом исключения TransferException\t" + this);
        Assertions.assertNull(transfer);
    }

    @ParameterizedTest(name = "{index} - {0} throw the TransferException")
    @MethodSource
    @DisplayName("validateTransferMoneyExTests")
    public void validateTransferMoneyExTests(Transfer transfer) {
        System.out.println(": невалидный перевод с выбросом исключения TransferException\t" + this);
        Assertions.assertThrows(TransferException.class, () -> validator.validateTransferMoney(transfer));
    }

    static Stream<Transfer> validateTransferMoneyExTests() {
        return Stream.of(
                new Transfer("1234567890123456", "02/34", "231",
                        "2345678901234561", new Money(-1000)),
                new Transfer("1234567890123456", "02/34", "231",
                        "2345678901234561", new Money(0)),
                new Transfer("12345678901", "02/34", "231",
                        "2345678901234561", new Money(3000)),
                new Transfer("1234567890123456", "02/34", "231",
                        "2345678901", new Money(4000)),
                new Transfer("1234567890123456", "02/34", "231000",
                        "2345678901234561", new Money(5000)),
                new Transfer("1234567890123456", "02/34", "",
                        "2345678901234561", new Money(5100)),
                new Transfer("1234567890123456", "0234", "231",
                        "2345678901234561", new Money(6000)),
                new Transfer("1234567890123456", "2/24", "231",
                        "2345678901234561", new Money(6100)),
                new Transfer("1234567890123456", "13/24", "231",
                        "2345678901234561", new Money(7000)),
                new Transfer("1234567890123456", "01/24", "231",
                        "2345678901234561", new Money(7100))
        );
    }

    @Test
    @DisplayName("validateTransferMoneyTest")
    public void validateTransferMoneyTest() {
        System.out.println(": null-перевод с выбросом исключения TransferException\t" + this);
        Transfer transfer = new Transfer(
                "1234567890123456", "02/34", "231",
                "2345678901234561", new Money(167000)
        );
        Assertions.assertDoesNotThrow(() -> validator.validateTransferMoney(transfer));
    }
}
