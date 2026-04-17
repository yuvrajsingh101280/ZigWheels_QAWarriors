@login @regression
Feature: Login feature
  @smoke
  Scenario Outline: Login with invalid Google account
    Given user click on login and selects Google option
    When  user switches to Google login window
    And   user enters "<email>" and submits
    Then  error message should be displayed
    Examples:
      |email          |
      |abcd123@zyz.com|
