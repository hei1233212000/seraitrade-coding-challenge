package seraitrade.credit_assessment_calculator.test.acceptance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import seraitrade.credit_assessment_calculator.test.model.CreditScoreInputsTestModel;

import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.testcontainers.containers.wait.strategy.Wait.forHttp;

@Testcontainers
public class RunInDockerAcceptanceTest {
    @Container
    public GenericContainer<?> app =
            new GenericContainer<>(new ImageFromDockerfile().withDockerfile(Path.of("Dockerfile").toAbsolutePath()))
                    .withExposedPorts(8080)
                    .waitingFor(forHttp("/actuator/health").forStatusCode(200));

    @Test
    void shouldAbleToAccessTheApi() throws JsonProcessingException {
        // given
        var port = app.getFirstMappedPort();
        var url = String.format("http://localhost:%d/credit-assessment-calculator/calculateCreditScore", port);

        var creditScoreInputsTestModel = CreditScoreInputsTestModel.builder()
                .companyType("Others")
                .numberOfEmployees(1)
                .numberOfYearsOperated(0)
                .build();
        var payload = toJson(creditScoreInputsTestModel);

        // when
        given()
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post(url)
            .then()
                .statusCode(200);
    }

    private String toJson(CreditScoreInputsTestModel creditScoreInputsTestModel) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(creditScoreInputsTestModel);
    }
}
