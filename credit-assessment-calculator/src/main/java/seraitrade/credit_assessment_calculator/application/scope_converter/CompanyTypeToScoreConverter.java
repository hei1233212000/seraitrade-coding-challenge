package seraitrade.credit_assessment_calculator.application.scope_converter;

import org.springframework.stereotype.Component;
import seraitrade.credit_assessment_calculator.application.model.CompanyType;

@Component
public class CompanyTypeToScoreConverter {
    public int toScore(CompanyType companyType) {
        var score = 0;
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
                break;
        }
        return score;
    }
}
