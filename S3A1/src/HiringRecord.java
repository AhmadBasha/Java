//Ahmed Basha A Altowairqi
// S3644668
public class HiringRecord
{
	private String recordId;
	private DateTime rentDate;
	private DateTime estimateDate;
	private DateTime actualReturnDate;
	private double rentalFee;
	private double lateFee;
	// getter methods 
	public DateTime getRentDate()
	{
		return this.rentDate;
	}
	public DateTime getEstimatedDate()
	{
		return this.estimateDate;
	}
	// constructor 
	public HiringRecord(String recordId, DateTime rDate, int numDays)
	{
		this.recordId = recordId;
		this.rentDate = rDate;
		this.estimateDate = new DateTime(rDate, numDays);

		this.rentalFee = -1.0;


	}

	public String toString()
	{
		// recordId:rentDate:estimatedReturnDate:actualReturnDate:rentalFee:lateFee
		// ( Condition ) ? (True assignment) : (false assignment);
		String returnStr = this.recordId + ":" + this.rentDate + ":" + this.estimateDate + ":"
				+ ((this.actualReturnDate == null) ? "none:none:none"
						: (this.actualReturnDate + ":" + String.format("%.2f", this.rentalFee) + ":" + String.format("%.2f", this.lateFee)));
		
		return returnStr;
	}

	public String getDetails()
	{
		/* Minimizing string concatenation and reference operations by creating string
		 * in one statement */

		String returnStr = "Record ID:\t\t" + this.recordId + "\nRentDate:\t\t" + this.rentDate
				+ "\nEstimated Return Date:\t" + this.estimateDate
				+ ((this.actualReturnDate == null) ? ""
						: "\nActual Return Date:\t" + this.actualReturnDate + "\nRental Fee:\t\t" + String.format("%.2f", this.rentalFee)
								+ "\nLate Fee:\t\t" + String.format("%.2f", this.lateFee));
		return returnStr;
	}

	public boolean testDate(DateTime testDateR, int numDays)
	{
		// Estimated date for new booking.
		DateTime testDateE = new DateTime(testDateR, numDays);

		// Both test dates are before the current rent date
		if (DateTime.diffDays(this.rentDate, testDateR) > 0 && DateTime.diffDays(this.rentDate, testDateE) >= 0)
		{
			return true;
		}
		// Both test dates are after the current estimated date
		else if (DateTime.diffDays(this.actualReturnDate, testDateR) <= 0
				&& DateTime.diffDays(this.actualReturnDate, testDateE) < 0)
		{
			return true;
		}
		return false;
	}
	
	// updating the record 
	
	public void updateRecord(DateTime returnDate, double rate, double lateRate)
	{
		
		// checking for how many days by the different between return and rent 
		// also , to know the extra days .
		int daysStay = DateTime.diffDays(returnDate, this.rentDate);
		int extraDays = DateTime.diffDays(returnDate, this.estimateDate);
		
		// ( Condition ) ? (True assignment) : (false assignment);
		extraDays = extraDays > 0 ? extraDays : 0;
		daysStay -= extraDays;
		this.rentalFee = rate * daysStay;
		this.lateFee = lateRate * extraDays;
		this.actualReturnDate = returnDate;
	}

}


