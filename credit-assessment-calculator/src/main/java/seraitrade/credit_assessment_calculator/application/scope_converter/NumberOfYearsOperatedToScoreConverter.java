package seraitrade.credit_assessment_calculator.application.scope_converter;

import org.springframework.stereotype.Component;

@Component
public class NumberOfYearsOperatedToScoreConverter {
    public int toScore(int numberOfYearsOperated) {
        var score = 0;
        if(numberOfYearsOperated >= 4 && numberOfYearsOperated <= 9) {
            score = 28;
        } else if(numberOfYearsOperated >= 10 && numberOfYearsOperated <= 15) {
            score = 36;
        } else if(numberOfYearsOperated >= 16) {
            score = 59;
        }
        return score;
    }
}
