package seraitrade.credit_assessment_calculator.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import seraitrade.credit_assessment_calculator.application.model.CompanyType;
import seraitrade.credit_assessment_calculator.application.scope_converter.CompanyTypeToScoreConverter;
import seraitrade.credit_assessment_calculator.application.scope_converter.NumberOfEmployeesToScoreConverter;
import seraitrade.credit_assessment_calculator.application.scope_converter.NumberOfYearsOperatedToScoreConverter;

@Service
@AllArgsConstructor
public class CreditAssessmentCalculatorService {
    private final CompanyTypeToScoreConverter companyTypeToScoreConverter;
    private final NumberOfEmployeesToScoreConverter numberOfEmployeesToScoreConverter;
    private final NumberOfYearsOperatedToScoreConverter numberOfYearsOperatedToScoreConverter;

    public int calculateCreditScore(CompanyType companyType, int numberOfEmployees, int numberOfYearsOperated) {
        var companyTypeScore = companyTypeToScoreConverter.toScore(companyType);
        var numberOfEmployeesScore = numberOfEmployeesToScoreConverter.toScore(numberOfEmployees);
        var numberOfYearsOperatedScore = numberOfYearsOperatedToScoreConverter.toScore(numberOfYearsOperated);

        return companyTypeScore + numberOfEmployeesScore + numberOfYearsOperatedScore;
    }
}
