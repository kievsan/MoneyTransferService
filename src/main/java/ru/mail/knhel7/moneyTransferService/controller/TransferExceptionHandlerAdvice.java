package ru.mail.knhel7.moneyTransferService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mail.knhel7.moneyTransferService.exception.OtherTransferException;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_response.TransferExResp;
import ru.mail.knhel7.moneyTransferService.exception.NotFoundException;
import ru.mail.knhel7.moneyTransferService.exception.TransferException;

@Slf4j
@RestControllerAdvice
public class TransferExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    public TransferExResp errResp(Exception ex) {
        TransferExResp errResp = new TransferExResp(ex.getMessage());
        log.info("[Transfer error] {}", errResp);
        return errResp;
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<TransferExResp> handlerTransfer(TransferException ex) {
        return new ResponseEntity<>(errResp(ex), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<TransferExResp> handlerNotFound(NotFoundException ex) {
        return new ResponseEntity<>(errResp(ex), HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(OtherTransferException.class)
    public ResponseEntity<TransferExResp> handlerTransfer(OtherTransferException ex) {
        return new ResponseEntity<>(errResp(ex), HttpStatusCode.valueOf(500));
    }
}
