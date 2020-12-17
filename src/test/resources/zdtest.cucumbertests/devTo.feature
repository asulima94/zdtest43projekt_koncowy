Feature: basic dev to functionalities
  Scenario: Select first podcast and play it
    Given DevTo main page is open
    When User click on podcasts
    And User select first podcast
    Then User can see its title
    And User can play it
  Scenario: Select first video and play it
    Given DevTo main page is open
    When User click on videos
    And User select first video
    Then User can see first video page

