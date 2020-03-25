//Ahmed Basha A Altowairqi
// S3644668
import java.util.*;

public class CityLodge
{
    // here creating a list object of BaseRoom and sc for the user input.
	public ArrayList<BaseRoom> rooms = new ArrayList<>();
	private static Scanner sc;
    // this method if to check the room if exist or not 
    // and checking the room id and compare it with other for renting purpose
	private int getRoomIndex(String inputId)
	{
		if (rooms.isEmpty())
		{
			return -1;
		}

		int index = -1; // -1 means doesn't exist.
		for (int i = 0; i < rooms.size(); i++)
		{
			if (inputId.compareTo(rooms.get(i).getRoomId()) == 0)
			{
				index = i; // room found at index i
				break;
			}
		}
		return index;
	}
	
	// this method to split the date and return the date 

	private DateTime getDateFormat(String inputDate)
	{
		String dateList[] = inputDate.split("/");
		if (dateList.length != 3)
		{
			System.out.println("Invalid Date entered!");
			return null;
		}
		int day = Integer.parseInt(dateList[0]);
		int month = Integer.parseInt(dateList[1]);
		int year = Integer.parseInt(dateList[2]);

		DateTime date = new DateTime(day, month, year);
		return date;
	}

	// here the main method of the code 
	public void main(String[] args)
	{
		/* i gave a value which for sc object and make two more values option is for the
		input of the menu object that i made */ 
		sc = new Scanner(System.in);


		int option;
		String menu[] = {

				"\n**** CityLodge SYSTEM MENU ****", "\n (1) Add Room", "\n (2) Rent Room", "\n (3) Return Room",
				"\n (4) Room Maintenance", "\n (5) Complete Maintenance", "\n (6) Display All Rooms",
				"\n (7) Exit Program", "\n\n\tEnter your choice : " };
		// here a do loop and switch that have in each case a method that related with the
		// operation of the user need (( from the option value that the user input )).
		do
		{
			for (int i = 0; i < menu.length; i++)
				System.out.print(menu[i]);
			option = Integer.parseInt(sc.nextLine());
			switch (option)
			{
			case 1:
				addRoom();
				break;
			case 2:
				rentRoom();
				break;
			case 3:
				returnRoom();
				break;
			case 4:
				performMaintainance();
				break;
			case 5:
				completeMaintainance();
				break;
			case 6:
				dispRooms();
				break;
			case 7:
				break;
			default:
				System.out.println("Invalid choice!");
			}
		} while (option != 7);
		
		System.out.println("Thank you to use CityLodge program ");
	}
	
	/*
	 * Here for the fifth option . if the room does not exist so will go to if condition 
	 * because suite needs for the last maintenance date so users need to input the date if the room  is suite 
	 * then checking the date in getDateFormat() method . then it will go to completeMaintenance() method that 
	 * is Suite class to check if can be under Maintenance or not 
	 */

	private void completeMaintainance()
	{
		System.out.print("\n Enter Room ID : ");
		String inputId = sc.nextLine();
		int index = getRoomIndex(inputId);
		if (index == -1) // still -1, means that room wasn't found
		{
			System.out.println("Room " + inputId + " doesn't exist");
			return;
		}
		DateTime completionDate = null;
		if (inputId.charAt(0) == 'S')
		{
			System.out.print("Enter completion Date (dd/mm/yyyy) : ");
			String cDate = sc.nextLine();

			completionDate = getDateFormat(cDate);
			if(completionDate == null)
			{
				return;
			}

		}

		if (rooms.get(index).completeMaintenance(completionDate))
		{
			System.out.println("Room " + inputId + " is now Available.");
		} else
		{
			System.out.println("Room " + inputId + " is not under Maintenance.");
		}
	}
	/*
	 * Here for the Room Maintenance option and if the condition for if the room exist or not 
	 * and the others if condition to see if can change to maintenance or not . 
	 */

	private void performMaintainance()
	{
		System.out.print("\n Enter Room ID : ");
		String inputId = sc.nextLine();
		int index = getRoomIndex(inputId);
		if (index == -1) // still -1, means that room wasn't found
		{
			System.out.println("Room " + inputId + " doesn't exist");
			return;
		}

		if (rooms.get(index).performMaintenance())
		{
			System.out.println("Room " + inputId + " is now under maintainance.");
		} else
		{
			System.out.println("Room " + inputId + " can not be put under maintainance.");
		}
	}
	
	/*
	 * this method for return the room . checking the room id if the user entered and wrong room id 
	 * if the room not exist so the if condition will be printed . if available the user need to continue . 
	 * the user need to enter the date and the date will go to getDateFormat() method  to check the formating . 
	 */

	private void returnRoom()
	{
		System.out.print("\n Enter Room ID : ");
		String inputId = sc.nextLine();
		int index = getRoomIndex(inputId);
		if (index == -1) // still -1, means that room wasn't found
		{
			System.out.println("Room " + inputId + " doesn't exist");
			return;
		}

		System.out.print("Return Date (dd/mm/yyyy) : ");
		String rDate = sc.nextLine();
		DateTime returnDate = getDateFormat(rDate);
		if( returnDate == null)
		{
			return;
		}
        // this if condition , will go to one of the extended classes of returnRoom() method . to return the room 
        // and will print the room from toString() method and printLastRecord() .
		
		if (rooms.get(index).returnRoom(returnDate))
		{
			System.out.println("Room returned successfully");
			System.out.println(rooms.get(index));
			System.out.println(rooms.get(index).printLastRecord());
		} else
		{
			System.out.println("Room not returned");
		}

	}
	
	/*
	 * This is rent method for renting the room , after the user entering the room id , the value
	 *  will check in getRoomIndex() method 
	 * and will return the room id if exist or not ... 
	 * all of the previous step i have wrote for checking the room id is available to rent or not 
	 * if not available the code will go for if condition with index= -1 , 
	 * if available the user need to continue entering the next steps 
	 * 
	 */

	private void rentRoom()
	{
		System.out.print("\n Enter Room ID : ");
		String inputId = sc.nextLine();
		int index = getRoomIndex(inputId);
		if (index == -1) // still -1, means that room wasn't found
		{
			System.out.println("Room " + inputId + " doesn't exist");
			return;
		}

		System.out.print("Customer ID : ");
		String custId = sc.nextLine();

		System.out.print("Rent Date (dd/mm/yyyy) : ");
		String rDate = sc.nextLine();
		DateTime rentalDate = getDateFormat(rDate);
		if(rentalDate == null)
		{
			return;
		}

		System.out.print("How many days? : ");
		int numDays = Integer.parseInt(sc.nextLine());

		// Polymorphic call to rent() depend on what the class of the object is.
		// Late binding implementation.
		if (rooms.get(index).rent(custId, rentalDate, numDays) == false)
		{
			System.out.println("Sorry Room can't be rented for the specified time.!");
		} else
		{
			System.out.println("Room " + rooms.get(index).getRoomId() + " is now rented by customer " + custId);
		}
	}

	/* here adding room method to create a new room 
	 * at the begging the user need to  enter the room id and then for loop will compare
	 * the room is with other to make sure if exist or not
	 * the feature summary should be under 20 
	 * if the user write R at the first for standard room so he need to enter how many beds by if condition
	 * and the room will add successfully if the user choose suite so he need to enter the Maintenance date 
	 * and then the date formating will check on getDateFormat() method 
	 * because the value of maintainanceDate object is a new value of  getDateFormat() method 
	 * then the room will be successfully added
	 */
	public void addRoom()
	{
		System.out.println("**** CityLodge ****");
		System.out.print("\n Add Room ID : ");
		String inputId = sc.nextLine();
		for (int i = 0; i < rooms.size(); i++)
		{
			if (inputId.compareTo(rooms.get(i).getRoomId()) == 0)
			{
				System.out.println("Room Id : " + inputId + " Already exists !");
				return;
			}
		}

		System.out.print("\n Write a Feature summary of the room : ");
		String summary = sc.nextLine();
		System.out.println(summary);

		if (summary.length() > 20)
		{
			System.out.print("\n The summary of the room is maximum 20 letters \n ");
			return;
		}

		if (inputId.charAt(0) == 'R')
		{
			System.out.print("\n Enter number of Bedrooms : ");
			int num = Integer.parseInt(sc.nextLine());
			if (!(num == 1 || num == 2 || num == 4))
			{
				System.out.print("\n The number of room should be 1,2 or 4 \n ");
				return;
			}

			StdRoom temp = new StdRoom(inputId, num, summary);
			rooms.add(temp);
			System.out.println("Room added successfully!");

		} else if (inputId.charAt(0) == 'S')
		{
			System.out.print("Maintenance Date (dd/mm/yyyy) : ");
			String mDate = sc.nextLine();
			DateTime maintainanceDate = getDateFormat(mDate);
			if (maintainanceDate == null )
			{
				return;
			}
			Suite temp = new Suite(inputId, summary, maintainanceDate);
			rooms.add(temp);
			System.out.println("Suite added successfully!");
		} else
		{
			System.out.print(" There something wrong .. The roomn ID should start with capital 'R' or 'S' ");
		}
	}
	
	// this method to display the rooms 

	public void dispRooms()
	{
		if (rooms.isEmpty())
		{
			System.out.println("No rooms to display!");
		} else
		{
			for (int i = 0; i < rooms.size(); i++)
			{
				// rooms.get(i) calls the toString() function by default
				System.out.println(rooms.get(i).getDetails()
						+ ((i == rooms.size() - 1) ? "" : "\n======================================"));
			}
		}
	}
}
