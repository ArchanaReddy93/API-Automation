package StepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static Utilities.APIUtility.response;

public class Hooks {

    public static Logger Log= LogManager.getLogger(Hooks.class);


    @Before
    public void beforeScenario(Scenario scenario)  {
        Log.info("Starting the scenario: ", scenario.getName());

    }
    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            Log.info("Scenario Failed: Capturing response details...");
            if (response != null) {
                scenario.attach("Response Status Code:","text/plain", String.valueOf(response.getStatusCode()));
                scenario.attach("Response Body: ","text/plain", response.prettyPrint());
            }
        }
          }

}
