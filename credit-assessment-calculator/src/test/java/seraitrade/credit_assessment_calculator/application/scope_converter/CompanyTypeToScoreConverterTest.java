package seraitrade.credit_assessment_calculator.application.scope_converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import seraitrade.credit_assessment_calculator.application.model.CompanyType;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyTypeToScoreConverterTest {
    @InjectMocks
    private CompanyTypeToScoreConverter companyTypeToScoreConverter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest(name = "{index} => when companyType is \"{0}\", it should return \"{1}\"")
    @MethodSource("calculateScoreProvider")
    void shouldReturnScoreCorrectly(CompanyType companyType, int expectedScore) {
        // when
        var score = companyTypeToScoreConverter.toScore(companyType);

        // then
        assertThat(score).isEqualTo(expectedScore);
    }

    private static Stream<Arguments> calculateScoreProvider() {
        return Arrays.stream(CompanyType.values()).map(companyType -> {
            Integer score = null;
            switch(companyType) {
                case SOLE_PROPRIETORSHIP:
                    score = 12;
                    break;
                case LIMITED_LIABILITY_COMPANY:
                    score = 63;
                    break;
                case PARTNERSHIP:
                    score = 75;
                    break;
                case OTHERS:
                    score = 0;
                    break;
            }
            return Arguments.of(companyType, score);
        });
    }
}