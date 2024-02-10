package ru.mail.knhel7.money_transfer_service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Transfer {
    String cardFromNumber;
    String cardFromValidTill;
    String cardFromCVV;
    String cardToNumber;
    Money amount;
}
