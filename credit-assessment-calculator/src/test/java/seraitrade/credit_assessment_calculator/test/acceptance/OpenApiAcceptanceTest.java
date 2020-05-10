package seraitrade.credit_assessment_calculator.test.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OpenApiAcceptanceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private SpringDocConfigProperties springDocConfigProperties;

    private ValidatableResponse response;

    @BeforeEach
    void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        var apiDocUrl = String.format("http://localhost:%d%s", port, springDocConfigProperties.getApiDocs().getPath());
        response = given()
                .accept(ContentType.JSON)
            .when()
                .get(apiDocUrl)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    void shouldBeInVersion3() {
        response.body("openapi", is("3.0.1"));
    }

    @Test
    void shouldHaveServerInfo() {
        response.body("servers.url", hasItem("http://localhost:" + port));
    }

    @Test
    void shouldHaveApiDefined() {
        response.body("paths", hasKey("/credit-assessment-calculator/calculateCreditScore"));
    }
}
