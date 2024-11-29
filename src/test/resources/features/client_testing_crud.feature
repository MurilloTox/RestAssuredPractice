@active
Feature: Client testing CRUD

  @smoke @test1
  Scenario: Change the phone number of the first client named Laura
    Given there are at least 10 registered clients
    When I find the first client named "Laura"
    And I save her current phone number
    And I update her phone number
    Then her new phone number should be different


