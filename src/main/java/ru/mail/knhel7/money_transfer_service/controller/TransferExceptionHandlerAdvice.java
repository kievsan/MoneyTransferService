package ru.mail.knhel7.money_transfer_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mail.knhel7.money_transfer_service.exception.NotFoundEx;
import ru.mail.knhel7.money_transfer_service.exception.OtherTransferEx;
import ru.mail.knhel7.money_transfer_service.exception.TransferException;
import ru.mail.knhel7.money_transfer_service.model.http_response.TransferExResp;
import ru.mail.knhel7.money_transfer_service.util.DateTimeUtil;

@Slf4j
@RestControllerAdvice
public class TransferExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    public TransferExResp errResp(Exception ex) {
        TransferExResp errResp = new TransferExResp(ex.getMessage());
        log.info("[{}, err] {}", DateTimeUtil.timestamp(), errResp);
        return errResp;
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<TransferExResp> handlerTransfer(TransferException ex) {
        return new ResponseEntity<>(errResp(ex), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(NotFoundEx.class)
    public ResponseEntity<TransferExResp> handlerNotFound(NotFoundEx ex) {
        return new ResponseEntity<>(errResp(ex), HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(OtherTransferEx.class)
    public ResponseEntity<TransferExResp> handlerTransfer(OtherTransferEx ex) {
        return new ResponseEntity<>(errResp(ex), HttpStatusCode.valueOf(500));
    }
}
