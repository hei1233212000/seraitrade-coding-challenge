package seraitrade.credit_assessment_calculator.test.acceptance;

import io.cucumber.java8.En;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * It needs to be a Glue to make cucumber could scan it out. That's why it implements the {@link En}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@CucumberContextConfiguration
public class StartupSpringInCucumber implements En {}
