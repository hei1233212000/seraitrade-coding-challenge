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

class NumberOfYearsOperatedToScoreConverterTest {
    @InjectMocks
    private NumberOfYearsOperatedToScoreConverter numberOfYearsOperatedToScoreConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest(name = "{index} => when numberOfYearsOperated is \"{0}\", it should return \"{1}\"")
    @MethodSource("calculateScoreProvider")
    void shouldReturnScoreCorrectly(int numberOfYearsOperated, int expectedScore) {
        // when
        var score = numberOfYearsOperatedToScoreConverter.toScore(numberOfYearsOperated);

        // then
        assertThat(score).isEqualTo(expectedScore);
    }

    private static Stream<Arguments> calculateScoreProvider() {
        return IntStream.range(0, 18).mapToObj(numberOfYearsOperated -> {
            var score = 0;
            if(numberOfYearsOperated >= 4 && numberOfYearsOperated <= 9) {
                score = 28;
            } else if(numberOfYearsOperated >= 10 && numberOfYearsOperated <= 15) {
                score = 36;
            } else if(numberOfYearsOperated >= 16) {
                score = 59;
            }
            return Arguments.of(numberOfYearsOperated, score);
        });
    }
}