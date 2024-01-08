Feature: Evaluate Withdraw

  Scenario: Verify invalid withdraw
    Given Execute WithdrawCredit
    When User credit is 20
    And If wanted amount is 21
    Then Throw InsufficientCredit error

  Scenario: Verify valid withdraw
    Given Execute WithdrawCredit
    When User credit is 20
    And If wanted amount is 19
    Then decrease the user credit by amount