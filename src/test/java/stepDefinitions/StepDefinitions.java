package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.Booking;
import pojo.CreateBooking;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinitions {

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	Utils utl = new Utils();
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	String bookingId, pathValue, checkInDate, checkOutDate;

	@Given("Create booking payload with {string} {string} {string} {string} {string} {string} {string}")
	public void for_create_booking_payload(String firstName, String lastName, String totalPrice, String depositPaid,
			String checkIn, String checkOut, String additionalOptions) throws IOException, ParseException {
		res = given().spec(utl.requestSpecification())
				.body(data.createABooking(firstName, lastName, Integer.parseInt(totalPrice),
						Boolean.parseBoolean(depositPaid), utl.assignDate(checkIn), utl.assignDate(checkOut),
						additionalOptions));
	}

	@When("user calls {string} endpoint with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {

		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("post"))
			response = res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("get"))
			response = res.when().get(resourceAPI.getResource() + pathValue);
		else if (method.equalsIgnoreCase("getAll"))
			response = res.when().get(resourceAPI.getResource());
		if (method.equalsIgnoreCase("partialUpdate"))
			response = res.when().patch(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("delete"))
			response = res.when().get(resourceAPI.getResource() + pathValue);

	}

	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {

		assertEquals(response.getStatusCode(), 200);

	}

	@Then("bookingid is generated in response body")
	public void the_bookingid_is_generated() {

		bookingId = response.as(Booking.class).getBookingid();
		assertTrue(bookingId.length() > 0);

	}

	@Then("{string} {string} {string} {string} {string} {string} {string} is displayed in the booking payload")
	public void in_response_body_is(String firstName, String lastName, String totalPrice, String depositPaid,
			String checkIn, String checkOut, String additionalOptions) throws ParseException {
		assertEquals(response.as(Booking.class).getBooking().getFirstName(), firstName);
		assertEquals(response.as(Booking.class).getBooking().getLastName(), lastName);
		assertEquals(response.as(Booking.class).getBooking().getTotalPrice(), Integer.parseInt(totalPrice));
		assertEquals(response.as(Booking.class).getBooking().isDepositPaid(), Boolean.parseBoolean(depositPaid));
		assertEquals(response.as(Booking.class).getBooking().getBookingDates().getCheckIn(),  utl.assignDate(checkIn));
		assertEquals(response.as(Booking.class).getBooking().getBookingDates().getCheckOut(), utl.assignDate(checkOut));
		assertEquals(response.as(Booking.class).getBooking().getAdditionalNeeds(), additionalOptions);

	}

	@Then("verify if get bookingbyid returns the booking details {string} {string} {string} {string} {string} {string} {string}")
	public void verify_place_Id_created_maps_to_using(String firstName, String lastName, String totalPrice,
			String depositPaid, String checkIn, String checkOut, String additionalOptions) throws IOException, ParseException {

		// requestSpec

		pathValue = bookingId;
		user_calls_with_http_request("booking", "GET");

		assertEquals(response.as(CreateBooking.class).getFirstName(), firstName);
		assertEquals(response.as(CreateBooking.class).getLastName(), lastName);
		assertEquals(response.as(CreateBooking.class).getTotalPrice(), Integer.parseInt(totalPrice));
		assertEquals(response.as(CreateBooking.class).isDepositPaid(), Boolean.parseBoolean(depositPaid));
		assertEquals(response.as(CreateBooking.class).getBookingDates().getCheckIn(), utl.assignDate(checkIn));
		assertEquals(response.as(CreateBooking.class).getBookingDates().getCheckOut(), utl.assignDate(checkOut));
		assertEquals(response.as(CreateBooking.class).getAdditionalNeeds(), additionalOptions);

	}

}
