
public class AutoLoan extends Loan{
	
	//Default constructor for the AutoLoan class
	public AutoLoan() {
		super("Auto");
	}
	
	//Custom constructor for attributes, not including recordId
	public AutoLoan(float intRate, float amntLeft, float loanTermLeft) {
		super("Auto",intRate,amntLeft,loanTermLeft);
	}
	
	//Custom constructor for all attributes
	public AutoLoan(String recordId, float intRate, float amntLeft, float loanTermLeft) {
		super(recordId,"Auto",intRate,amntLeft,loanTermLeft);
	}
	
	//Returning error values with overpayment
	public void setOverpayment(float overpayment) {
		System.out.println("This type of loan does not accept any overpayment");
	}
	
	public String getOverpayment() {
		return "N/A";
	}
}
