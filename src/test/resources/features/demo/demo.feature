Feature: Log in functional for facebook

  @UI @Run
  Scenario Outline: Creating account validations
    Given user navigates to following 'https://www.facebook.com/' link
    And page 'FacebookHomePage' is displayed
    When user inserts following data in current page
      | fieldName     | fieldValue     |
      | <elementName> | <elementValue> |
    And user clicks on 'submit' from current page
    Then page contains following text
      | What’s your name? |
    Examples:
      | elementName | elementValue |
      | firstName   | 1            |
      | lastName    | 1            |