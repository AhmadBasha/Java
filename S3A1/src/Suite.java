//Ahmed Basha A Altowairqi
// S3644668

public class Suite extends BaseRoom
{
	// all the values 
	private static final int SUITE_SIZE = 6;
	private static final int pricePerDay = 999;
	private static final int latePricePerDay = 1099;
	private DateTime lastMaintenanceDate;

	// constructor 
	public Suite(String roomId, String summary, DateTime lastMaintenanceDate)
	{
		super(roomId, SUITE_SIZE, summary);
		this.lastMaintenanceDate = lastMaintenanceDate;
	}
	
	/*
	 *  taking three parameters and checking the statues if can be rented or not 
	 *  for the if condition is about that suites must have a maintenance interval of 10 days.
	 *  then it goes to addHiringRecord method .
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

		if (DateTime.diffDays(rentDate, this.lastMaintenanceDate) > (10 - numOfRentDay))
		{
			System.out.println("INFO: Maintainance due in specified period.");
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
		
		this.lastMaintenanceDate = completionDate;
		this.roomStatus = Status.AVAILABLE;
		
		return true;
	}
	
	/*
	 * checking the status of the room if not rented so it goes to if condition 
	 * then check if the return before the rent date or not 
	 * then goes to updateRecord method to pass three values and in the end changing the status 
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
		if (daysStay < 0)
		{
			System.out.println("INFO: You can't checkout before the rent date!");
			return false;
		}

		this.record.get(0).updateRecord(returnDate, Suite.pricePerDay, Suite.latePricePerDay);
		this.roomStatus = Status.AVAILABLE;
		return true;
	}
	
	/*
	 * i think is so clear 
	 * @see BaseRoom#toString()
	 */

	@Override
	public String toString()
	{
		String returnStr = this.roomId + ":" + this.noBedrooms + ":Suite:" + this.roomStatus + ":"
				+ this.lastMaintenanceDate + ":" + this.summary;
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
				+ "\nTyep:\t\t\tSuite\nStatus:\t\t\t" + this.roomStatus + "\nLast maintenance date:\t"
				+ this.lastMaintenanceDate + "\nFeature Summary:\t" + this.summary + "\nRENTAL RECORD";
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
