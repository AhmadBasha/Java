//Ahmed Basha A Altowairqi
// S3644668
import java.util.*;

public abstract class BaseRoom
{
	// all the variables 
	String roomId;
	int noBedrooms;
	String summary;
	protected Status roomStatus;
	protected ArrayList<HiringRecord> record = new ArrayList<>();
	
	// constructor 

	public BaseRoom(String roomId, int noBedrooms, String summary)
	{
		this.roomId = roomId;
		this.noBedrooms = noBedrooms;
		this.summary = summary;

		this.roomStatus = Status.AVAILABLE;
	}

	// this method to change the status of the room to rented . 
	// the first condition for checking the rent date with number of days 
	// and making a new object of HiringRecord class which have all the parameters values 
	// then , check the size of the record if 10 or not to save 
	protected boolean addHiringRecord(String custId, DateTime rDate, int numDays)
	{

		for (int i = 0; i < this.record.size(); ++i)
		{
			if (!this.record.get(i).testDate(rDate, numDays))
			{
				System.out.println("INFO: Date entered colides with previous bookings.");
				return false;
			}
		}
		String recordId = this.roomId + "_" + custId + "_" + rDate.getEightDigitDate();

		HiringRecord temp = new HiringRecord(recordId, rDate, numDays);

		if (this.record.size() == 10)
		{
			this.record.remove(record.size() - 1);
		}
		this.record.add(0, temp);
		this.roomStatus = Status.RENTED;
		return true;
	}

	// for the room id 
	public String getRoomId()
	{
		return this.roomId;
	}
	
	// all the method that need for the sub classes

	public abstract boolean rent(String customerId, DateTime rentDate, int numOfRentDay);

	public abstract boolean completeMaintenance(DateTime completionDate);

	public abstract boolean returnRoom(DateTime returnDate);
	public abstract String toString() ;
	
	public abstract String getDetails();
	
	// check the status 
	public boolean performMaintenance()
	{
		if (this.roomStatus != Status.AVAILABLE)
		{
			System.out.println("INFO: Room is currently Occupied or Under Maintainance.");
			return false;
		}

		this.roomStatus = Status.MAINTENANCE;

		return true;
	}
	// check the status 
	public boolean isAvailable()
	{
		if (this.roomStatus != Status.AVAILABLE)
		{
			return false;
		}
		return true;
	}
	// for the last record 

	public HiringRecord printLastRecord()
	{
		return (this.record.get(0));
	}
}
