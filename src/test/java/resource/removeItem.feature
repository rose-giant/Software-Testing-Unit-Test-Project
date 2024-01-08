Feature: Evaluate Removing from buy list

  Scenario: Removing not existed item
    Given Execute removeItemFromBuyList
    When Current buy list has commodity " id: "i1" , amount: 2 "
    And If wanted commodity id is "i2"
    Then Throw CommodityIsNotInBuyList error

  Scenario: Removing item that has only one in stock
    Given Execute removeItemFromBuyList
    When Current buy list has commodity " id: "i1" , amount: 1 "
    And If wanted commodity id is "i1"
    Then Remove the commodity from buy list

  Scenario: Removing item that has more than one in stock
    Given Execute removeItemFromBuyList
    When Current buy list has commodity " id: "i1" , amount: 2 "
    And If wanted commodity id is "i1"
    Then Decrease the amount of commodity in buy list by one