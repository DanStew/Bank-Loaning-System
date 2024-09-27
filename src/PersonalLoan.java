public class PersonalLoan extends Loan{

	//Default constructor for the AutoLoan class
	public PersonalLoan() {
		super("Personal");
	}
	
	//Custom constructor for attributes, not including recordId
	public PersonalLoan(float intRate, float amntLeft, float loanTermLeft) {
		super("Personal",intRate,amntLeft,loanTermLeft);
	}
	
	//Custom constructor for all attributes
	public PersonalLoan(String recordId, float intRate, float amntLeft, float loanTermLeft) {
		super(recordId,"Personal",intRate,amntLeft,loanTermLeft);
	}
	
	//Returning error values with overpayment
	public void setOverpayment(float overpayment) {
		System.out.println("This type of loan does not accept any overpayment");
	}
		
	public String getOverpayment() {
		return "N/A";
	}
}
