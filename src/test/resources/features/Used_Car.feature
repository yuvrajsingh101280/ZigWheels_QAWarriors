@usedcars @regression
Feature: Used cars

  @smoke
  Scenario Outline: view popular used cars for different cities
    Given user opens Used Cars section
    When  user selects city "<city>"
    Then  popular used car models should be displayed

    Examples:
      | city    |
      |Chennai  |
      |Delhi    |
      |Bangalore|