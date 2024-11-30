@active
Feature: Client testing CRUD

  @smoke
  Scenario: Change the phone number of the first client named Laura
    Given there are at least 10 registered clients
    When I find the first client named "Laura"
    And I save her current phone number
    And I update her phone number
    Then her new phone number should be different

  @smoke
  Scenario: Retrieve and update active resources
    Given there are at least 5 active resources
    When I find all active resources
    Then I update them as inactive

  @test1
  Scenario: Create, update, and delete a client
    When I create a new client
    Then I should find the new client
    And I update any parameter of the new client
    And I delete the new client
    And the response status should be 200
    And the response body should match the expected schema
    And the response body data should reflect the update