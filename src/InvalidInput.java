//Custom Exception class, for if the input the user enters is invalid
public class InvalidInput extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidInput(String errorMsg) {
		//Calling the Exception class with the given message
		super(errorMsg);
	}
}
