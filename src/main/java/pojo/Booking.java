package pojo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCaseStrategy.class)
public class Booking {
	
	private CreateBooking booking;
	private String bookingid;

	public String getBookingid() {
		return bookingid;
	}

	public void setBookingid(String bookingid) {
		this.bookingid = bookingid;
	}

	public CreateBooking getBooking() {
		return booking;
	}

	public void setBooking(CreateBooking booking) {
		this.booking = booking;
	}

}
