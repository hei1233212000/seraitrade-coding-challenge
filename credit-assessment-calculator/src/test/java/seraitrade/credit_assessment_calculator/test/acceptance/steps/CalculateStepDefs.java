package seraitrade.credit_assessment_calculator.test.acceptance.steps;

import io.cucumber.java8.En;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import seraitrade.credit_assessment_calculator.test.acceptance.service.ScoreCalculatorTestApi;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculateStepDefs implements En {
    private ResponseEntity<Object> responseEntity = null;

    public CalculateStepDefs(ScoreCalculatorTestApi calculator) {
        Before(1, () -> responseEntity = null);

        When("The calculateCreditAssessmentScore API is called with parameters {int}, {string}, {int}",
                (Integer numberOfEmployees, String companyType, Integer numberOfYearsOperated) ->
                {
                    responseEntity =
                            calculator.calculateScore(numberOfEmployees, companyType, numberOfYearsOperated);
                    assertThat(responseEntity).isNotNull();
                });

        Then("The credit assessment score should match {int}", (Integer expectedScore) -> {
            assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            assertThat(responseEntity.getBody()).isEqualTo(expectedScore);
        });

        Then("^We should see validation error$", () -> {
            assertThat(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST).isTrue();
        });
    }
}
