package Utilities;

import StepDefinition.Hooks;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import java.time.Instant;
import java.util.*;
import static StepDefinition.APIStepDef.URI;
import static io.restassured.RestAssured.given;
import static io.restassured.config.HttpClientConfig.httpClientConfig;
import static io.restassured.config.RestAssuredConfig.config;


@SuppressWarnings("unchecked")
public class APIUtility extends Hooks {

    public static Logger Log= LogManager.getLogger(APIUtility.class);
    public static Response response;


    public void getChannelProgram() {
        response = given().get(URI);
        Log.info("API Response in String:{}", response.asPrettyString());
    }

    public void validateResponseStatusCode(int ResponseCode) {
        int responseCode = response.getStatusCode();
        try {
            Assert.assertEquals(ResponseCode, responseCode);
            Log.info("AP Response Status code: {}", responseCode);
            } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyResponseTime(int ResponseTime) {
        try {
            long RequestResponseTime = given()
                    .config(config().httpClient(
                            httpClientConfig()
                                    .setParam("http.connection.timeout", 1000)
                                    .setParam("http.socket.timeout", 1000)
                    ))
                    .when()
                    .get(URI).time();
            if (RequestResponseTime<ResponseTime){
                Assert.assertTrue(true);

            }else{
                Assert.assertFalse(false);
                }
            Log.info("API Response time is: {}", RequestResponseTime);

        } catch(RuntimeException e)  {
            Log.info("Exception at verifying response time{}", String.valueOf(e));
        }
    }

    public void verifyIDIsNeverNullForAllGivenItems(String id, String item) {
        JsonPath extractor = response.jsonPath();
        Map<Object, Object> map = extractor.getMap("schedule");

        ArrayList<Object> elementsList = (ArrayList<Object>) map.get(item);
        for (Object listItem : elementsList) {

            Map<String, String> mapItem = (Map<String, String>) listItem;
            String idValue = mapItem.get(id);
            Log.info("id value from elements: {}", idValue);
            Assert.assertNotNull("ID is null", idValue);
        }

    }

    public void verifyEpisodeTypeIsAlwaysEpisode(String type, String episode, String episodeType) {
        JsonPath extractor = response.jsonPath();
        Map<Object, Object> map = extractor.getMap("schedule");

        ArrayList<Object> elementsList = (ArrayList<Object>) map.get("elements");
        for (Object listItem : elementsList) {
            Map<Object, Object> mapItem = (Map<Object, Object>) listItem;
            Map<Object, Object> episodeMap = (Map<Object, Object>) mapItem.get(episode);
            String episodeTypeValue = (String) episodeMap.get(type);
            Log.info("Episode type value: {}", episodeTypeValue);
            Assert.assertEquals("Type is not episode", episodeType, episodeTypeValue);
        }

    }

    public void verifyTitleIsNeverNullInEpisode(String title, String episode) {
        JsonPath extractor = response.jsonPath();
        Map<Object, Object> map = extractor.getMap("schedule");

        ArrayList<Object> elementsList = (ArrayList<Object>) map.get("elements");
        for (Object listItem : elementsList) {
            Map<Object, Object> mapItem = (Map<Object, Object>) listItem;
            Map<Object, Object> episodeMap = (Map<Object, Object>) mapItem.get(episode);
            String getTitle = (String) episodeMap.get(title);
            Log.info("Title in Episode: {}", getTitle);
            Assert.assertNotNull("Title is null", getTitle);
        }

    }

    public void verifyOnlyOneEpisodeLiveFieldAsTrue(String live, String episode) {
        JsonPath extractor = response.jsonPath();
        Map<Object, Object> map = extractor.getMap("schedule");
        int liveCount = 0;
        ArrayList<Object> elementsList = (ArrayList<Object>) map.get("elements");
        ArrayList<Boolean> list = new ArrayList<Boolean>();
        for (Object listItem : elementsList) {
            Map<Object, Object> mapItem = (Map<Object, Object>) listItem;
            Map<Object, Object> episodeMap = (Map<Object, Object>) mapItem.get(episode);
            boolean getLiveValue = (Boolean) episodeMap.get(live);
            Log.info("Episode Live field value: {}", getLiveValue);
            list.add(getLiveValue);
        }
        for (boolean value : list) {
            if (value) {
                liveCount++;
                if (liveCount > 1) {
                    Assert.assertFalse("More than one live episode found", false);
                }else{
                    Log.info("In Episode Live value as true count {}", liveCount);
                }
            }
        }
    }

    public void verifyTransmissionStartDateIsBeforeTransmissionEndDate(String transmission_start, String transmission_end) {
        JsonPath extractor = response.jsonPath();
        Map<Object, Object> map = extractor.getMap("schedule");

        ArrayList<Object> elementsList = (ArrayList<Object>) map.get("elements");
        for (Object listItem : elementsList) {
            Map<String, String> mapItem = (Map<String, String>) listItem;
            String transmission_startDate = mapItem.get(transmission_start);
            String transmission_endDate = mapItem.get(transmission_end);
            Instant start = Instant.parse(transmission_startDate);
            Instant end = Instant.parse(transmission_endDate);
            try {
                if (start.isBefore(end)) {
                    Log.info("transmission_start date {} is before transmission_end date {}", start, end);
                } else if (start.equals(end)) {
                    Log.info("transmission_start date {} is the same as transmission_end date {}", start, end);
                } else {
                    Log.info("transmission_start date is {} after transmission_end {}", start, end);
                }
            } catch (Exception e) {
                Log.info("Exception at runtime: {}", e.getMessage());
            }
        }
    }

    public void verifyDateValueInResponseHeader() {
        Headers allHeaders = response.headers();
        for (Header header : allHeaders) {
            if (header.getName().equals("Date")) {
                String date = header.getValue();
                Log.info("Date Header value is{} ", date);
            } else {
                Assert.assertFalse("Date Header is not found", false);
            }
        }
    }

    public void verifyErrorDetailsAndResponseCode(String details, String http_response_code){
        JsonPath extractor = response.jsonPath();
        Map<Object, Object> map=extractor.getMap("error");
        for (Object prop : map.keySet()) {
            String key = (String) prop;
            if(key.equals(details)){
                Assert.assertTrue("Error has details key",true);
                Log.info("Error has details key");
            }else if(key.equals(http_response_code)){
                Assert.assertTrue("Error has http_response_code key",true);
                Log.info("Error has http_response_code key");
            } else{
                Assert.assertFalse("details and http_response_code not found in error object",false);
            }

        }

    }
}