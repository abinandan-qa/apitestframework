			Feature: Create Bookings

  @CreateBooking
  Scenario: Verify if user is able to create a booking
    Given Create booking payload with "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    When user calls "booking" endpoint with "POST" http request
    Then the API call responds with status code 200
    And bookingid is generated in response body
    And "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>" is displayed in the booking payload
    And verify if get bookingbyid returns the booking details "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    And verify if getAllBookingById has the newly created bookingid
	
Examples:
	|	firstname	|	lastname	|	totalprice	|	depositpaid	|	checkin	| checkout	|	additionalneeds	|
	|	qauserjohn	|	qauserdave	|	1000	|	true	|	2022-12-01	| 2022-12-03	|	coffee	|
	|	qausermark	|	qauserfken	|	2000	|	false		|		| 	|	coffee	|

  @PartialUpdateBooking
  Scenario: Verify if user is able to partial update a booking
    Given Create booking payload with "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    And  user calls "booking" endpoint with "POST" http request
    And  Partial update booking payload with "<newfirstname>" "<newlastname>"
    When user calls "booking" endpoint with "PATCH" http request
    Then the API call responds with status code 200
    And bookingid is generated in response body
    And "<newfirstname>" "<newlastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>" is displayed in the booking payload
    And verify if get bookingbyid returns the booking details "<newfirstname>" "<newlastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
	
Examples:
	|	firstname	|	lastname	|	totalprice	|	depositpaid	|	checkin	| checkout	|	additionalneeds	| newfirstname | newlastname |
	|	qauserchen	|	qauserwan	|	3000	|	true		|		| 	|	carpool	| qausernewchen | qausernewchan |
	|	qausermerry	|	qauserjane	|	9000	|	false		|		| 	|	ac	| qausernewjammy | qausernewrichy |

  @DeleteABooking
  Scenario: Verify if user is able to delete a booking
    Given Create booking payload with "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    And  user calls "booking" endpoint with "POST" http request		
    When user calls "booking" endpoint with "DELETE" http request
    Then the API call responds with status code 201
    And user calls "booking" endpoint with "GET" http request
    And the API call responds with status code 404
    And verify if getAllBookingById does not have the deleted bookingid
	
Examples:
	|	firstname	|	lastname	|	totalprice	|	depositpaid	|	checkin	| checkout	|	additionalneeds	| newfirstname | newlastname |
	|	qauserchen	|	qauserwan	|	3000	|	true		|		| 	|	carpool	| qausernewchen | qausernewchan |
	|	qausermerry	|	qauserjane	|	9000	|	false		|		| 	|	ac	| qausernewjammy | qausernewrichy |
	
	  @E2E @Regression
  Scenario: Booking sanity test
    Given Create booking payload with "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    And user calls "booking" endpoint with "POST" http request
    And Partial update booking payload with "<newfirstname>" "<newlastname>"
    And user calls "booking" endpoint with "PATCH" http request
    And verify if get bookingbyid returns the booking details "<newfirstname>" "<newlastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"	
    When user calls "booking" endpoint with "DELETE" http request
    Then the API call responds with status code 201
    And user calls "booking" endpoint with "GET" http request
    And the API call responds with status code 404
    And verify if getAllBookingById does not have the deleted bookingid
	
Examples:
	|	firstname	|	lastname	|	totalprice	|	depositpaid	|	checkin	| checkout	|	additionalneeds	| newfirstname | newlastname |
	|	qauserchen	|	qauserwan	|	3000	|	true		|		| 	|	carpool	| qausernewchen | qausernewchan |
	|	qausermerry	|	qauserjane	|	9000	|	false		|		| 	|	ac	| qausernewjammy | qausernewrichy |

