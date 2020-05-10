package seraitrade.credit_assessment_calculator.interfaces.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import seraitrade.credit_assessment_calculator.application.model.CompanyType;
import seraitrade.credit_assessment_calculator.application.service.CreditAssessmentCalculatorService;
import seraitrade.credit_assessment_calculator.test.model.CreditScoreInputsTestModel;

import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreditAssessmentCalculatorController.class)
class CreditAssessmentCalculatorControllerIntegrationTest {
    @MockBean
    private CreditAssessmentCalculatorService creditAssessmentCalculatorService;

    @SuppressWarnings("unused")
    @MockBean
    private Logger logger;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String url = "/credit-assessment-calculator/calculateCreditScore";

    @Test
    void shouldGetScore() throws Exception {
        // given
        var creditScoreInputsTestModel = defaultCreditScoreInputsTestModelBuilder().build();

        var expectedScore = 10;
        given(creditAssessmentCalculatorService.calculateCreditScore(
                CompanyType.fromReadableName(creditScoreInputsTestModel.getCompanyType()),
                creditScoreInputsTestModel.getNumberOfEmployees(),
                creditScoreInputsTestModel.getNumberOfYearsOperated()
        )).willReturn(expectedScore);

        var payload = toJson(creditScoreInputsTestModel);

        // when
        mockMvc.perform(post(url)
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedScore)));
    }

    @ParameterizedTest(name = "{index} => when input is \"{0}\"")
    @MethodSource("validationFailProvider")
    void shouldReturnBadRequest(CreditScoreInputsTestModel creditScoreInputsTestModel) throws Exception {
        // given
        var payload = toJson(creditScoreInputsTestModel);

        // when
        mockMvc.perform(post(url)
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String toJson(CreditScoreInputsTestModel creditScoreInputsTestModel) throws JsonProcessingException {
        return objectMapper.writeValueAsString(creditScoreInputsTestModel);
    }

    private static Stream<Arguments> validationFailProvider() {
        return Stream.of(
                Arguments.of(defaultCreditScoreInputsTestModelBuilder().companyType(null).build()),
                Arguments.of(defaultCreditScoreInputsTestModelBuilder().companyType("").build()),
                Arguments.of(defaultCreditScoreInputsTestModelBuilder().companyType("unknown").build()),
                Arguments.of(defaultCreditScoreInputsTestModelBuilder().numberOfEmployees(null).build()),
                Arguments.of(defaultCreditScoreInputsTestModelBuilder().numberOfEmployees(0).build()),
                Arguments.of(defaultCreditScoreInputsTestModelBuilder().numberOfYearsOperated(null).build()),
                Arguments.of(defaultCreditScoreInputsTestModelBuilder().numberOfYearsOperated(-1).build())
        );
    }

    private static CreditScoreInputsTestModel.CreditScoreInputsTestModelBuilder defaultCreditScoreInputsTestModelBuilder() {
        return CreditScoreInputsTestModel.builder()
                .companyType("Others")
                .numberOfEmployees(1)
                .numberOfYearsOperated(0);
    }
}