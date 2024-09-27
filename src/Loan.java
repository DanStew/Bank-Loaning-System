import java.util.ArrayList;

public abstract class Loan {
	//Creating the 4 basic attributes for the loan class
	private String recordId;
	private String loanType;
	private float intRate;
	private float amntLeft;
	private float loanTermLeft;
	//Creating a defaultRecordId label, so be used when setting a default recordId
	private static String defaultRecordId = "000001";
	//Making a list of usedRecordIds, to ensure all recordIds are unique
	private static ArrayList<String> usedRecordIds = new ArrayList<String>();
	
	//Creating the constructor methods
	//Basic constructor method, with no inputs
	public Loan() {
		//Setting default values for all of the instance variables in the class
		//Ensuring that the recordId going to be given is unique
		defaultRecordId = Loan.uniqueRecordIdValidator(defaultRecordId);
		recordId = defaultRecordId;
		//Adding the used id to the array, to be used for validation later
		usedRecordIds.add(recordId);
		//Initialising the other instance variables
		loanType = "Auto";
		intRate = (float) 5.0;
		amntLeft = 10;
		loanTermLeft = 2;
	}
	
	//Default Super Constructor for the subclasses
	//This is the constructor that will be used there as it allows you to input the LoanType into the constructor
	public Loan(String loanType) {
		//Setting default values for all of the instance variables in the class
		//Ensuring that the recordId going to be given is unique
		defaultRecordId = Loan.uniqueRecordIdValidator(defaultRecordId);
		recordId = defaultRecordId;
		//Adding the used id to the array, to be used for validation later
		usedRecordIds.add(recordId);
		//Initialising the other instance variables
		this.loanType = loanType;
		intRate = (float) 5.0;
		amntLeft = 10;
		loanTermLeft = 2;
	}
	
	//Constructor with all inputs, other than having a default recordId
	public Loan(String loanType,float intRate, float amntLeft,float loanTermLeft) {
		//Ensuring a unique default recordId
		defaultRecordId = Loan.uniqueRecordIdValidator(defaultRecordId);
		recordId = defaultRecordId;
		//Adding the used id to the array, to be used for validation later
		usedRecordIds.add(recordId);
		//Initialising the other instance variables
		this.loanType = loanType;
		this.intRate = intRate;
		this.amntLeft = amntLeft;
		this.loanTermLeft = loanTermLeft;
	}
	
	//Fully Customisable Constructor, with all inputs
	public Loan(String recordId, String loanType, float intRate, float amntLeft, float loanTermLeft) {
		//NOTE : The recordId is validated to be default in the XYZBank class, before moving to make the Loan Here
		this.recordId = recordId;
		//Adding the recordId to the usedIds
		usedRecordIds.add(recordId);
		this.loanType = loanType;
		this.intRate = intRate;
		this.amntLeft = amntLeft;
		this.loanTermLeft = loanTermLeft;
	}
	
	//Defining the abstract methods for this class
	//Functions to get and set the overpayment attribute
	//NOTE : Subclasses which don't have overpayment will return some error value instead
	public abstract void setOverpayment(float overpayment);
	public abstract String getOverpayment();
	
	//Function to print all of the details from the loan
	public static void printDetails(Loan loan) {
		System.out.println("");
		//Creating a length which all of the gaps in the outputted table will be
		int maxLength = 14;
		//Making an array of all of the items that need to be outputted to the user
		String[] outputtedItems = {loan.getRecordId(),loan.getLoanType(),
								   Float.toString(loan.getIntRate()),loan.getOverpayment(),
								   Float.toString(loan.getAmntLeft()),Float.toString(loan.getLoanTermLeft())};
		//Printing out each of the items needed, calculating the amount of spaces needed for the correct formatting
		for (String item : outputtedItems) {				
			System.out.print(item + " ".repeat(maxLength-item.length()));
		}
	}
	
	//Function used to ensure that the recordId going to be used is unique
	public static String uniqueRecordIdValidator(String recordId) {
		//Looping through all of the used recordIds
		for (String usedRecordId : usedRecordIds) {
			//Testing to see if the usedId is equal to the id trying to be used
			if (usedRecordId.equals(recordId)) {
				//If so, increment the id and then recursively call the validator, until a unique id is found
				recordId = incrementRecordId();
				recordId = uniqueRecordIdValidator(defaultRecordId);
			}
		}
		//Returning the unique id that you have found
		return recordId;
	}
		
	//Incrementing the defaultRecordId by 1
	public static String incrementRecordId() {
		//Incrementing the recordId by 1
		int recordIdNmb = Integer.parseInt(defaultRecordId) + 1;
		//Testing to see whether the recordId has surpassed 6 figures, and is now 7 figures and out of format
		if (recordIdNmb >= 1000000) {
			System.err.println("System has ran out of valid defaultRecordIds");
			defaultRecordId = "-1"; //Default error value for recordId
		}
		//Formatting the number to ensure that it is in the correct format
		defaultRecordId = String.format("%0" + 6 + "d",recordIdNmb);
		return defaultRecordId;
	}
	
	//Getter and Setter Methods for the attributes of the class
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public float getIntRate() {
		return intRate;
	}

	public void setIntRate(float intRate) {
		this.intRate = intRate;
	}

	public float getAmntLeft() {
		return amntLeft;
	}

	public void setAmntLeft(float amntLeft) {
		this.amntLeft = amntLeft;
	}
	
	//Allowing the user to be able to pay off part of their loan
	public void payOff(float amount) {
		this.amntLeft -= amount;
	}

	public float getLoanTermLeft() {
		return loanTermLeft;
	}

	public void setLoanTermLeft(float loanTermLeft) {
		this.loanTermLeft = loanTermLeft;
	}
}
