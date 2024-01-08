Feature: Evaluate Credit

  Scenario: Verify invalid credit
    Given Execute AddCredit
    When If added amount is -1
    Then Throw invalidCreditRange error

  Scenario: Verify valid credit
    Given Execute AddCredit
    When If added amount is 1
    Then Increase the user credit by amount