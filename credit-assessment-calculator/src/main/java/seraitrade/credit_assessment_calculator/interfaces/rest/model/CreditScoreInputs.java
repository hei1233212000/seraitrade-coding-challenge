package seraitrade.credit_assessment_calculator.interfaces.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import seraitrade.credit_assessment_calculator.application.model.CompanyType;
import seraitrade.credit_assessment_calculator.interfaces.rest.converter.CompanyTypeJsonDeserializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Schema(name = "Credit Score Inputs")
public class CreditScoreInputs {

    @NotNull
    @Schema(
            required = true,
            example = CompanyType.READABLE_VALUE_SOLE_PROPRIETORSHIP,
            type = "String",
            allowableValues = {
                    CompanyType.READABLE_VALUE_SOLE_PROPRIETORSHIP,
                    CompanyType.READABLE_VALUE_LIMITED_LIABILITY_COMPANY,
                    CompanyType.READABLE_VALUE_SOLE_PROPRIETORSHIP,
                    CompanyType.READABLE_VALUE_OTHERS,
            }
    )
    @JsonDeserialize(using = CompanyTypeJsonDeserializer.class)
    private CompanyType companyType;

    @NotNull
    @Min(1)
    @Schema(
            required = true,
            example = "1"
    )
    private Integer numberOfEmployees;

    @NotNull
    @Min(0)
    @Schema(
            required = true,
            example = "0"
    )
    private Integer numberOfYearsOperated;
}
