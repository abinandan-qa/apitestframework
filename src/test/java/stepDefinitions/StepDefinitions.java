package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.Booking;
import pojo.CreateBooking;
import pojo.GetBookingIDs;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinitions {

	RequestSpecification req;
	ResponseSpecification resspec;
	Response response;
	Utils utl = new Utils();
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	String bookingId, checkInDate, checkOutDate;

	public List<GetBookingIDs> getBookingIDsList() {
		triggerHTTPRequest("booking", "GETALL");
		List<GetBookingIDs> bookingids = response.jsonPath().getList(".");
		return bookingids;
	}

	@Given("Create booking payload with {string} {string} {string} {string} {string} {string} {string}")
	public void createBookingPayloadCreation(String firstName, String lastName, String totalPrice, String depositPaid,
			String checkIn, String checkOut, String additionalOptions) throws IOException, ParseException {
		req = given().spec(utl.requestSpecification())
				.body(data.createABooking(firstName, lastName, Integer.parseInt(totalPrice),
						Boolean.parseBoolean(depositPaid), utl.assignDate(checkIn), utl.assignDate(checkOut),
						additionalOptions));
	}

	@Given("Partial update booking payload with {string} {string}")
	public void partialUpdatePayloadCreation(String firstName, String lastName) throws IOException, ParseException {
		req = given().spec(utl.requestSpecification()).body(data.partialUpdateABooking(firstName, lastName));
	}

	@When("user calls {string} endpoint with {string} http request")
	public void triggerHTTPRequest(String resource, String method) {

		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("post"))
			response = req.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("get"))
			response = req.when().get(resourceAPI.getResource() + bookingId);
		else if (method.equalsIgnoreCase("getAll"))
			response = req.when().get(resourceAPI.getResource());
		if (method.equalsIgnoreCase("patch"))
			response = req.when().patch(resourceAPI.getResource() + bookingId);
		else if (method.equalsIgnoreCase("delete"))
			response = req.when().get(resourceAPI.getResource() + bookingId);

	}

	@Then("the API call responds with status code {int}")
	public void verifyAPIStatusCode(int statusCode) {

		assertEquals(response.getStatusCode(), statusCode);

	}

	@Then("bookingid is generated in response body")
	public void getBookingIDAndAssert() {

		bookingId = response.as(Booking.class).getBookingid();
		assertTrue(bookingId.length() > 0);

	}

	@Then("{string} {string} {string} {string} {string} {string} {string} is displayed in the booking payload")
	public void assertCreateBookingResponseBody(String firstName, String lastName, String totalPrice,
			String depositPaid, String checkIn, String checkOut, String additionalOptions) throws ParseException {
		assertEquals(response.as(Booking.class).getBooking().getFirstName(), firstName);
		assertEquals(response.as(Booking.class).getBooking().getLastName(), lastName);
		assertEquals(response.as(Booking.class).getBooking().getTotalPrice(), Integer.parseInt(totalPrice));
		assertEquals(response.as(Booking.class).getBooking().isDepositPaid(), Boolean.parseBoolean(depositPaid));
		assertEquals(response.as(Booking.class).getBooking().getBookingDates().getCheckIn(), utl.assignDate(checkIn));
		assertEquals(response.as(Booking.class).getBooking().getBookingDates().getCheckOut(), utl.assignDate(checkOut));
		assertEquals(response.as(Booking.class).getBooking().getAdditionalNeeds(), additionalOptions);

	}

	@Then("verify if get bookingbyid returns the booking details {string} {string} {string} {string} {string} {string} {string}")
	public void assertGetBookingByIDResponseBody(String firstName, String lastName, String totalPrice,
			String depositPaid, String checkIn, String checkOut, String additionalOptions)
			throws IOException, ParseException {

		triggerHTTPRequest("booking", "GET");

		assertEquals(response.as(CreateBooking.class).getFirstName(), firstName);
		assertEquals(response.as(CreateBooking.class).getLastName(), lastName);
		assertEquals(response.as(CreateBooking.class).getTotalPrice(), Integer.parseInt(totalPrice));
		assertEquals(response.as(CreateBooking.class).isDepositPaid(), Boolean.parseBoolean(depositPaid));
		assertEquals(response.as(CreateBooking.class).getBookingDates().getCheckIn(), utl.assignDate(checkIn));
		assertEquals(response.as(CreateBooking.class).getBookingDates().getCheckOut(), utl.assignDate(checkOut));
		assertEquals(response.as(CreateBooking.class).getAdditionalNeeds(), additionalOptions);

	}

	@Then("verify if getAllBookingById has the newly created bookingid")
	public void assertBookingIDExistenceInGetAllBookings() throws IOException, ParseException {

		assertTrue(getBookingIDsList().toString().contains(bookingId));
	}

	@Then("verify if getAllBookingById does not have the deleted bookingid")
	public void assertBookingIDNonExistenceInGetAllBookings() throws IOException, ParseException {
		
		assertTrue(!getBookingIDsList().toString().contains(bookingId));
	}

	@After("@CreateBooking and @PartialUpdateBooking")
	public void cleanCreatedBookings() {
		if (bookingId != null) {
			triggerHTTPRequest("booking", "DELETE");
		}

	}

}
