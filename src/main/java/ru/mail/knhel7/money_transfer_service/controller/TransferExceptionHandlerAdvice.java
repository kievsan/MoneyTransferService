package ru.mail.knhel7.money_transfer_service.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mail.knhel7.money_transfer_service.exception.NotFoundEx;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.logger.Logger;
import ru.mail.knhel7.money_transfer_service.model.http_response.TransferExResp;


@RestControllerAdvice
public class TransferExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private final Logger logger = Logger.getLogger();

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<TransferExResp> handlerTransfer(TransferException ex) {
        TransferExResp errResp = new TransferExResp(ex.getMessage());
        logger.logTransactionErr(errResp);
        return new ResponseEntity<>(errResp, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(NotFoundEx.class)
    public ResponseEntity<TransferExResp> handlerCardNotFound(NotFoundEx ex) {
        TransferExResp errResp = new TransferExResp(ex.getMessage());
        logger.logTransactionErr(errResp);
        return new ResponseEntity<>(errResp, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(OtherTransferEx.class)
    public ResponseEntity<TransferExResp> handlerTransfer(OtherTransferEx ex) {
        TransferExResp errResp = new TransferExResp(ex.getMessage());
        logger.logTransactionErr(errResp);
        return new ResponseEntity<>(errResp, HttpStatusCode.valueOf(500));
    }

}
