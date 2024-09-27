import java.util.ArrayList;
import java.util.Scanner;

public class Customer implements CheckerPrinter{
	//Making a global scanner object to accept inputs
	public static Scanner input = new Scanner(System.in);
	
	//Creating the basic attributes for this class
	private String customerId;
	private float customerIncome;
	private boolean eligibilityStatus;
	//Including a customerInformation object, to keep personal information about the customer
	//This will make it easier to be able to find details about the customer, and help find the customer without the need of CustomerId
	//It will also make sure that the same person can't make two accounts with the Bank
	private CustomerInfo customerInformation;
	//Creating an arrayList to store all of the customers records
	private ArrayList<Loan> customerRecords = new ArrayList<Loan>();
	//Creating the additional attributes implemented
	private static String defaultCustomerId = "AAA001";
	private static ArrayList<String> usedCustomerIds = new ArrayList<String>();
	//Creating an arrayList to store all of the customers in the program
	public static ArrayList<Customer> customers = new ArrayList<Customer>();
	
	//Fully default constructor
	public Customer() {
		//Generating a defaultCustomerId to add
		defaultCustomerId = Customer.uniqueCustomerIdValidator(defaultCustomerId);
		customerId = defaultCustomerId;
		usedCustomerIds.add(customerId); //Adding the customerId to the used array
		//Setting the other attributes for the class
		customerInformation = new CustomerInfo();
		customerIncome = 50;
		//Working out if the user is eligible or not
		eligibilityStatus = this.eligibilityChecker();
	}
	
	//Constructor only needing the name and customerIncome input
	public Customer(CustomerInfo customerInformation, float customerIncome) {
		//Generating a defaultCustomerId to add
		defaultCustomerId = Customer.uniqueCustomerIdValidator(defaultCustomerId);
		customerId = defaultCustomerId;
		usedCustomerIds.add(customerId); //Adding the customerId to the used array
		//Setting the other attributes for the class
		this.customerInformation = customerInformation;
		this.customerIncome = customerIncome;
		//Working out if the user is eligible or not
		eligibilityStatus = this.eligibilityChecker();
	}
	
	//Fully Customisable class
	//NOTE : eligibilityStatus is validated, to ensure it is valid, in the main program
	public Customer(String customerId, CustomerInfo customerInformation, float customerIncome) {
		this.customerId = customerId;
		usedCustomerIds.add(customerId);
		this.customerInformation = customerInformation;
		this.customerIncome = customerIncome;
		this.eligibilityStatus = this.eligibilityChecker();
	}
	
	//Implementing the functions needed from the interface
	
	//Function to print out all of the information from a customer object
	public void printCustomerDetails() {
		//Printing customer general details
		System.out.println("CustomerId :" + this.getCustomerId());
		System.out.println("Customer Name : " + this.getCustomerInformation().getName());
		System.out.println("Customer Age : " + this.getCustomerInformation().getAge());
		System.out.println("Customer Postcode : " + this.getCustomerInformation().getPostcode());
		System.out.println("Customer Phone Number : " + this.getCustomerInformation().getPhoneNmb());
		System.out.println();
		System.out.println("Amount of Loans : " + this.loanNmb());
		System.out.println("Total Loan Amount :" + this.totalAmount());
		System.out.println("Eligible to create new loans : " + this.isEligibilityStatus());
		System.out.println();
		//Getting the customer records of the current customer
		ArrayList<Loan> customerRecords = this.getCustomerRecords();
		//If the customer loan records are empty, let user know		
		if (customerRecords.size() == 0) {
			System.out.println("Customer currently has no existing loans");
		}
		else {
			
			System.out.print("RecordID     LoanType      IntRate       Overpayment   AmountLeft    TimeLeft");
			//Printing the details of every record
			for (Loan loan : customerRecords) {
				//NOTE : This can't be toString because of the special formatting that is needed in order to keep it in the table
				Loan.printDetails(loan);
			}
		}
		System.out.println();
		System.out.println("=======================================================================");
	}
	
	//Implementing the functions for the eligibility check
	//Applying the formula to see whether customer eligible or not
	public boolean eligibilityChecker() {
		return this.totalAmount() < 4*this.customerIncome ;
	}
	
	//Function to see whether a customer is eligible for the new loan or not
	public boolean loanEligibilityCheck(float amount) {
		//If the new amount means the total loans exceed the eligibility, return false
		if (this.totalAmount() + amount >= 4*this.customerIncome) {
			return false;
		}
		//Else, return true
		return true;
	}
	
	//Function to update the customer's eligibility status, after every added loan
	public void updateEligibility() {
		this.eligibilityStatus = eligibilityChecker();
	}
	
	//Working out the total amount that the customer has to pay
	public float totalAmount() {
		float totalAmount = 0;
		for (Loan loan : customerRecords) {
			//Adding the amount left that each loan has
			totalAmount += loan.getAmntLeft();
		}
		return totalAmount;
	}
	
	//Returning the number of loans in the customerRecord array
	public int loanNmb() {
		return customerRecords.size();
	}
	
	//Functions used to make a loan in the customerRecords array
	
	//Functions to use the information that the user has given to make a new loan for the given customer they use
	//Using polymorphism so that they all have the same function name but have different functionalities
	public void makeLoan(String loanType) {
		//Making a loan variable to store the new loan to be made
		Loan newLoan;
		//Making the correct type of loan, depending on what the user has entered
		switch (loanType){
			case "Auto":
				newLoan = new AutoLoan();
				break;
			case "Builder":
				newLoan = new BuilderLoan();
				break;
			case "Mortgage":
				newLoan = new MortgageLoan();
				break;
			case "Personal":
				newLoan = new PersonalLoan();
				break;
			case "Other":
				newLoan = new OtherLoan();
				break;
			default:
				//This code will never be reached because of the validation of the loanType variable, but it needed
				newLoan = new AutoLoan();
				System.out.println("An unexpected error has occurred");
			}
		//Adding the newLoan made to the customers loan records
		this.addCustomerRecord(newLoan);
		System.out.println();
		System.out.println("Loan Record has been successfully made and added to the customer");
		System.out.println();
	}
		
	public void makeLoan(String loanType, float intRate, float amntLeft, float loanTermLeft) {
		//Making a variable to store the overpayment option, available for builder and mortgage
		float overpayment = 0;
		//Getting the overpayment from builder and mortgage loans
		if (loanType.equals("Builder") || loanType.equals("Mortgage")) {
			overpayment = XYZBank.getOverpayment();
		}
		//Making a loan variable to store the new loan to be made
		Loan newLoan;
		//Making the correct type of loan, depending on what the user has entered
		switch (loanType){
			case "Auto":
				newLoan = new AutoLoan(intRate,amntLeft,loanTermLeft);
				break;
			case "Builder":
				newLoan = new BuilderLoan(intRate,amntLeft,loanTermLeft,overpayment);
				break;
			case "Mortgage":
				newLoan = new MortgageLoan(intRate,amntLeft,loanTermLeft,overpayment);
				break;
			case "Personal":
				newLoan = new PersonalLoan(intRate,amntLeft,loanTermLeft);
				break;
			case "Other":
				newLoan = new OtherLoan(intRate,amntLeft,loanTermLeft);
				break;
			default:
				//This code will never be reached because of the validation of the loanType variable, but it needed
				newLoan = new AutoLoan();
				System.out.println("An unexpected error has occurred");
			}
		//Adding the newLoan made to the customers loan records
		this.addCustomerRecord(newLoan);
		System.out.println("Loan Record has been successfully made and added to the customer");
	}
		
	public void makeLoan(String recordId,String loanType, float intRate, float amntLeft, float loanTermLeft) {
		//Making a variable to store the overpayment option, available for builder and mortgage
		float overpayment = 0;
		//Getting the overpayment from builder and mortgage loans
		if (loanType.equals("Builder") || loanType.equals("Mortgage")) {
			overpayment = XYZBank.getOverpayment();
		}
		//Making a loan variable to store the new loan to be made
		Loan newLoan;
		//Making the correct type of loan, depending on what the user has entered
		switch (loanType){
			case "Auto":
				newLoan = new AutoLoan(recordId,intRate,amntLeft,loanTermLeft);
				break;
			case "Builder":
				newLoan = new BuilderLoan(recordId,intRate,amntLeft,loanTermLeft,overpayment);
				break;
			case "Mortgage":
				newLoan = new MortgageLoan(recordId,intRate,amntLeft,loanTermLeft,overpayment);
				break;
			case "Personal":
				newLoan = new PersonalLoan(recordId,intRate,amntLeft,loanTermLeft);
				break;
			case "Other":
				newLoan = new OtherLoan(recordId,intRate,amntLeft,loanTermLeft);
				break;
			default:
				//This code will never be reached because of the validation of the loanType variable, but it needed
				newLoan = new AutoLoan();
				System.out.println("An unexpected error has occurred");
			}
		//Adding the newLoan made to the customers loan records
		this.addCustomerRecord(newLoan);
		System.out.println("Loan Record has been successfully made and added to the customer");
	}
	
	//Applying other methods needed for the class
	
	//Function to ensure that the customerId going to be used is unique
	public static String uniqueCustomerIdValidator(String customerId) {
		//Looping through all of the used customerIds
		for (String usedCustomerId : usedCustomerIds) {
			//Testing to see if the usedId is equal to the id trying to be used
			if (usedCustomerId.equals(customerId)) {
				//If so, increment the id and then recursively call the validator, until a unique id is found
				incrementCustomerId();
				customerId = uniqueCustomerIdValidator(defaultCustomerId);
			}
		}
		//Returning the unique id that you have found
		return customerId;
	}
	
	//Incrementing the customerId
	public static String incrementCustomerId() {
		//Making an char array of all the characters in the id
		char[] characters = defaultCustomerId.substring(0,3).toCharArray();
		//Incrementing the number in the customerId by 1
		int customerIdNmb = Integer.parseInt(defaultCustomerId.substring(3)) + 1;
		//Testing to see whether the letters in the customerId has to be incremented
		//This will happen when the number reaches 1000, so the letter needs to be incremented and number reset to 1
		if (customerIdNmb >= 1000) {
			customerIdNmb = 1;
			//Creating test variable to see whether a change to the id has been made or not
			boolean updatedId = false;
			//Looping through all the characters in the id
			//Looping through backwards as you want to change the last character in the id first
			for (int i=2; i>=0;i--) {
				//If the character is Z, that character can't be updated, so you move to the next character in the array
				if (characters[i] == 'Z'){
					continue;
				}
				//Incrementing the character by 1, setting it back to the array and notifying a change has been made
				int charAscii = (int) characters[i];
				characters[i] = (char)(charAscii+1);
				updatedId = true;
				break;
			}
			//If no changes are made, error occurs and output to user
			//This is because it would mean that there are no unique id's that can be made, and therefore a change to the system must be made
			if (updatedId == false) {
				System.out.println("The system has ran out of possible customerIds to implement");
				defaultCustomerId = "-1"; //Returning the error value for the customerId
				return defaultCustomerId;
			}			
		}
		//Making the new customerId, with the changes that have been made
		//A stringBuilder has been used to be able to add the different characters and formats together, which is then converted back to a regular string
		defaultCustomerId = new StringBuilder().append(characters[0]).append(characters[1]).append(characters[2]).append(String.format("%0" + 3 + "d",customerIdNmb)).toString();
		return defaultCustomerId;
	}

	//Getter and Setter methods for the needed attributes within the class
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public float getCustomerIncome() {
		return customerIncome;
	}

	public void setCustomerIncome(float customerIncome) {
		this.customerIncome = customerIncome;
	}

	public boolean isEligibilityStatus() {
		return eligibilityStatus;
	}

	public void setEligibilityStatus(boolean eligibilityStatus) {
		this.eligibilityStatus = eligibilityStatus;
	}

	//Function to return the loan records of the customer to the main program
	public ArrayList<Loan> getCustomerRecords() {
		return customerRecords;
	}
	
	//Function to allow you to add a loan to the loan records
	public void addCustomerRecord(Loan loan) {
		this.customerRecords.add(loan);
		//Updating the eligibility of the customer, after record added
		updateEligibility();
	}
	
	//Allowing the customer to delete a record, given a recordId
	public void deleteRecord(Loan loanRecord) {
		System.out.println();
		System.out.println("Enterring the delete record interface...");
		//Making sure the user wants to delete the record
		String choice;
		//Repeating the code until the user has a valid response
		while (true) {
			System.out.println("You are able to delete record " + loanRecord.getRecordId());
			System.out.print("Are you sure you want to do this? (yes or no) : ");
			try {
				choice = input.next().toLowerCase();
				//Ensuring the user has entered a valid input
				if (!choice.equals("yes") && !choice.equals("no")) {
					throw new InvalidInput("Choice input must either be yes or no");
				}
				break;
			}
			//Dealing with any errors that could have occurred
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again...");
				input.next();
			}
			catch (Exception inputMismatch) {
				System.out.println("Choice input must be of type String");
				input.next();
			}
		}
		//If the user enters no, exit out of the function
		if (choice.equals("no")) {
			return;
		}
		//Else, delete the record from the customerRecords (and remove it from usedRecordIds)
		System.out.println();
		System.out.println("Removing the loanRecord from the customers records");
		this.customerRecords.remove(loanRecord);
		this.usedCustomerIds.remove(loanRecord.getRecordId());
		//Updating the eligibility of the customer, after record removed
		updateEligibility();
		}
	
	//Function to find the number of loans that a customer has
	public int numberOfLoans() {
		return this.customerRecords.size();
	}
	
	public void setCustomerInfo(String name, int age, String postcode, String phoneNmb) {
		this.customerInformation = new CustomerInfo(name,age,postcode,phoneNmb);
	}
	
	public CustomerInfo getCustomerInformation() {
		return this.customerInformation;
	}
	
	//Functions to get and add a customer to the static customers array
	public static void addCustomer(Customer customer) {
		customers.add(customer);
	}
	
	public static ArrayList<Customer> getCustomersArray(){
		return customers;
	}
	
	//Finding out how many customers have been made
	public static int numberOfCustomers() {
		return customers.size();
	}
}
