@UnScheduledDate
Feature: To test Single day schedule of a channel API when schedule is not found

  @scenario7
  Scenario: verify the status code and properties when schedule is not found for the given date
    Given user set channel program API endpoint "api/RMSTest/ibltest/2023-09-11"
    When user send the GET request
    Then validate HTTP status code of the response is 404
    And verify the error object had the properties "details" and "http_response_code"