package seraitrade.credit_assessment_calculator.application.scope_converter;

import org.springframework.stereotype.Component;

@Component
public class NumberOfEmployeesToScoreConverter {
    public int toScore(int numberOfEmployees) {
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
        return score;
    }
}
