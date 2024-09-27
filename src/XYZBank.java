import java.util.Scanner;
import java.util.ArrayList;

public class XYZBank {
	//Making a global scanner, so it can be used in any function
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		//Looping until the user chooses to exit out of the program
		boolean keepGoing = true;
		while (keepGoing) {
			//Outputting the possible choices that the user could make...
			System.out.println();
			System.out.println("What would you like to do ? ");
			System.out.println("1. Register a new Customer");
			System.out.println("2. Register a new Loan for a Customer");
			System.out.println("3. Manage an exisiting Customer's loans");
			System.out.println("4. Updating information about an existing Customer");
			System.out.println("5. Check the eligibility of an existing Customer");
			System.out.println("6. Print loan information");
			System.out.println("7. Exit the Program");
			System.out.print("Enter your choice (1 to 7) : ");
			int choice = 0;
			try {
				//Receiving the input from the user
				choice = input.nextInt();
				//Ensuring the input is in the wanted range
				if (choice < 1 || choice >7) {
					throw new InvalidInput("Choice must be between the numbers 1 and 7");
				}				
			}
			//Calling an exception if the input is out of range
			catch(InvalidInput err) {
				System.out.println(err + ", Please try again...");
				continue;
			}
			//Calling an exception if the input is of the wrong type
			catch(Exception inputMismatch) {
				System.out.println("Choice input must be of type integer, Please try again...");
				input.next();
				continue;
			}
			//Transferring the user to the correct function, depending on what input they have enterred
			switch (choice) {
				case 1 :
					System.out.println("Transferring you to the Register Customer interface...");
					registerCustomer();
					break;
				case 2 :
					System.out.println("Transferring you to the Loan Maker interface...");
					loanMaker();
					break;
				case 3 :
					System.out.println("Transferring you to the Loan Management interface...");
					loanManagement();
					break;
				case 4 : 
					System.out.println("Transferring you to the Update Customer Information interface...");
					System.out.println("NOTE : You will not be able to update any customer loan records in this interface");
					System.out.print("Is this alright? (yes,no) :  ");
					String response = input.next().toLowerCase();
					if (response.equals("yes")) {
						System.out.println("Transferring you to the Update Customer Information interface...");
						updateCustomerInfo();
					}
					else {
						System.out.println("Sorry for the inconvienience, returning you to the main program..."); 
					}
					break;
				case 5 : 
					System.out.println("Transferring you to the Eligibilty Checker interface...");
					eligibilityChecker();
					break;
				case 6 : 
					System.out.println("Transferring you to the Loans Information interface...");
					loanInformation();
					break;
				//If the user wanted to exit the program, make the validation variable false, to leave the while loop
				case 7 : 
					System.out.println("Exiting the program...");
					keepGoing = false;
					break;
				//Default while loop just incase something does go wrong
				default:
					System.out.println("An unexpected error has occurred, please try again...");
			}
		}
		input.close();
	}
	
	//Function to allow user to register a new customer in the program 
	public static void registerCustomer() {
		System.out.println();
		System.out.println("Welcome to registerCustomer");	
		//Finding what method the user would like to do to make their new customer
		//Creating a validation variable, so code repeats until correct input recieved
		boolean validatedChoice = false;
		//Creating a variable to store the users choice
		int choice = 0;
		//Looping through until the user has a valid choice
		while (!validatedChoice) {
			//Displaying the possible options to the user
			System.out.println();
			System.out.println("How would you like your new customer to be created ? ");
			System.out.println("1. Fully default customer");
			System.out.println("2. Customised customer, excluding CustomerId");
			System.out.println("3. Fully customised customer");
			System.out.println("4. Exit out of registerCustomer interface");
			System.out.print("Enter your choice (1-4) : ");
			try {
				choice = input.nextInt();
				//Ensuring that the choice is in the correct range
				if (choice < 1 || choice > 4) {
					throw new InvalidInput("Choice input must be between 1 and 4");
				}
				validatedChoice = true;
			}
			//Dealing with any errors that may have occurred, outputting message
			catch (InvalidInput err) {
				System.out.println(err + ",Please try again");
				validatedChoice = false;
			}
			catch (Exception inputMismatch) {
				input.next();
				System.out.println("Input must be of type int, please try again");
			}
		}
		System.out.println();
		//Outputting the correct code, depending on what choice the user has made
		//Making a default customer and customerInfo object, to be used later
		CustomerInfo customerInformation;
		int customerIncome;
		Customer newCustomer;
		switch (choice) {
			case 1:
				System.out.println("Making the new default customer");
				//Making the default customer
				newCustomer = new Customer();
				break;
			case 2: 
				System.out.println("Helping you make your customised customer");
				//Getting all of the customer's information from the user
				customerInformation = getCustomerInformation();
				//Getting the customer's income from the user
				customerIncome = getCustomerIncome();
				//Making the customer object
				System.out.println("Now, making the customer object");
				newCustomer = new Customer(customerInformation,customerIncome);
				break;
			case 3:
				System.out.println("Helping you make your fully customised customer...");
				//Calling the function to get and validate the customer id from the user
				//NOTE : The false identifies that the customerId made does have to be unique
				String customerId = getCustomerId(false);
				//Getting all of the customer's information from the user
				customerInformation = getCustomerInformation();
				//Getting (and validating) the customer's income from the user
				customerIncome = getCustomerIncome();
				//Making the customer object
				System.out.println("Now, making the customer object");
				newCustomer = new Customer(customerId,customerInformation,customerIncome);
				break;
			case 4:
				System.out.println("Exiting the create customer interface...");
				return;
			//Dealing with any errors that may occur during this switch case
			default:
				//NOTE : The validation on choice means you'll never reach this, but you still need a default
				newCustomer = new Customer();
				System.out.println("An unexpected error has occurred, returning to the main program...");
		}
		//Ensuring that the customers information hasn't previously been used
		//This is used to ensure that a customer is unable to make multiple accounts with the same bank
		String duplicatedId = validateUniqueInfo(newCustomer);
		//If the duplicatedId isn't false, don't add the customer and return to main program 
		if (!duplicatedId.equals("false")) {
			System.out.println("Error : Newly created customers information matches an existing customer. CustomerId : " + duplicatedId);
			System.out.println("New customer is no longer created, returning to main program...");
			return;
		}
		//If the information isn't duplicated, add the customer
		//Adding the customer object to the customer arrayList
		Customer.addCustomer(newCustomer);
		System.out.println("New customer has been added to the system");
	}
		
	//Allowing the user to make and add loans to Customers
	//NOTE : This is included separately to the customerInformation interface as I felt this is one of the main uses of the program
	public static void loanMaker() {
		System.out.println();
		System.out.println("Welcome to the loanMaker interface");
		//If there are no existing customers in the system, leave the loanMaker
		if (Customer.numberOfCustomers() == 0) {
			System.out.println("There are no existing customers in the system, so no loans can be made");
			System.out.println("Returning you to the main program...");
			return;
		}
		//Finding the customer that the user would like to make a loan for 
		System.out.println("Firstly, lets find the customer that you would like to make a loan for");
		Customer customer = findCustomer();
		System.out.println();
		System.out.println("Customer successfully found");
		System.out.println();
		//Finding out what method the user would like to use to make the loan
		int choice = -1;
		//Repeating until the user enters a valid choice
		while (choice < 0) {
			//Listing out the possible options for the user to choose
			System.out.println("What loan record would you like to make? ");
			System.out.println("1. A default loan record, with customisable loanType");
			System.out.println("2. A customised loan record, requiring all attributes except RecordId");
			System.out.println("3. A fully customised loan record, including RecordId");
			System.out.println("4. Exit back to the main program");
			System.out.print("Enter your choice (1-4) : ");
			try {
				choice = input.nextInt();
				//Ensuring that the input is in the correct range
				if (choice < 1 || choice > 4) {
					throw new InvalidInput("Choice input must be between 1 and 5");
				}
			}
			//Dealing with any errors that could occur
			catch (InvalidInput err){
				System.out.println(err + ", Please try again");
			}
			catch (Exception InputMismatch) {
				System.out.println("Choice input must be of type INT, Please try again");
			}
		}
		//Implementing the correct code, depending on what input the user has used
		//Making the variables to store the users inputs into
		String loanType;
		float intRate;
		float amntLeft;
		float loanTermLeft;
		boolean eligibilityCheck;
		switch (choice) {
			case 1:
				loanType = getLoanType();
				//Ensuring that the user passes the eligibility check, even with the new loan added
				eligibilityCheck = customer.loanEligibilityCheck(10);
				//If they don't pass the eligibility, return to main program
				if (!eligibilityCheck) {
					System.out.println("Unable to make loan as customer fails eligiblity check");
					System.out.println("Returning back to the main program...");
					return;
				}
				//Making the new loan
				customer.makeLoan(loanType);
				break;
			case 2:
				loanType =  getLoanType();
				intRate = getIntRate();
				amntLeft = getAmntLeft();
				//Ensuring that the user passes the eligibility check, even with the new loan added
				eligibilityCheck = customer.loanEligibilityCheck(amntLeft);
				//If they don't pass the eligibility, return to main program
				if (!eligibilityCheck) {
					System.out.println("Unable to make loan as customer fails eligiblity check");
					System.out.println("Returning back to the main program...");
					return;
				}
				loanTermLeft = getLoanTermLeft();
				//Making the new loan
				customer.makeLoan(loanType,intRate,amntLeft,loanTermLeft);
				break;
			case 3:
				String recordId = getRecordId(false);
				loanType =  getLoanType();
				intRate = getIntRate();
				amntLeft = getAmntLeft();
				//Ensuring that the user passes the eligibility check, even with the new loan added
				eligibilityCheck = customer.loanEligibilityCheck(amntLeft);
				//If they don't pass the eligibility, return to main program
				if (!eligibilityCheck) {
					System.out.println("Unable to make loan as customer fails eligiblity check");
					System.out.println("Returning back to the main program...");
					return;
				}
				loanTermLeft = getLoanTermLeft();
				//Making the new loan
				customer.makeLoan(recordId,loanType,intRate,amntLeft,loanTermLeft);
				break;
			case 4:
				System.out.println("Exiting back to the main program");
				break;
			default:
				System.out.println("An unexpected error has occurred");
		}
	}
	
	//Function to allow the user to manage (pay off and delete) loan records from customers
	public static void loanManagement() {
		System.out.println();
		System.out.println("Welcome to the loanManagement interface");
		//If there are no existing customers in the system, leave the loanMaker
		if (Customer.numberOfCustomers() == 0) {
			System.out.println("There are no existing customers in the system, so no loans can be made");
			System.out.println("Returning you to the main program...");
			return;
		}
		//Finding the customer that the user would like to make a loan for 
		System.out.println("Firstly, lets find the customer that you would like to manage loans for");
		Customer customer = findCustomer();
		System.out.println();
		System.out.println("Customer successfully found");
		System.out.println();
		//Looping until the user enters that they want to leave
		while (true) {
			//Identifying which action the user would like to make
			int choice = -1;
			//Repeating until the user has made a valid choice
			while (choice < 0) {
				System.out.println();
				System.out.println("How would you like to manage the loan records of customer " + customer.getCustomerId() + "? :");
				System.out.println("1. Print out the customer's loan records");
				System.out.println("2. Pay off an amount of a loan record");
				System.out.println("3. Delete a loan record");
				System.out.println("4. Return back to the main program");
				System.out.print("Enter your choice (1-4) : ");
				try {
					choice = input.nextInt();
					//Ensuring the choice input is in the correct range
					if (choice < 1 || choice > 4) {
						throw new InvalidInput("Choice input must be between 1 and 4");
					}
				}
				//Dealing with any errors that could occur
				catch (InvalidInput err) {
					System.out.println(err + ", Please try again...");
				}
				catch (Exception inputMismatch) {
					System.out.println("Choice input must be of type INT, Please try again...");
				}
			}
			//Implementing the correct code, depending on what the user selects
			switch (choice) {
			case 1 :
				System.out.println("Printing the customers loan records...");
				System.out.println();
				System.out.println("=======================================================================");
				//Printing out the information of the chosen customer
				customer.printCustomerDetails();
				break;
			case 2 : 
				System.out.println("Transferring you to the loan payment interface...");
				loanPayment(customer);
				break;
			case 3 : 
				System.out.println("Transferring you to the loan deletion interface...");
				loanDeletion(customer);
				break;
			case 4 : 
				System.out.println("Exiting out of the loan management interface...");
				return;
			default:
				System.out.println("An unexpected error has occurred, return to main menu...");
				break;
			}
		}
	}
	
	//Function to allow the user to be able to pay off a loan record
	public static void loanPayment(Customer customer) {
		System.out.println();
		System.out.println("Welcome to the Loan Payment Interface");
		//Finding the loan record in which the user would like to pay off
		//If there are no existing customers in the system, leave loanPayment
		if (customer.numberOfLoans() == 0) {
			System.out.println("There are no existing loan in the system for this customer, so no loans are able to be paid off");
			System.out.println("Returning you to the main program...");
			return;
		}
		//Finding the loan record which they would like to pay off
		Loan loanRecord = findLoan(customer);
		//Finding out how much they would like to pay off
		//NOTE : This function includes validation to ensure you can't pay off more than the amntLeft
		System.out.println();
		float amount = findAmountToPay(loanRecord.getAmntLeft());
		System.out.println();
		//Paying of the amount that the user has entered
		System.out.println("Paying off " + amount + " from loan record " + loanRecord.getRecordId() + "...");
		loanRecord.payOff(amount);
		System.out.println("Payment Successful");
		//Updating the eligibility of the customer, after the amount has been paid off
		customer.updateEligibility();
		//Checking to see whether they have paid off the entire loan, and asking whether they would like to delete it
		if (loanRecord.getAmntLeft() == 0) {
			String choice;
			while (true) {
				System.out.println();
				System.out.println("Loan record " + loanRecord.getRecordId() + " has been entirely paid off");
				System.out.print("Would you like to delete it? (yes or no) : ");
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
			if (choice.equals("yes")) {
				customer.deleteRecord(loanRecord);
			}
		}
	}
	
	//Finding out how much money the user would like to pay off from the loan record
	public static float findAmountToPay(float amountLeft) {
		float amount = -1;
		//Repeating until the user has entered a valid amount
		while (amount < 0) {
			System.out.println("Amount left to pay :" + amountLeft);
			System.out.print("Enter the amount that you would like to pay off : ");
			try {
				amount = input.nextFloat();
				//Ensuring that the amount being paid isn't negative
				if (amount < 0) {
					throw new InvalidInput("You must pay off an amount >=0");
				}
				//Ensuring they aren't paying off more than actual amount left
				else if (amount > amountLeft) {
					throw new InvalidInput("You are unable to pay off more than the amount left of the loan");
				}
			}
			//Dealing with any error that has occurred
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again...");
				input.nextLine();
			}
			catch (Exception inputMismatch){
				System.out.println("Amount input must of type FLOAT, please try again");
				input.nextLine();
			}
		}
		return amount;
	}
	
	//Function to allow the user to be able to delete a loan record
	public static void loanDeletion(Customer customer) {
		System.out.println();
		System.out.println("Welcome to the Loan Payment Interface");
		//Finding the loan record in which the user would like to pay off
		//If there are no existing customers in the system, leave loanPayment
		if (customer.numberOfLoans() == 0) {
			System.out.println("There are no existing loan in the system for this customer, so no loans are able to be paid off");
			System.out.println("Returning you to the main program...");
			return;
		}
		//Finding the loan record which they would like to pay off
		Loan loanRecord = findLoan(customer);
		//Deleting the loan record from the customer
		customer.deleteRecord(loanRecord);
	}
	
	//Function to allow the user to find a customer and update some of their information
	public static void updateCustomerInfo() {
		System.out.println();
		System.out.println("Welcome to updateCustomerInfo");
		//If there are no existing customers in the system, leave the loanMaker
		if (Customer.numberOfCustomers() == 0) {
			System.out.println("There are no existing customers in the system, so no customer's information is able to be changed");
			System.out.println("Returning you to the main program...");
			return;
		}
		//Finding the customer that the user would like to make a loan for 
		System.out.println("Firstly, lets find the customer that you would like to make a loan for");
		Customer customer = findCustomer();
		System.out.println();
		System.out.println("Customer successfully found");
		System.out.println();
		//Finding out what information the user would like to update
		int choice = -1;
		//Looping until a valid choice
		while (choice < 0) {
			System.out.println("What customer information would you like to update? : ");
			System.out.println("1. Customer's Income");
			System.out.println("2. Customer's Name");
			System.out.println("3. Customer's Age");
			System.out.println("4. Customer's Postcode");
			System.out.println("5. Customer's Phone Number");
			System.out.print("Enter your choice : ");
			try {
				choice = input.nextInt();
				//Ensuring that the choice input is in the correct range
				if (choice < 1 || choice > 5) {
					throw new InvalidInput("Choice must be between 1 and 5");
				}
			}
			//Dealing with any errors that could occur
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again...");
				input.next();
			}
			catch (Exception InputMismatch) {
				System.out.println("Choice input must be of type INT, Please try again");
			}
		}
		//Implementing the correct code, depending on what the user has entered
		switch (choice) {
			case 1:
				System.out.println("Interface to change the Customer's Income...");
				int customerIncome = getCustomerIncome();
				customer.setCustomerIncome(customerIncome);
				System.out.println("Successfully changed Customer Income");
				break;
			case 2 : 
				System.out.println("Interface to change the Name...");
				String name = getName();
				customer.getCustomerInformation().setName(name);
				System.out.println("Successfully changed Name");
				break;
			case 3:
				System.out.println("Interface to change the Age...");
				int age = getAge();
				customer.getCustomerInformation().setAge(age);
				System.out.println("Successfully changed Age");
				break;
			case 4:
				System.out.println("Interface to change the Postcode...");
				String postcode = getPostcode();
				customer.getCustomerInformation().setPostcode(postcode);
				System.out.println("Successfully changed Postcode");
				break;
			case 5:
				System.out.println("Interface to change the Customer's Phone Number...");
				String phoneNmb = getPhoneNmb();
				customer.getCustomerInformation().setPhoneNmb(phoneNmb);
				System.out.println("Successfully changed Phone Number");
				break;
			//Default input to clean up any errors
			default:
				System.out.println("An unexpected error has occurred, returning to main program");
		}
	}
	
	//Allowing the user to be able to check the eligibility of a customer
	public static void eligibilityChecker() {
		System.out.println();
		System.out.println("Welcome to eligibilityChecker");
		//If there are no existing customers in the system, leave the loanMaker
		if (Customer.numberOfCustomers() == 0) {
			System.out.println("There are no existing customers in the system, so no customer's eligibility can be checked");
			System.out.println("Returning you to the main program...");
			return;
		}
		//Finding the customer that the user would like to make a loan for 
		System.out.println("Firstly, lets find the customer that you would like to make a loan for");
		Customer customer = findCustomer();
		System.out.println();
		System.out.println("Customer successfully found");
		System.out.println();
		//Outputting the eligibility of the customer
		System.out.println("New loan eligibility of Customer " + customer.getCustomerId() + " : " + customer.isEligibilityStatus());
	}
	
	//Allowing the user to print the information of an individual customers or all customers loans
	public static void loanInformation() {
		System.out.println();
		System.out.println("Welcome to the loanInformation terminal");
		//Finding out how the user would like to print the loans
		//Looping until the user enters a valid choice
		int choice = -1;
		while (choice < 0) {
			System.out.println("How would you like to print the loans? ");
			System.out.println("1. Print a specific customer's loans");
			System.out.println("2. Print all customers loans");
			System.out.println("3. Exit back to main program");
			System.out.print("Enter your choice (1,2,3) : ");
			try {
				choice = input.nextInt();
				//Ensuring the choice is in the correct range
				if (choice < 1 || choice > 3) {
					throw new InvalidInput("Choice input must be either 1 or 2");
				}
			}
			//Dealing with any errors that may have occurred
			catch(InvalidInput err) {
				System.out.println(err + ", Please try again...");
				continue;
			}
			catch (Exception inputMismatch) {
				System.out.println("Choice input must be of type INT, Please try again...");
				continue;
			}
		}
		//Implementing the correct code, depending on what choice the user has made
		if (choice == 1) {
			System.out.println("Finding the customer that you would like to print the records of...");
			//Finding the customer that the user wants to print the info of
			Customer customer = findCustomer();
			System.out.println("=======================================================================");
			//Printing out the information of the chosen customer
			customer.printCustomerDetails();
		}
		if (choice == 2) {
			System.out.println("Printing the information of all customers...");
			System.out.println();
			//Printing general information about the customers and records that we store
			System.out.println("Number of customers : " + Customer.numberOfCustomers());
			//Printing the information of every customer
			ArrayList<Customer> customers = Customer.getCustomersArray();
			System.out.println("=======================================================================");
			for (Customer customer : customers) {
				customer.printCustomerDetails();
			}
		}	
	}
		
	//Function to find the customer that the user would like to use in the program
	public static Customer findCustomer() {
		//Making the default customer object to be returned, after changes made
		Customer customer;
		//Finding out what method the user would like to use to find the customer
		//Looping through until the user make a valid choice
		int choice = -1;
		while (choice <0) {
			System.out.println();
			System.out.println("How would you like to find your customer? ");
			System.out.println("1. Use customerId");
			System.out.println("2. Use customer's name");
			System.out.print("Enter your choice (1 or 2) : ");
			try {
				//Getting the input from the user
				choice = input.nextInt();
				//Ensuring that the input is in the correct range
				if (choice <1 || choice > 2) {
					throw new InvalidInput("Choice input must be either 1 or 2");
				}
			}
			//Dealing with any errors that could have occurred
			catch(InvalidInput err) {
				System.out.println(err + ", Please try again...");
				continue;
			}
			catch(Exception inputMismatch) {
				System.out.println("Choice input must be of type INT, Please try again");
				continue;
			}
		}
		//Implementing the correct choice, depending on what the user has entered
		System.out.println();
		if (choice == 1) {
			//Implementing the function to find the customer object by Id and print the details
			customer = findCustomerById();
		}
		else if (choice == 2) {
			//Implementing the function to find the customer object through the customers name
			customer = findCustomerByName();
		}
		//NOTE : This code is never reached, due to validation in the choice variable, however removes errors from the code
		else {
			customer = new Customer();
		}
		//Returning the customer to the parent function
		return customer;
	}
	
	//Function to find a customer object by the customersId
	public static Loan findLoan(Customer customer) {
		System.out.println("Finding your loan through their recordId...");
		//Looping until a loan has been successfully found
		while (true) {
			//Looping through until a valid choice is made
			int choice = -1;
			while (choice < 0) {
				System.out.println();
				//Seeing what choice the user would want to make
				System.out.println("What would you like to do? ");
				System.out.println("1. Enter the recordId to help find the loan record");
				//This helps the user identify what recordId it is that they need to enter
				System.out.println("2. Print the recordIds of all records in the system, to help identify a recordId");
				System.out.print("Enter your choice (1 or 2) : ");
				try {
					choice = input.nextInt();
					//Throwing an error if the input is out of the range of possible options
					if (choice < 1 || choice > 2) {
						throw new InvalidInput("Choice input must be either 1 or 2");
					}
				}
				//Dealing with any errors that could have occurred in the system
				catch (InvalidInput err) {
					System.out.println(err + ", Please try again");
					input.next();
				}
				catch (Exception inputMismatch) {
					System.out.println("Choice input must be of type INT, please try again");
					input.next();
				}
			}
			//Outputting the correct code, depending on what the user has enterred
			//If choice is 1, get the user to enter the recordId
			if (choice == 1) {				
				//Getting the recordId from the user
				//The true identifies that the received id doesn't have to be unique
				String recordId = getRecordId(true);
				//Getting the loan records from the customer object
				ArrayList<Loan> customerRecords = customer.getCustomerRecords();
				//Finding the customer with the customerId given
				for (Loan loan : customerRecords) {
					if (loan.getRecordId().equals(recordId)) {
						//If you have found the customer, return it
						return loan;
					}
				}
				System.out.println("RecordId entered doesn't match any existing loan record from this customer, Please try again");
			}
			//If choice is 2, print out all the recordsIds that the current customer has
			if (choice == 2) {
				//Getting the loan records from the customer object
				ArrayList<Loan> customerRecords = customer.getCustomerRecords();
				System.out.println();
				System.out.println("RecordIds : ");
				//Printing the id of every customer
				for (Loan loan : customerRecords) {
					System.out.println(loan.getRecordId());
				}
				System.out.println();
			}
		}
	}
	
	//Function to find a customer object by the customersId
	public static Customer findCustomerById() {
		System.out.println("Finding your customer through their customerId...");
		//Looping until a customer has been successfully found
		while (true) {
			//Looping through until a valid choice is made
			int choice = -1;
			while (choice < 0) {
				System.out.println();
				//Seeing what choice the user would want to make
				System.out.println("What would you like to do? ");
				System.out.println("1. Enter the customerId to help find the customer");
				//This helps the user identify what customerId it is that they need to enter
				System.out.println("2. Print the customerIds of all customer in the system, to help identify a customerId");
				System.out.print("Enter your choice (1 or 2) : ");
				try {
					choice = input.nextInt();
					//Throwing an error if the input is out of the range of possible options
					if (choice < 1 || choice > 2) {
						throw new InvalidInput("Choice input must be either 1 or 2");
					}
				}
				//Dealing with any errors that could have occurred in the system
				catch (InvalidInput err) {
					System.out.println(err + ", Please try again");
					input.next();
				}
				catch (Exception inputMismatch) {
					System.out.println("Choice input must be of type INT, please try again");
					input.next();
				}
			}
			//Outputting the correct code, depending on what the user has enterred
			//If choice is 1, get the user to enter the customerId
			if (choice == 1) {				
				//Getting the customerId from the user
				//The true identifies that the received id doesn't have to be unique
				String customerId = getCustomerId(true);
				//Getting the customer records from the Customer class
				ArrayList<Customer> customers = Customer.getCustomersArray();
				//Finding the customer with the customerId given
				for (Customer customer : customers) {
					if (customer.getCustomerId().equals(customerId)) {
						//If you have found the customer, return it
						return customer;
					}
				}
				System.out.println("CustomerId entered doesn't match any existing customers, Please try again");
			}
			//If choice is 2, print out all the customerIds in the system
			if (choice == 2) {
				//Getting the customer records from the Customer class
				ArrayList<Customer> customers = Customer.getCustomersArray();
				System.out.println();
				System.out.println("CustomerIds : ");
				//Printing the id of every customer
				for (Customer customer : customers) {
					System.out.println(customer.getCustomerId());
				}
				System.out.println();
			}
		}
	}
	
	//Function to find a customer object by the customer's name
	public static Customer findCustomerByName() {
		System.out.println("Finding the customer by their name");
		//Looping until a customer has been successfully found
		while (true) {
			//Looping through until a valid choice is made
			int choice = -1;
			while (choice < 0) {
				System.out.println();
				//Seeing what choice the user would want to make
				System.out.println("What would you like to do? ");
				System.out.println("1. Enter the customer name to help find the customer");
				//This helps the user identify what customerId it is that they need to enter
				System.out.println("2. Print the names of all customers in the system, to help identify the customer's name");
				System.out.print("Enter your choice (1 or 2) : ");
				try {
					choice = input.nextInt();
					//Throwing an error if the input is out of the range of possible options
					if (choice < 1 || choice > 2) {
						throw new InvalidInput("Choice input must be either 1 or 2");
					}
				}
				//Dealing with any errors that could have occurred in the system
				catch (InvalidInput err) {
					System.out.println(err + ", Please try again");
					input.next();
				}
				catch (Exception inputMismatch) {
					System.out.println("Choice input must be of type INT, please try again");
					input.next();
				}
			}
			System.out.println();
			//Outputting the correct code, depending on what the user has enterred
			//If choice is 1, get the user to enter the customer name
			if (choice == 1) {	
				//Getting the name of the customer from the user
				String name = getName();
				//Finding all the customers in the system with that name
				//The function will return the customer object of those people with the names
				ArrayList<Customer> sameNames = getSameNames(name);
				//Dealing with the 3 possible outcomes of the sameNames function
				//If there is noone with the name that the user entered, repeat code
				if (sameNames.size() == 0) {
					System.out.println("There are no customers with that name, Please try again");
				}
				//If there is only one person with that name, return that customer
				else if (sameNames.size() == 1) {
					return sameNames.get(0);
				}
				//If there are multiple people with the same name, help the user identify which customer they want
				else {
					System.out.println("There are multiple customers with that name, please identify which customer you mean");
					//This function will loop through all of the customers, help the user identify which customer they want to choose, and return it
					return verifiedCustomerObject(sameNames);
				}
			}
			//If choice is 2, print out all the customerIds in the system
			if (choice == 2) {
				//Getting the customer records from the Customer class
				ArrayList<Customer> customers = Customer.getCustomersArray();
				System.out.println("Customer Names : ");
				//Printing the id of every customer
				for (Customer customer : customers) {
					System.out.println(customer.getCustomerInformation().getName());
				}
			}
		}
	}
	
	//Function which takes a given name and returns an arrayList of all the people in the system with that name
	public static ArrayList<Customer> getSameNames(String name){
		//Making the arrayList to be returned later
		ArrayList<Customer> sameNames = new ArrayList<Customer>();
		//Getting the customer records from the customer class
		ArrayList<Customer> customers = Customer.getCustomersArray();
		//Looping through every customer in the array
		for (Customer customer : customers) {
			//If the name is the same as the entered name, add them to the array
			if (customer.getCustomerInformation().getName().equals(name)) {
				sameNames.add(customer);
			}
		}
		//Returning the sameNames array to the parent function
		return sameNames;
	}
	
	//Function to help the user identify which customer object they want, by printing details of customers
	public static Customer verifiedCustomerObject(ArrayList<Customer> sameNames) {
		//Looping until the user has successfully identified the customer
		while (true) {
			//Printing out the details of the customers with the same name
			System.out.println();
			System.out.println("Printing out the details of the customers with the same name");
			System.out.println();
			System.out.println("=======================================================================");
			for (Customer sameCustomer : sameNames) {
				sameCustomer.printCustomerDetails();
			}
			//Getting the user to enter the customerId of the customer they want
			String customerId = getCustomerId(true);
			//Returning the customer object of the customer with that name
			for (Customer sameCustomer : sameNames) {
				if (customerId.equals(sameCustomer.getCustomerId())) {
					return sameCustomer;
				}
			}
			//If the customerId doesn't meet any of the customers, make the user repeat
			System.out.println();
			System.out.println("Entered customerId doesn't match any of the possible customers, Please try again");
		}
	}
	
	//Function to get and validate (to ensure it is unique) the customerId from the user
		public static String getCustomerId(boolean uniqueId) {
			String customerId;
			//Repeating the code below until the user enters a correct input
			do {
				System.out.print("Enter the customerId in the format AAADDD, where every A is a letter of the alphabet and every D is a digit between 0 and 9 : ");
				//Accepting the input from the user
				customerId = input.next().toUpperCase();
				//Converting the received input to characters
				char[] characters = customerId.toCharArray();
				//Ensuring that the length of the user's input is correct (the input is 6 characters long)
				if (characters.length != 6) {
					System.out.println("Wrong length of input received, please try again...");
					continue;
				}
				//Making a validation variable used later to see whether the id is validated or not
				boolean validatedId = true;
				//Looping through half of the array
				//This is because charAscii needs to access the first half of the array and intAscii needs to access the second half
				for (int i=0;i<=2;i++) {
					//Accessing the ascii of the characters and the integers that are supposed to be located within the customerId
					int charAscii = (int) characters[i];
					int intAscii = (int) characters[characters.length-i-1];
					//Testing the the charAscii value is the ascii of a character in the alphabet
					if (charAscii<65 || intAscii>90) {
						validatedId = false;
					}	
					//Testing that the intAscii value is the ascii of a digit 0-9
					if (intAscii<48 || intAscii>57) {
						validatedId = false;
					}
				}
				if (validatedId == false) {
					System.out.println("The format which you have enterred the customerId is wrong, please retry...");
					continue;
				}
				//If statement to see whether the customerId needs to be validated to be unique or not
				if (uniqueId == false) {
					//Ensuring that the recordId received is unique
					String uniqueCustomerId = Customer.uniqueCustomerIdValidator(customerId);
					//If the new id is the same as the old one, then no issues and program continues
					if (uniqueCustomerId.equals(customerId)) {
						uniqueId = true;
					}
					//If the id has already been used...
					else {
						System.out.println("CustomerId received is not unique, however " + uniqueCustomerId + " is...");
						System.out.print("Would you like to use the new customerId? : yes,no : ");
						String response = input.next().toLowerCase();
						if (response.equals("yes")) {
							customerId = uniqueCustomerId;
							uniqueId = true;
						}
						else {
							System.out.println("Returning, to allow you to input new customerId");
						}
					}
				}
				//If the user has accepted a uniqueId
				if (uniqueId) {
					break;
				}
			}while (true);
			//Returning the id back to the system
			return customerId;
		}
	
	//Function to get customerInformation and make the customerInfo object, to be used to make customers
	public static CustomerInfo getCustomerInformation() {
		System.out.println("Requesting customer information from the user... ");
		//Each input has their own functions to collect the information from
		//This is because different inputs require different validations and formats, and will return different error messages
		String name = getName();
		int age = getAge();
		String postcode = getPostcode();
		String phoneNmb = getPhoneNmb();
		//Making the customerInfo object
		CustomerInfo customerInformation = new CustomerInfo(name,age,postcode,phoneNmb);
		return customerInformation;
	}
	
	//Function to get and validate a customer's name
	public static String getName() {
		input.nextLine();
		String name = "";
		//Repeating until we have a set name
		while (name.equals("")) {
			System.out.println();
			System.out.print("Please enter the new customers name : ");
			String newName;
			try {
				newName = input.nextLine();
				//Ensuring that the new name only includes alphabetical characters or spaces
				//Separating the name string into characters
				char[] characters = newName.toLowerCase().toCharArray();
				//Looping through every character in the name
				for (char character : characters) {
					int charAscii = (int) character;
					//Testing the the charAscii value is the ascii of a character in the alphabet
					if ((charAscii<97 || charAscii>122) && charAscii !=32) {
						throw new InvalidInput("All characters in name must be alphabetical, or spaces");
					}
				}
			}
			catch(InvalidInput err) {
				System.out.println(err + ", Please try again...");
				continue;
			}
			//Setting the name to the validated name
			name = newName;
		}
		//Returning the name to the program
		System.out.println("Valid Name : " + name);
		return name;
	}
	
	//Function to get the customer's age from the user
	public static int getAge() {
		int age = -1;
		//Repeating until we have a set age
		while (age < 0) {
			System.out.println();
			System.out.print("Please enter the new customers age : ");
			int newAge;
			try {
				newAge = input.nextInt();
				//Ensuring that the age isn't negative
				if (newAge < 0) {
					throw new InvalidInput("Customer's age must be >= 0");
				}
			}
			catch(InvalidInput err) {
				System.out.println(err + ", Please try again...");
				//input.next();
				continue;
			}
			catch(Exception inputMismatch) {
				System.out.println("Age input must be of type INT, Please try again...");
				input.next();
				continue;
			}
			//Setting the age to the validated age
			age = newAge;
		}
		//Returning the age to the program
		System.out.println("Valid Age : " + age);
		return age;
	}
	
	//Function to get the customer's postcode from the user
	public static String getPostcode() {
		String postcode = "";
		input.nextLine();
		//Continuing while we don't have a set postcode
		while (postcode.equals("")) {
			System.out.println();
			System.out.println("Please enter the customer's postcode : ");
			System.out.println("Format : AAD DAA, where A is an alphabetical letter and D is a single digit number (0-9)");
			System.out.print("Enter your input : ");
			try {
				String newPostcode = input.nextLine().toUpperCase();
				//Ensuring the postcode is of the correct length
				if (newPostcode.length() != 7) {
					throw new InvalidInput("Postcode is of wrong length");
				}
				//Ensuring the individual characters are the correct, corresponding to the needed format
				//Finding the different substrings that need to be alphabetical characters
				String charactersToCheck1 = newPostcode.substring(0,2);
				String charactersToCheck2 = newPostcode.substring(5,7);
				//Appending the two arrays (making it easier to loop through all of them and check them)
				char[] charactersToCheck = appendArrays(charactersToCheck1, charactersToCheck2);
				//Getting the space character that needs to be checked
				String spaceCheck = newPostcode.substring(3,4);
				//Finding the different digits that need to be checked
				String digitToCheck1 = newPostcode.substring(2,3);
				String digitToCheck2 = newPostcode.substring(4,5);
				//Appending the two digits that need to be checked into 1 array
				char[] digitsToCheck = appendArrays(digitToCheck1,digitToCheck2);
				//Ensuring the different characters/spaces/digits that we have identified are of the correct type
				boolean validatedCharacters = checkCharacters(charactersToCheck);
				boolean validatedSpace = checkSpace(spaceCheck);
				boolean validatedDigits = checkDigits(digitsToCheck);
				//Ensuring that all the validation variables are true, and therefore the format is correct
				if (!validatedCharacters || !validatedSpace || !validatedDigits) {
					//If the format is incorrect, throw an Exception
					throw new InvalidInput("Postcode is of the incorrect format");
				}
				//Setting the postcode to the postcode that the user has enterred
				postcode = newPostcode;
			}
			//Returning error if the input is incorrect
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again...");
				continue;
			}
		}
		//Returning the postcode to the program
		System.out.println("Valid Postcode : " + postcode);
		return postcode;
	}
	
	//Function to get the customer's phonenmb from the user
	public static String getPhoneNmb() {
		String phoneNmb = "";
		//Looping until the user enters a valid choice
		int choice = -1;
		while (choice < 0) {
			System.out.println();
			System.out.println("How would you like to get the phone number? : ");
			System.out.println("1. Generate a random phone number");
			System.out.println("2. Enter your own phone number");
			System.out.print("Enter your choice (1 or 2) : ");
			try {
				choice = input.nextInt();
				//Ensuring that the choice is in the correct range
				if (choice < 1 || choice > 2) {
					throw new InvalidInput("Choice input must be either 1 or 2");
				}
			}
			//Dealing with any errors that could occur
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again");
				input.nextLine();
			}
			catch (Exception inputMismatch) {
				System.out.println("");
				input.nextLine();
			}
		} 
		//Implementing the correct code, depending on the users input
		if (choice == 1) {
			phoneNmb = CustomerInfo.randomPhoneNmb();
		}
		else {
			phoneNmb = getUserPhoneNmb();
		}
		return phoneNmb;
	}
	
	//Function to allow the user to input their own phoneNmb into the system
	public static String getUserPhoneNmb() {
		String phoneNmb = "";
		//Repeating until we have a set phoneNmb
		while (phoneNmb.equals("")) {
			System.out.println();
			System.out.println("Please enter the new customers phone number...");
			System.out.println("Format : DDDDDDDDDDD - 11 Digits (0-9)");
			System.out.print("Enter the phone number : ");
			String newPhoneNmb;
			try {
				newPhoneNmb = input.next();
				if (newPhoneNmb.length() != 11) {
					throw new InvalidInput("Phone number is of incorrect length");
				}
				//Creating a char[] array from the phoneNmb received
				char[] digits = newPhoneNmb.toCharArray();
				//Ensuring that every character in the phone number is a numerical digit
				boolean validatedDigits = checkDigits(digits);
				//If every character in the phone number isn't a digit, throw an error
				if (!validatedDigits) {
					throw new InvalidInput("All characters in a phone number must be a numerical digit");
				}
			}
			catch(InvalidInput err) {
				System.out.println(err + ", Please try again...");
				continue;
			}
			//Setting the phoneNmb to the validated phoneNmb
			phoneNmb = newPhoneNmb;
		}
		//Returning the phoneNmb to the program
		return phoneNmb;
	}
	
	//Function to get and validate the customer's income from the user
	public static int getCustomerIncome() {
		int customerIncome = -1;
		input.nextLine();
		while (customerIncome < 0) {
			System.out.println();
			System.out.print("Enter the customer's income (in thousands) : ");
			int newCustomerIncome;
			try {
				newCustomerIncome = input.nextInt();
				//Ensuring the customer's income isn't negative
				if (newCustomerIncome < 0) {
					throw new InvalidInput("A customer's income cannot be negative");
				}
			}
			//Dealing with any errors that could have occurred in the system
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again");
				continue;
			}
			catch (Exception err) {
				System.out.println("Customer income input is of the wrong type, please try again");
				continue;
			}
			//Setting the customerIncome to be the newCustomerIncome
			customerIncome = newCustomerIncome;
		}
		//Returning the customerIncome to the program
		System.out.println("Valid Customer Income : " + customerIncome);
		System.out.println();
		return customerIncome;
	}
	
	//Function used to append two arrays into a char[] array
	public static char[] appendArrays(String sub1, String sub2) {
		//Making the output array, of the length of the two substrings
		char[] characters = new char[sub1.length()+sub2.length()];
		//Making the two substrings into character arrays
		char[] chars1 = sub1.toCharArray();
		char[] chars2 = sub2.toCharArray();
		//Keeping track of where we are within the characters array
		int counter = 0;
		//Looping through the two substring arrays and adding the characters to the output array
		for (char character : chars1) {
			characters[counter] = character;
			//Appending the counter to ensure we know where we are
			counter++;
		}
		//Repeating the same steps as above
		for (char character : chars2) {
			characters[counter] = character;
			counter++;
		}
		//Returning the characters array to the program
		return characters;
	}
	
	//Function to check a char[] and ensure that every character is a alphabetical character
	public static boolean checkCharacters(char[] characters) {
		for (char character: characters) {
			//Accessing the ascii of the characters
			int charAscii = (int) character;
			//Testing the the charAscii value is the ascii of a character in the alphabet
			if (charAscii<65 || charAscii>90) {
				//If it isn't a character, return false
				return false;
			}	
		}
		//If every character is an alphabetical character, return true
		return true;
	}
	
	//Function to check a char[] and ensure that every character is a numerical digit
	public static boolean checkDigits(char[] characters) {
		for (char digit: characters) {
			int digitAscii = (int) digit;
			//Ensuring that the digit ascii is a digit
			if (digitAscii < 48 || digitAscii > 57) {
				//If it isn't a digit, return false
				return false;
			}
		}
		//if all characters are digits, return true
		return true;
	}
	
	//Function to check a string and ensure that it is a space characters
	public static boolean checkSpace(String space) {
		//If the string is a space, return true. Else, return false
		if (space.equals(" ")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Function to ensure that the customerInfo of every customer is in some way unique, and the same person hasn't made multiple accounts
	//If the customerInfo isn't unique, customerId is returned to help user identify the duplicated persons info
	public static String validateUniqueInfo(Customer newCustomer) {
		//Getting the customers array from the class
		ArrayList<Customer> customers = Customer.getCustomersArray();
		//Looping through every customer in the array
		for (Customer customer : customers) {
			//Ensuring that the customerInformation is not the same
			//The toString() function will return a string, containing all the customers information
			if (customer.getCustomerInformation().toString().equals(newCustomer.getCustomerInformation().toString())) {
				//If information equal, return the duplicated customers Id
				return customer.getCustomerId();
			}
		}
		//If information doesn't match, return false
		return "false";
	}
	
	
	//Function to get and validate the inputs needed to make records in the system
	//Collecting and validating the recordId from the user, ensuring correct format and unique
	public static String getRecordId(boolean uniqueId) {
		String recordId;
		//Repeating the code below until the user enters a correct input
		do {
			System.out.print("Enter the recordId in the format DDDDDD, where every D is a digit between 0 and 9 : ");
			//Accepting the input from the user
			recordId = input.next().toUpperCase();
			//Converting the received input to characters
			char[] characters = recordId.toCharArray();
			//Ensuring that the length of the user's input is correct (the input is 6 characters long)
			if (characters.length != 6) {
				System.out.println("Wrong length of input received, please try again...");
				continue;
			}
			//Testing whether every character can be converted to an integer
			//This will mean that the user has entered the correct format of input
			//Creating a boolean test variable to see whether correctFormat or not
			boolean correctFormat = true;
			for (char character : characters) {
				try {
					int charInt = Integer.parseInt(String.valueOf(character));
				}
				catch(Exception e){
					System.out.println("Incorrect format of input enterred, please retry...");
					correctFormat = false;
					break;
				}
			}
			//If the input isn't the correct format, continue to the next loop 
			if (correctFormat == false) {
				continue;
			}
			//Ensuring that the RecordId is unique, depending on what parameter is given to the function
			if (uniqueId == false) {
				//Ensuring that the recordId received is unique
				String uniqueRecordId = Loan.uniqueRecordIdValidator(recordId);
				//If the new id is the same as the old one, then no issues and program continues
				if (uniqueRecordId.equals(recordId)) {
					uniqueId = true;
				}
				//If the id has already been used...
				else {
					System.out.println("RecordId received is not unique, however " + uniqueRecordId + " is...");
					System.out.print("Would you like to use the new recordId? : yes,no : ");
					String response = input.next().toLowerCase();
					if (response.equals("yes")) {
						recordId = uniqueRecordId;
						uniqueId = true;
					}
					else {
						System.out.println("Returning, to allow you to input new recordId");
					}
				}
			}
			//Breaking out of the loop if ID is unique
			if (uniqueId == true) {
				break;
			}
		}while (true);
		//Returning the id back to the system
		return recordId;
	}
	//Collecting and validating the loanType variable from the user
	public static String getLoanType() {
		//Repeating the code below until the user enters a correct input
		do {
			System.out.print("Enter the loan type of the current record : Auto, Builder, Mortgage, Personal or Other : ");
			//Accepting the input from the user
			String loanType = input.next().toUpperCase();
			//Checking to see whether the correct input entered
			//This code also ensures that all loanTypes are the format, no matter what case the user types their input in
			switch (loanType) {
				case "AUTO" :
					return "Auto";
				case "BUILDER" :
					return "Builder";
				case "MORTGAGE":
					return "Mortgage";
				case "PERSONAL":
					return "Personal";
				case "OTHER":
					return "Other";
				default:
					//Default case for if the user enters a wrong input
					System.out.println("Incorrect loan type entered. Please try again...");
			}
		}while (true);
	}
	
	//Collecting and validating the IntRate variable from the user
	public static float getIntRate() {
		float intRate = -1;
		//Looping through until the variable inputed is positive
		while (intRate <0) {
			System.out.print("Enter the current record's interest rate (>=0) : ");
			//Ensuring that a number has been inputted (not a string)
			try {
				intRate = input.nextFloat();
				if (intRate < 0) {
					throw new InvalidInput("Interest rate must be >= 0");
				}
			}
			//Dealing with any errors that could occur
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again");
				input.next();
			}
			catch (Exception e) {
				//Error output for if a number has not been inputed
				System.out.println("Wrong input entered, please retry...");
				input.next();
			}
		}
		return intRate;
	}
	
	//Function to collect and validate the amntLeft variable from the user
	public static int getAmntLeft() {
		int amntLeft = -1;
		while (amntLeft <0) {
			System.out.print("Enter the remaining balance, in thousands, of the current record's loan (>=0) : ");
			//Ensuring that a number has been inputted (not a string)
			try {
				amntLeft = input.nextInt();
				if (amntLeft < 0) {
					throw new InvalidInput("Amount Left must be >= 0");
				}
			}
			//Dealing with any errors
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again...");
				input.next();
			}
			catch (Exception e) {
				//Error output for if a number has not been inputed
				System.out.println("Wrong input entered, please retry...");
				input.next();
			}
		}
		return amntLeft;
	}
	
	//Collecting and validating the loanLengthLeft variable from the user
	public static int getLoanTermLeft() {
		int loanTermLeft = -1;
		while (loanTermLeft <0) {
			System.out.print("How many years left does the current record's loan have? (>=0) : ");
			//Ensuring that a number has been inputted (not a string)
			try {
				loanTermLeft = input.nextInt();
				if (loanTermLeft < 0) {
					throw new InvalidInput("Loan Term Left must be >= 0");
				}
			}
			//Dealing with any errors that could occur
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again");
				input.next();
			}
			catch (Exception e) {
				//Error output for if a number has not been inputed
				System.out.println("Wrong input entered, please retry...");
				input.next();
			}
		}
		return loanTermLeft;
	}
	
	//Function to get and validate the overpayment attribute of loan records
	public static float getOverpayment() {
		float overpayment = -1;
		while (overpayment <0) {
			System.out.print("Enter the overpayment option in the loan (between 0 and 2) : ");
			//Ensuring that a number has been inputted (not a string)
			try {
				overpayment = input.nextFloat();
				//Ensuring that the overpayment option is in the correct range
				if (overpayment < 0 || overpayment > 2) {
					throw new InvalidInput("Overpayment attribute must be between 0 and 2");
				}
			}
			//Dealing with any errors that could occur in the program
			catch (InvalidInput err) {
				System.out.println(err + ", Please try again...");
			}
			catch (Exception e) {
				//Error output for if a number has not been inputed
				System.out.println("Wrong input entered, please retry...");
				input.next();
			}
		}
		return overpayment;
	}
}
