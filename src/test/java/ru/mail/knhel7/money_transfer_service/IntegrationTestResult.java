package ru.mail.knhel7.money_transfer_service;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.mail.knhel7.money_transfer_service.model.http_response.TransferResponse;

import java.util.Objects;

public class IntegrationTestResult<R> {

    private final R objRequest;
    private final Integer port;
    private final String URI;

    public IntegrationTestResult(R objRequest, Integer port, String URI) {
        this.objRequest = objRequest;
        this.port = port;
        this.URI = URI;
    }

    String result(TestRestTemplate testTemplate) throws JSONException {
        String URL = "http://localhost:" + port + URI;
        ResponseEntity<TransferResponse> resp = testTemplate.postForEntity(URL, objRequest, TransferResponse.class);
        TransferResponse transferResponse = Objects.requireNonNull(resp.getBody());
        final String result = new JSONObject(transferResponse.toString()).get("operationId").toString();
        System.out.println(URL + " ==> operation ID = " + result);
        return result;
    }
}
