package ru.mail.knhel7.moneyTransferService;

import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.mail.knhel7.moneyTransferService.model.operation.card_operation.transfer.http_response.TransferResponse;

import java.util.Objects;

@AllArgsConstructor
public class IntegrationTestResult<R> {

    private final R objRequest;
    private final Integer port;
    private final String URI;

    String result(TestRestTemplate testTemplate) throws JSONException {
        String URL = "http://localhost:" + port + URI;
        ResponseEntity<TransferResponse> resp = testTemplate.postForEntity(URL, objRequest, TransferResponse.class);
        TransferResponse transferResponse = Objects.requireNonNull(resp.getBody());
        final String result = new JSONObject(transferResponse.toString()).get("operationId").toString();
        System.out.println(URL + " ==> operation ID = " + result);
        return result;
    }
}
