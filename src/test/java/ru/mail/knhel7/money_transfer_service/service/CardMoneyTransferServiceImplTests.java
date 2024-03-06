package ru.mail.knhel7.money_transfer_service.service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import ru.mail.knhel7.moneyTransferService.exception.OtherTransferException;
import ru.mail.knhel7.moneyTransferService.model.Money;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.Transfer;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_request.TransferConfirm;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_response.TransferResponse;
import ru.mail.knhel7.moneyTransferService.model.transaction.Transaction;
import ru.mail.knhel7.moneyTransferService.repository.TransactionsRepo;
import ru.mail.knhel7.moneyTransferService.service.CardMoneyTransferServiceImpl;
import ru.mail.knhel7.moneyTransferService.service.TransferService;

public class CardMoneyTransferServiceImplTests {

    private TransferService service;

    private static long suiteStartTime;
    private long testStartTime;

    private final Transfer transfer = new Transfer(
            "1234567890123456", "02/34", "231",
            "2345678901234561",
            new Money(167000)
    );
    private final TransferConfirm confirm = new TransferConfirm("1", "0000");

    private Transaction<Transfer> transaction;
    private TransferResponse response;

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
        System.out.println("Starting new test" + this);
        testStartTime = System.nanoTime();
        transaction = new Transaction<>(transfer, 1);
        response = new TransferResponse(1);
    }

    @AfterEach
    public void finishTest() {
        service = null;
        transaction = null;
        response = null;
        System.out.println("Current test complete:" + (System.nanoTime() - testStartTime));
    }

    @Test
    @DisplayName("transferMoneyTest")
    public void transferMoneyTest() {
        TransactionsRepo repo = Mockito.mock(TransactionsRepo.class);
        Mockito.when(repo.addTransferTransaction(Mockito.any(Transfer.class)))
                .thenReturn(transaction);

        service = new CardMoneyTransferServiceImpl(repo);
        try {
            ResponseEntity<TransferResponse> transferResponse = service.transferMoney(transfer);
            ResponseEntity<TransferResponse> expected = ResponseEntity.ok(response);
            // ResponseEntity<TransferResponse> expected = ResponseEntity.ok(new TransferResponse(11));
            Assertions.assertEquals(expected, transferResponse);
        } catch (RuntimeException ex) {
            Assertions.assertEquals(OtherTransferException.class, ex.getClass());
        }
    }

    @Test
    @DisplayName("transferConfirmTest")
    public void transferConfirmTest() {
        TransactionsRepo repo = Mockito.mock(TransactionsRepo.class);
        Mockito.when(repo.addTransferTransaction(Mockito.any(Transfer.class)))
                .thenReturn(transaction);
        Mockito.when(repo.confirmTransfer(Mockito.any(TransferConfirm.class)))
                .thenReturn(transaction);
        Mockito.when(repo.executeTransfer(Mockito.any(Transaction.class)))
                .thenReturn(transaction);

        service = new CardMoneyTransferServiceImpl(repo);
        try {
            service.transferMoney(transfer);
            ResponseEntity<TransferResponse> transferResponse = service.transferConfirm(confirm);
            ResponseEntity<TransferResponse> expected = ResponseEntity.ok(response);
            Assertions.assertEquals(expected, transferResponse);
        } catch (RuntimeException ex) {
            Assertions.assertEquals(OtherTransferException.class, ex.getClass());
            // Assertions.fail();
        }
    }

}
