Feature: Managing entries and orders at Le Juste bar
  Background:
    Given A bar named "Le Juste" with a maximum capacity of 10 seats
    And Mr. Pignon and Mr. Leblanc want to go to the bar

  Scenario: Denied entry because the bar is almost full
    Given There are already 9 people in the bar
    When Mr. Pignon and Mr. Leblanc arrive
    Then They are denied entry

  Scenario Outline: Entering the bar and ordering drinks
    Given There are already <people> people in the bar
    When Mr. Pignon and Mr. Leblanc arrive
    Then The person behind them is denied entry
    And The bar displays "Full"
    When They each order the cocktail of the month for 10€
    And Mr. Leblanc pays for both
    Then At the end of their drink, they check the bill
    And Mr. Leblanc pays
    And Mr. Pignon is happy because they only had one drink
    Examples:
      | people |
      | 8      |

  Scenario: Ordering additional drinks and paying separately
    Given There are 3 people in the bar
    When Mr. Pignon and Mr. Leblanc arrive
    And They each order the cocktail of the month for 10€
    Then They check their individual bills
    When Mr. Pignon pays his bill
    And Mr. Leblanc insists on ordering another cocktail of the month
    And He orders 2 more cocktails for his bill
    Then At the end of his drinks, Mr. Leblanc checks his bill and pays
    And Mr. Pignon is sad because drinking more than one cocktail makes him sick
