import java.lang.Math;

//Class to store personal, identifying information about each customer
public class CustomerInfo {
	private String name;
	private int age;
	private String postcode;
	private String phoneNmb;
	
	//Constructors for this class
	//Default Constructor
	public CustomerInfo() {
		this.name = "John Doe";
		//Generating a random age for the default class
		this.age = (1 + (int)(Math.random() * ((100 - 1) + 1)));
		this.postcode = "AA1 1AA";
		this.phoneNmb = CustomerInfo.randomPhoneNmb();
	}
	
	//Fully customisable constructor
	public CustomerInfo(String name, int age, String postcode, String phoneNmb) {
		this.name = name;
		this.age = age;
		this.postcode = postcode;
		this.phoneNmb = phoneNmb;
	}
	
	//Printing out all of the information from the class
	public String toString() {
		return "Name :" + this.name + ", Age : " + this.age + ", Postcode : " + this.postcode + ", Phone Number : " + this.phoneNmb;
	}
	
	//Function to generate a random phoneNmb
	//This is used for the ease of the user, to easily be able to input phoneNmbs
	public static String randomPhoneNmb() {
		//Options of all of the numbers that the phoneNmb can be made of
		String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
		String phoneNmb = "";
		//Looping through to select 11 different numbers
		for (int i=1; i<=11; i++) {
			//Generating a random number
			int randomIndex = (0 + (int)(Math.random() * ((8 - 0) + 1)));
			//Adding the item at the randomIndex to the phoneNmb output
			phoneNmb += numbers[randomIndex];		
		}
		return phoneNmb;
	}

	//Getter and Setter methods for the variables
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPhoneNmb() {
		return phoneNmb;
	}

	public void setPhoneNmb(String phoneNmb) {
		this.phoneNmb = phoneNmb;
	}	
}
