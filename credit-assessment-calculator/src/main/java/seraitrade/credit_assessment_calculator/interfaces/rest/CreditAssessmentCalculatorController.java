package seraitrade.credit_assessment_calculator.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import seraitrade.credit_assessment_calculator.application.service.CreditAssessmentCalculatorService;
import seraitrade.credit_assessment_calculator.interfaces.rest.model.CreditScoreInputs;

import javax.validation.Valid;

@RestController
@RequestMapping("credit-assessment-calculator/")
@AllArgsConstructor
public class CreditAssessmentCalculatorController {
    private final Logger logger;
    private final CreditAssessmentCalculatorService creditAssessmentCalculatorService;

    @PostMapping(
            value = "/calculateCreditScore",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(description = "calculate the credit")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The calculated score would be returned"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error"
            )
    })
    @ResponseBody
    public Integer calculateCreditScore(
            @RequestBody @Valid CreditScoreInputs creditScoreInputs
    ) {
        logger.info("creditScoreInputs: {}", creditScoreInputs);
        return creditAssessmentCalculatorService.calculateCreditScore(
                creditScoreInputs.getCompanyType(),
                creditScoreInputs.getNumberOfEmployees(),
                creditScoreInputs.getNumberOfYearsOperated()
        );
    }
}
