package org.leanpay.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.leanpay.project.TestUtils;
import org.leanpay.project.model.ErrorResponseDto;
import org.leanpay.project.model.LoanRequestDto;
import org.leanpay.project.model.TotalPaymentDto;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;


@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
@Sql({"/db/create_db.sql"})
public class InstallmentPlanControllerTest {

    private static final String URL = "/v1/installment-plan";
    private WebTestClient webTestClient;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
        new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");


    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @BeforeEach
    void setUp(WebApplicationContext context) {
        this.webTestClient = MockMvcWebTestClient.bindToApplicationContext(context).build();
    }


    @ParameterizedTest
    @MethodSource("provideValidLoanRequestTestData")
    void calculateInstallmentPlanTest(LoanRequestDto requestBody, TotalPaymentDto expectedResponse, int expectedStatus) {
        webTestClient.post()
            .uri(URL)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isEqualTo(expectedStatus)
            .expectBody(TotalPaymentDto.class).isEqualTo(expectedResponse);
    }


    @ParameterizedTest
    @MethodSource("provideInvalidLoanRequestTestData")
    void calculateInstallmentPlanInvalidDataTest(LoanRequestDto requestBody, ErrorResponseDto expectedResponse, int expectedStatus) {
        webTestClient.post()
            .uri(URL)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isEqualTo(expectedStatus)
            .expectBody(ErrorResponseDto.class).isEqualTo(expectedResponse);
    }


    static Stream<Arguments> provideValidLoanRequestTestData() {
        return Stream.of(
            arguments(
                // JSON body for the loan request
                TestUtils.readJson(LoanRequestDto.class, "/data/status-200/request.json"),
                // Expected JSON response
                TestUtils.readJson(TotalPaymentDto.class, "/data/status-200/response.json"),
                // Expected HTTP status
                200
            ),
            arguments(
                // JSON body for the loan request
                TestUtils.readJson(LoanRequestDto.class, "/data/status-200-rate-0/request.json"),
                // Expected JSON response
                TestUtils.readJson(TotalPaymentDto.class, "/data/status-200-rate-0/response.json"),
                // Expected HTTP status
                200
            )
        );
    }


    static Stream<Arguments> provideInvalidLoanRequestTestData() {
        return Stream.of(
            arguments(
                TestUtils.readJson(LoanRequestDto.class, "/data/status-400-amount/request.json"),
                TestUtils.readJson(ErrorResponseDto.class, "/data/status-400-amount/response.json"),
                400
            ),
            arguments(
                TestUtils.readJson(LoanRequestDto.class, "/data/status-400-months/request.json"),
                TestUtils.readJson(ErrorResponseDto.class, "/data/status-400-months/response.json"),
                400
            )
        );
    }

}
