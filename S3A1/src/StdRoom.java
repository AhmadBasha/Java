//Ahmed Basha A Altowairqi
// S3644668
public class StdRoom extends BaseRoom
{
	// all the values 
	private static final double pricePerDay1 = 59.0;
	private static final double pricePerDay2 = 99.0;
	private static final double pricePerDay4 = 199.0;
	private static final double latePricePercentage = 1.350;
	
    // constructor 
	public StdRoom(String roomId, int noBedrooms, String summary)
	{
		super(roomId, noBedrooms, summary);
	}
	
	// calculating the minimum days and for weekend condition 

	private int calcMinDays(DateTime rDate)
	{
		String day = rDate.getNameOfDay();
		int minDays = 2;
		switch (day)
		{
		case "Saturday":
		case "Sunday":
			minDays = 3;
			break;
		}
		return minDays;
	}
	
	/*
	 * this method for renting by passing three parameters and checking the status of the room 
	 * if available to rent or not  and checking the minimum days by calcMinDays() method 
	 * Also , checking if the days are more than 10 or not and then will pass the values to add HiringRecord () method
	 * 
	 * 
	 * @see BaseRoom#rent(java.lang.String, DateTime, int)
	 */

	@Override
	public boolean rent(String customerId, DateTime rentDate, int numOfRentDay)
	{
		if (this.roomStatus != Status.AVAILABLE)
		{
			System.out.println("INFO: Room " + this.roomId + " is not available at the moment.");
			return false;
		}
		int minDays = this.calcMinDays(rentDate);
		if (numOfRentDay < minDays)
		{
			System.out.println("INFO: Min days is : " + minDays + " you sent : " + numOfRentDay);
			return false;
		} else if (numOfRentDay >= 10)
		{
			System.out.println("INFO: Max days is : " + 10 + " you sent : " + numOfRentDay);
			return false;
		}

		return this.addHiringRecord(customerId, rentDate, numOfRentDay);
	}

	/*
	 * i think is so clear 
	 * @see BaseRoom#completeMaintenance(DateTime)
	 */
	@Override
	public boolean completeMaintenance(DateTime completionDate)
	{
		if (this.roomStatus != Status.MAINTENANCE)
		{
			System.out.println("INFO: Room is currently NOT Under Maintainance.");
			return false;
		}

		this.roomStatus = Status.AVAILABLE;

		return true;
	}
	
	/*
	 * Checking the status of the room if rented to return or not .
	 * and make sure for how many days the visitor have stayed with looking as well of the 
	 * weekend condition .. after that , checking how many best for calculating to update the hire record 
	 * The last step change the status to available 
	 * 
	 * @see BaseRoom#returnRoom(DateTime)
	 */

	@Override
	public boolean returnRoom(DateTime returnDate)
	{
		if (this.roomStatus != Status.RENTED)
		{
			System.out.println("INFO: Room " + this.roomId + " is currently not rented.");
			return false;
		}

		DateTime lastRentDate = this.record.get(0).getRentDate();
		
		int daysStay = DateTime.diffDays(returnDate, lastRentDate);
		int minDays = this.calcMinDays(lastRentDate);
		
		if(daysStay < 0)
		{
			System.out.println("INFO: You can't checkout before the rent date!");
			return false;
		}
		else if (daysStay < minDays)
		{
			System.out.println("INFO: Min days is : " + minDays + " You can't checkout yet.");
			return false;
		}

		double rate = 0;
		switch (this.noBedrooms)
		{
		case 1:
			rate = StdRoom.pricePerDay1;
			break;
		case 2:
			rate = StdRoom.pricePerDay2;
			break;
		case 4:
			rate = StdRoom.pricePerDay4;
		}
		double lateRate = rate * StdRoom.latePricePercentage;
		this.record.get(0).updateRecord(returnDate, rate, lateRate);
		this.roomStatus = Status.AVAILABLE;
		return true;
	}
	
	/*i think is so clear 
	 * 
	 * @see BaseRoom#toString()
	 */

	@Override
	public String toString()
	{
		String returnStr = this.roomId + ":" + this.noBedrooms + ":Standard:" + this.roomStatus + ":" + this.summary;
		return returnStr;
	}
	
	/* here when the user choose option 6 from the main , it should show what below 
	 * if there is no rental record so will print empty if not so will check in getDetails() in HiringRecord class 
	 * and return the value 
	 * 
	 * @see BaseRoom#getDetails()
	 */

	@Override
	public String getDetails()
	{
		String returnStr = "Room ID:\t\t" + this.roomId + "\nNumber of bedrooms:\t" + this.noBedrooms
				+ "\nTyep:\t\t\tStandard\nStatus:\t\t\t" + this.roomStatus + "\nFeature Summary:\t" + this.summary
				+ "\nRENTAL RECORD";
		if (this.record.isEmpty())
		{
			returnStr += ":\t\tempty";
		} else
		{
			for (int i = 0; i < this.record.size(); ++i)
			{
				returnStr += ("\n" + this.record.get(i).getDetails()
						+ ((i == this.record.size() - 1) ? "" : "\n--------------------------------------"));
			}
		}
		return returnStr;
	}

}
