package ru.mail.knhel7.moneyTransferService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.mail.knhel7.moneyTransferService.service.TransferService;

@WebMvcTest(CardMoneyTransferController.class)
public class CardMoneyTransferControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    TransferService service;



}
