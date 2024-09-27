public class OtherLoan extends Loan{

	//Default constructor for the AutoLoan class
	public OtherLoan() {
		super("Other");
	}
	
	//Custom constructor for attributes, not including recordId
	public OtherLoan(float intRate, float amntLeft, float loanTermLeft) {
		super("Other",intRate,amntLeft,loanTermLeft);
	}
	
	//Custom constructor for all attributes
	public OtherLoan(String recordId, float intRate, float amntLeft, float loanTermLeft) {
		super(recordId,"Other",intRate,amntLeft,loanTermLeft);
	}
	
	//Returning error values with overpayment
	public void setOverpayment(float overpayment) {
		System.out.println("This type of loan does not accept any overpayment");
	}
		
	public String getOverpayment() {
		return "N/A";
	}
}
