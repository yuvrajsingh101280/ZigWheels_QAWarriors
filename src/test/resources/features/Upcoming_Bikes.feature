@bikes @regression
Feature: Upcoming Bikes
  @smoke
  Scenario Outline: Extract upcoming bikes under price limit
    Given user opens Upcoming Bikes Section
    When user filters by bike company "<company>"
    Then upcoming bikes should be displayed and stored in Excel

    Examples:
    |company|
    |Honda  |
    |Suzuki |
    |KTM    |

