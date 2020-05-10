package seraitrade.credit_assessment_calculator.application.scope_converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NumberOfEmployeesToScoreConverterTest {
    @InjectMocks
    private NumberOfEmployeesToScoreConverter numberOfEmployeesToScoreConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest(name = "{index} => when numberOfEmployees is \"{0}\", it should return \"{1}\"")
    @MethodSource("calculateScoreProvider")
    void shouldReturnScoreCorrectly(int numberOfEmployees, int expectedScore) {
        // when
        var score = numberOfEmployeesToScoreConverter.toScore(numberOfEmployees);

        // then
        assertThat(score).isEqualTo(expectedScore);
    }

    private static Stream<Arguments> calculateScoreProvider() {
        return IntStream.range(1, 22).mapToObj(numberOfEmployees -> {
            var score = 0;
            if(numberOfEmployees >= 6 && numberOfEmployees <= 10) {
                score = 20;
            } else if(numberOfEmployees >= 11 && numberOfEmployees <= 15) {
                score = 32;
            } else if(numberOfEmployees >= 16 && numberOfEmployees <= 20) {
                score = 55;
            } else if(numberOfEmployees > 20) {
                score = 70;
            }
            return Arguments.of(numberOfEmployees, score);
        });
    }
}