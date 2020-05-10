package seraitrade.credit_assessment_calculator.test.acceptance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import seraitrade.credit_assessment_calculator.test.model.CreditScoreInputsTestModel;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Service
@Scope(SCOPE_CUCUMBER_GLUE)
public class ScoreCalculatorTestApi {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    @LocalServerPort
    private Integer port;

    /**
     * This would fire the API call to calculate the score.
     *
     * @return the body would be in Integer if success; otherwise Object (normally in error cases);
     */
    public ResponseEntity<Object> calculateScore(Integer numberOfEmployees, String companyType, Integer numberOfYearsOperated) {
        var url = String.format("http://localhost:%d/credit-assessment-calculator/calculateCreditScore", port);
        var httpEntity = generateHttpEntity(numberOfEmployees, companyType, numberOfYearsOperated);

        return restTemplate.postForEntity(url, httpEntity, Object.class);
    }

    private HttpEntity<CreditScoreInputsTestModel> generateHttpEntity(Integer numberOfEmployees, String companyType,
            Integer numberOfYearsOperated) {
        var payload = CreditScoreInputsTestModel.builder()
                .companyType(companyType)
                .numberOfEmployees(numberOfEmployees)
                .numberOfYearsOperated(numberOfYearsOperated)
                .build();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(payload, headers);
    }
}
