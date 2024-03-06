package ru.mail.knhel7.moneyTransferService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {

	private static final String PROJ = "money_transfer_service";
	private static final Integer PORT = 5500;
	private static final String DockerImgName = "rest_transfer";

	@Autowired
	private TestRestTemplate testTemplate;

	@Test
	void contextLoads() {
	}

//	@Container
//	public static final GenericContainer<?> docker_container = new GenericContainer<>(new ImageFromDockerfile()
//			.withFileFromPath(".", Paths.get("target"))
//			.withDockerfileFromBuilder(builder -> builder
//					.from("openjdk:21")
//					.volume("/tmp")
//					.expose(PORT)
//					.add(PROJ + "-0.0.1-SNAPSHOT.jar", DockerImgName + ".jar")
//					.entryPoint("java", "-jar", "/" + DockerImgName + ".jar")
//					.build()))
//			.withExposedPorts(PORT);
//
//	@Container
//	public static final GenericContainer<?> rest_transfer = new GenericContainer<>(PROJ + "-" + DockerImgName)
//			.withExposedPorts(PORT);
//
//	@Test
//	void contextLoadsTransfer() throws JSONException {	// ???	org.testcontainers.containers.ContainerFetchException: Can't get Docker image
//		Transfer transfer = new Transfer("1234567890123456", "02/34", "231",
//				"2345678901234561", new Money(5000));
//
//		final Integer port = rest_transfer.getMappedPort(PORT);
//		IntegrationTestResult<Transfer> transferTest = new IntegrationTestResult<>(transfer, port, "/transfer");
//		assertEquals("1", transferTest.result(testTemplate));
//	}
//
//	@Test
//	void contextLoadsTransferConfirm() throws JSONException {	// ???	org.testcontainers.containers.ContainerFetchException: Can't get Docker image
//		TransferConfirm confirm = new TransferConfirm("1", "00");
//
//		final Integer port = rest_transfer.getMappedPort(PORT);
//		IntegrationTestResult<TransferConfirm> confirmTest = new IntegrationTestResult<>(confirm, port, "/confirmOperation");
//		assertEquals("1", confirmTest.result(testTemplate));
//	}

}
