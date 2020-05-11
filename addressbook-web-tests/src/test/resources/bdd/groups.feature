Feature: Groups

  Scenario Outline: Group creation
    Given a set of groups
    When I create new group with name <name>, header <header> and footer <footer>
    Then the new set of groups is equal to the old set with added group

    Examples:
    | name| header| footer|
    |test name0| test header0| test footer0|
    |test name1| test header1| test footer1|

  Scenario Outline: Group creation fail
    Given a set of groups
    When I create new group with name <name>, header <header> and footer <footer>
    Then the new set of groups is equal to the old set

    Examples:
      | name| header| footer|
      |test name'| test header| test footer|