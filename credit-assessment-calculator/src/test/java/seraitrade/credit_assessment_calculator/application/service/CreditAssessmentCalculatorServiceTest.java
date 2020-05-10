package seraitrade.credit_assessment_calculator.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import seraitrade.credit_assessment_calculator.application.model.CompanyType;
import seraitrade.credit_assessment_calculator.application.scope_converter.CompanyTypeToScoreConverter;
import seraitrade.credit_assessment_calculator.application.scope_converter.NumberOfEmployeesToScoreConverter;
import seraitrade.credit_assessment_calculator.application.scope_converter.NumberOfYearsOperatedToScoreConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class CreditAssessmentCalculatorServiceTest {
    @Mock
    private CompanyTypeToScoreConverter companyTypeToScoreConverter;

    @Mock
    private NumberOfEmployeesToScoreConverter numberOfEmployeesToScoreConverter;

    @Mock
    private NumberOfYearsOperatedToScoreConverter numberOfYearsOperatedToScoreConverter;

    @InjectMocks
    private CreditAssessmentCalculatorService creditAssessmentCalculatorService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCalculateCorrectScore() {
        // given
        var companyType = CompanyType.PARTNERSHIP;
        var numberOfEmployees = 10;
        var numberOfYearsOperated = 11;

        var companyTypeScore = 1;
        var numberOfEmployeesScore = 2;
        var numberOfYearsOperatedScore = 3;

        given(companyTypeToScoreConverter.toScore(companyType))
                .willReturn(companyTypeScore);
        given(numberOfEmployeesToScoreConverter.toScore(numberOfEmployees))
                .willReturn(numberOfEmployeesScore);
        given(numberOfYearsOperatedToScoreConverter.toScore(numberOfYearsOperated))
                .willReturn(numberOfYearsOperatedScore);

        // when
        var score = creditAssessmentCalculatorService
                .calculateCreditScore(companyType, numberOfEmployees, numberOfYearsOperated);

        // then
        var expectedScore = companyTypeScore + numberOfEmployeesScore + numberOfYearsOperatedScore;
        assertThat(score).isEqualTo(expectedScore);
    }
}