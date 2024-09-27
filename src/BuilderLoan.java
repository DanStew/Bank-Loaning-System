
public class BuilderLoan extends Loan{
	private float overpayment;

	//Default constructor for the AutoLoan class
	public BuilderLoan() {
		super("Builder");
		overpayment = 1;
	}
	
	//Custom constructor for attributes, not including recordId
	public BuilderLoan(float intRate, float amntLeft, float loanTermLeft,float overpayment) {
		super("Builder",intRate,amntLeft,loanTermLeft);
		this.overpayment = overpayment;
	}
	
	//Custom constructor for all attributes
	public BuilderLoan(String recordId, float intRate, float amntLeft, float loanTermLeft,float overpayment) {
		super(recordId,"Builder",intRate,amntLeft,loanTermLeft);
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
