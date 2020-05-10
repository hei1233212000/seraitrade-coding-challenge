package seraitrade.credit_assessment_calculator.interfaces.rest.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import seraitrade.credit_assessment_calculator.application.model.CompanyType;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class CompanyTypeJsonDeserializerTest {
    @InjectMocks
    private CompanyTypeJsonDeserializer companyTypeJsonDeserializer;

    @Mock
    private JsonParser jsonParser;

    @Mock
    private DeserializationContext deserializationContext;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        companyTypeJsonDeserializer = new CompanyTypeJsonDeserializer();
    }

    @ParameterizedTest(name = "{index} => when the input string is \"{0}\", it should deserialize it to \"{1}\"")
    @MethodSource("DeserializationTestProvider")
    void shouldAbleToDeserializeStringIntoCompanyType(
            String inputString,
            CompanyType expectedCompanyType) throws IOException {
        // given
        given(jsonParser.getText())
                .willReturn(inputString);

        // when
        var companyType = companyTypeJsonDeserializer.deserialize(jsonParser, deserializationContext);

        // then
        assertThat(companyType).isEqualTo(expectedCompanyType);
    }

    @Test
    void shouldThrowExceptionWhenUnknownCompanyTypeIsUsed() throws IOException {
        // given
        given(jsonParser.getText())
                .willReturn("unknown");

        // when
        assertThrows(
                NoSuchElementException.class,
                () -> companyTypeJsonDeserializer.deserialize(jsonParser, deserializationContext)
        );
    }

    private static Stream<Arguments> DeserializationTestProvider() {
        return Arrays.stream(CompanyType.values())
                .map(companyType -> Arguments.of(companyType.getReadableName(), companyType));
    }
}