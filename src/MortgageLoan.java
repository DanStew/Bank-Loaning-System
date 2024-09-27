public class MortgageLoan extends Loan{
	private float overpayment;

	//Default constructor for the AutoLoan class
	public MortgageLoan() {
		super("Mortgage");
		overpayment = 1;
	}
	
	//Custom constructor for attributes, not including recordId
	public MortgageLoan(float intRate, float amntLeft, float loanTermLeft,float overpayment) {
		super("Mortgage",intRate,amntLeft,loanTermLeft);
		this.overpayment = overpayment;
	}
	
	//Custom constructor for all attributes
	public MortgageLoan(String recordId, float intRate, float amntLeft, float loanTermLeft,float overpayment) {
		super(recordId,"Mortgage",intRate,amntLeft,loanTermLeft);
		this.overpayment = overpayment;
	}
	
	//Getter and setter for overpayment variable
	public void setOverpayment(float overpayment) {
		this.overpayment = overpayment;
	}
	
	//NOTE : This is returned as a string as this method in other subclasses return error values
	//It will be used as part of the custom Records output, so better if they are all the same type of variable
	public String getOverpayment() {
		return String.valueOf(overpayment);
	}
}
