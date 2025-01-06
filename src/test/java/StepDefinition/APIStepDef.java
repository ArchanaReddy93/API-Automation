package StepDefinition;

import Utilities.APIUtility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

import static Utilities.CommonUtility.getBaseURI;

public class APIStepDef {

    public static Logger Log= LogManager.getLogger(APIStepDef.class);
    APIUtility APIUtility=new APIUtility();
    public static String URI;

    @Given("user set channel program API endpoint {string}")
    public void userSetChannelProgramAPIEndpoint(String endPoint) throws IOException {
        URI=getBaseURI()+endPoint;
        Log.info("Get End Point: {}", URI);
    }

    @When("user send the GET request")
    public void userSendTheGETRequest() {
        APIUtility.getChannelProgram();
    }

    @Then("validate HTTP status code of the response is {int}")
    public void validateHTTPStatusCodeOfTheResponseIs(int ResponseCode) {
        APIUtility.validateResponseStatusCode(ResponseCode);
    }

    @And("verify the response time less than {int} milliseconds")
    public void verifyTheResponseTimeLessThanMilliseconds(int ResponseTime) {
        APIUtility.verifyResponseTime(ResponseTime);
    }

    @And("verify {string} field is never null or empty for all items present in the {string}")
    public void verifyFieldIsNeverNullOrEmptyForAllItemsPresentInThe(String id, String item) {
        APIUtility.verifyIDIsNeverNullForAllGivenItems(id,item);
    }

    @And("Verify that the {string} in {string} for every item is always {string}")
    public void verifyThatTheInForEveryItemIsAlways(String type, String episode, String episodeType) {
        APIUtility.verifyEpisodeTypeIsAlwaysEpisode(type,episode,episodeType);
    }

    @And("verify {string} field in {string} is never null or empty for any schedule item")
    public void verifyFieldInIsNeverNullOrEmptyForAnyScheduleItem(String title, String episode) {
        APIUtility.verifyTitleIsNeverNullInEpisode(title,episode);
    }

    @And("verify that only one episode in the list has {string} field in {string} as true")
    public void verifyThatOnlyOneEpisodeInTheListHasFieldInAsTrue(String live, String episode) {
        APIUtility.verifyOnlyOneEpisodeLiveFieldAsTrue(live,episode);
    }

    @And("Verify that the {string} date value is before the {string} date")
    public void verifyThatTheDateValueIsBeforeTheDate(String transmission_start, String transmission_end) {
        APIUtility.verifyTransmissionStartDateIsBeforeTransmissionEndDate(transmission_start,transmission_end);
    }

    @And("verify the Date value in the response headers")
    public void verifyTheDateValueInTheResponseHeaders() {
        APIUtility.verifyDateValueInResponseHeader();
    }

    @And("verify the error object had the properties {string} and {string}")
    public void verifyTheErrorObjectHadThePropertiesAnd(String details, String http_response_code) {
        APIUtility.verifyErrorDetailsAndResponseCode(details,http_response_code);
    }
}
