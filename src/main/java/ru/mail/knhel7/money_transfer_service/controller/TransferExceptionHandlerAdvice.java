package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mail.knhel7.money_transfer_service.exception.CardNotFoundEx;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.response.TransferExResp;


@RestControllerAdvice
public class TransferExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<TransferExResp> handlerTransfer(TransferException ex) {
        // logger(ex.getMessage());
        TransferExResp errResp = new TransferExResp(ex.getMessage());
        System.out.println(errResp);
        return new ResponseEntity<>(errResp, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(CardNotFoundEx.class)
    public ResponseEntity<TransferExResp> handlerCardNotFound(CardNotFoundEx ex) {
        // logger(ex.getMessage());
        TransferExResp errResp = new TransferExResp(ex.getMessage());
        System.out.println(errResp);
        return new ResponseEntity<>(errResp, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(OtherTransferEx.class)
    public ResponseEntity<TransferExResp> handlerTransfer(OtherTransferEx ex) {
        // logger(ex.getMessage());
        TransferExResp errResp = new TransferExResp(ex.getMessage());
        System.out.println(errResp);
        return new ResponseEntity<>(errResp, HttpStatusCode.valueOf(500));
    }

}
