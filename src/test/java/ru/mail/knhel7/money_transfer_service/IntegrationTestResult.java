package ru.mail.knhel7.money_transfer_service;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.mail.knhel7.money_transfer_service.model.http_response.TransferResponse;

import java.util.Objects;

@Getter
@Setter
public class IntegrationTestResult<R> {
    private final String BaseURL = "http://localhost:5500";
    private Integer port;
    private String URI;
    private R objRequest;

    public IntegrationTestResult() {
    }

    public IntegrationTestResult(R objRequest, Integer port, String URI) {
        this.objRequest = objRequest;
        this.port = port;
        this.URI = URI;
    }

    String result(TestRestTemplate testTemplate) throws JSONException {
        String URL = BaseURL + URI;
        ResponseEntity<TransferResponse> resp = testTemplate.postForEntity(URL, objRequest, TransferResponse.class);
        TransferResponse transferResponse = Objects.requireNonNull(resp.getBody());
        final String result = new JSONObject(transferResponse.toString()).get("operationId").toString();
        System.out.println(URL + " ==> operation ID = " + result);
        return result;
    }
}
