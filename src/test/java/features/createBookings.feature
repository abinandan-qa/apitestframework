Feature: Create Bookings

  @CreateBooking
  Scenario: Verify if user is able to create a booking
    Given Create booking payload with "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    When user calls "booking" endpoint with "POST" http request
    Then the API call got success with status code 200
    And bookingid is generated in response body
    And "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>" is displayed in the booking payload
    And verify if get bookingbyid returns the booking details "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
	
Examples:
	|	firstname	|	lastname	|	totalprice	|	depositpaid	|	checkin	| checkout	|	additionalneeds	|
	|	qauserjohn	|	qauserdave	|	1000	|	true	|	2022-12-01	| 2022-12-03	|	coffee	|
	|	qausermark	|	qauserfken	|	2000	|	false		|		| 	|	coffee	|
