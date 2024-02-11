package ru.mail.knhel7.money_transfer_service.model.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class TransferExResp {

    private String message;
    private final Integer id = 0;

    public TransferExResp() {
    }

    public TransferExResp(String message) {
        this.message = "Transfer error: " + message;
    }

    @Override
    public String toString() {
        return message + " (id=" + id + ")";
    }
}
