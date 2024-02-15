package ru.mail.knhel7.money_transfer_service;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.mail.knhel7.money_transfer_service.model.Money;
import ru.mail.knhel7.money_transfer_service.model.addit_code.Currency;
import ru.mail.knhel7.money_transfer_service.model.http_request.Transfer;
import ru.mail.knhel7.money_transfer_service.model.http_response.TransferResponse;

import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {

	private static final String PROJ = "money_transfer_service";
	private static final String HOST = "http://localhost";
	private static final Integer PORT = 5500;
	private static final String DockerImgName = "rest_transfer";

	@Autowired
	private TestRestTemplate testTemplate;

//	@Test
//	void contextLoads() {
//	}

	@Container
	public final static GenericContainer<?> rest_transfer = new GenericContainer<>(DockerImgName)
			.withExposedPorts(PORT);

	@Container
	public static final GenericContainer<?> docker_container = new GenericContainer<>(new ImageFromDockerfile()
			.withFileFromPath(".", Paths.get("target"))
			.withDockerfileFromBuilder(builder -> builder
					.from("openjdk:21")
					.volume("/tmp")
					.expose(PORT)
					.add(PROJ + "-0.0.1-SNAPSHOT.jar", DockerImgName + ".jar")
					.entryPoint("java", "-jar", "/" + DockerImgName + ".jar")
					.build()))
			.withExposedPorts(PORT);

	@Test
	void contextLoadsTransfer() throws JSONException {	// ???	org.testcontainers.containers.ContainerFetchException: Can't get Docker image
		Transfer transfer = new Transfer("1234567890123456", "02/34", "231",
				"2345678901234561", new Money(Currency.RUR, 5000));

		final Integer port = rest_transfer.getMappedPort(PORT);
		assertEquals("1", transferResult(port, transfer));
	}

	String transferResult(Integer port, Transfer transfer) throws JSONException {
		String targetURL = HOST + ":" + port + "/transfer";
		ResponseEntity<Transfer> respTransfer = testTemplate.postForEntity(targetURL, transfer, Transfer.class);
		final String result = new JSONObject(
				Objects.requireNonNull(respTransfer.getBody()).toString()
		).get("operationId").toString();
		System.out.println(targetURL + ": operation ID = " + result);
		return result;
	}

}
