package seraitrade.credit_assessment_calculator.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScoreInputsTestModel {
    private String companyType;
    private Integer numberOfEmployees;
    private Integer numberOfYearsOperated;
}
