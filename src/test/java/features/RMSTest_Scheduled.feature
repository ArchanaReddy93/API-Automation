@SheduledAPI
Feature: Single day schedule of a channel API

  Background: To check API is up and running
    Given user set channel program API endpoint "api/RMSTest/ibltest"
    When user send the GET request
    Then validate HTTP status code of the response is 200


  @scenario1
  Scenario: verify the response code and time of a channel API for a single day schedule
    And verify the response time less than 1000 milliseconds

  @scenario2
  Scenario: verify id is never null and episode type is always episode for all the given items
    And verify "id" field is never null or empty for all items present in the "elements"
    And Verify that the "type" in "episode" for every item is always "episode"

  @scenario3
  Scenario: Verify that the title field in episode is never null or empty for any schedule item
    And verify "title" field in "episode" is never null or empty for any schedule item

  @scenario4
  Scenario: Verify that only one episode in the list has live field in episode as true
    And verify that only one episode in the list has "live" field in "episode" as true

  @scenario5
  Scenario: Verify that the transmission_start date value is before the transmission_end date
    And Verify that the "transmission_start" date value is before the "transmission_end" date

  @scenario6
  Scenario: verify the Date value in the response headers
    And verify the Date value in the response headers





