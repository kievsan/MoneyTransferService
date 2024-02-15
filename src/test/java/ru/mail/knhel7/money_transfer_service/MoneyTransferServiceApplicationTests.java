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

import java.nio.file.Paths;
import java.util.Objects;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {

	private static final String PROJ = "money_transfer_service";
	private static final String HOST = "http://localhost";
	private static final Integer PORT = 5500;
	private static final String DockerImageName = "rest_transfer";

	@Autowired
	private TestRestTemplate testTemplate;

//	@Test
//	void contextLoads() {
//	}

	@Container
	public final static GenericContainer<?> rest_transfer = new GenericContainer<>(DockerImageName)
			.withExposedPorts(PORT);

	@Container
	public static final GenericContainer<?> docker_container = new GenericContainer<>(new ImageFromDockerfile()
			.withFileFromPath(".", Paths.get("target"))
			.withDockerfileFromBuilder(builder -> builder
					.from("openjdk:21")
					.expose(PORT)
					.add(PROJ + "-0.0.1-SNAPSHOT.jar", DockerImageName + ".jar")
					.entryPoint("java", "-jar", "/" + DockerImageName + ".jar")
					.build()))
			.withExposedPorts(PORT);

	@Test
	void contextLoadsTransfer() throws JSONException {	// ???
		Transfer transfer = new Transfer("1234567890123456",
				"02/34", "231", "9876543210654321", new Money(Currency.CNY, 3000));
		ResponseEntity<Transfer> forTransfer = testTemplate.postForEntity(HOST + ":" + docker_container.getMappedPort(PORT) + "/transfer",
				transfer, Transfer.class);
		String expected = new JSONObject(Objects.requireNonNull(forTransfer.getBody()).toString())
				.get("operationId").toString();
		Assertions.assertEquals(expected, "1");
	}

}
