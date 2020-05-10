package seraitrade.credit_assessment_calculator.interfaces.rest.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import seraitrade.credit_assessment_calculator.application.model.CompanyType;

import java.io.IOException;

public class CompanyTypeJsonDeserializer extends JsonDeserializer<CompanyType> {
    @Override
    public CompanyType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        var companyTypeInString = jsonParser.getText();
        return CompanyType.fromReadableName(companyTypeInString);
    }
}
