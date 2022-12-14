package resources;

import pojo.CreateBooking;
import pojo.PartialUpdateBooking;
import pojo.BookingDates;

public class TestDataBuild {
	
	public CreateBooking createABooking(String firstname, String lastName,int totalPrice,boolean depositPaid,String checkIn,String checkOut,String additionalOptions)
	{
		CreateBooking createBookingPayload =new CreateBooking();
		BookingDates bookingDate =new BookingDates();
		bookingDate.setCheckIn(checkIn);
		bookingDate.setCheckOut(checkOut);	
		
		createBookingPayload.setFirstName(firstname);
		createBookingPayload.setLastName(lastName);
		createBookingPayload.setDepositPaid(depositPaid);
		createBookingPayload.setTotalPrice(totalPrice);
		createBookingPayload.setAdditionalNeeds(additionalOptions);	
		createBookingPayload.setBookingDates(bookingDate);
		return createBookingPayload;
	}
	public PartialUpdateBooking partialUpdateABooking(String firstname, String lastName)
	{
		PartialUpdateBooking partialUpdateBookingPayload =new PartialUpdateBooking();	

		partialUpdateBookingPayload.setFirstName(firstname);
		partialUpdateBookingPayload.setLastName(lastName);
		
		return partialUpdateBookingPayload;
	}

}
