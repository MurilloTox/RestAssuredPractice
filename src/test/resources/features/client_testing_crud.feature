@active
Feature: Client testing CRUD

  @smoke
  Scenario: Change the phone number of the first client named Laura
    Given there are at least 10 registered clients
    When I find the first client named "Laura"
    And I save her current phone number
    And I update her phone number
    Then her new phone number should be different
    Then I delete all the registered clients

  @smoke
  Scenario: Retrieve and update active resources
    Given there are at least 5 active resources
    When I find all active resources
    Then I update them as inactive

  @smoke
  Scenario: Create, update, and delete a client
    When I create a new client
    Then I should find the new client
    And I update any parameter of the new client
    And I delete the new client

  @smoke
  Scenario: Update the latest resource
    Given there are at least 15 resources
    When I find the latest resource
    And I update all the parameters of this resource