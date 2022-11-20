package task.management.error;
 
public class ApiNotFoundException extends RuntimeException  {

	/**
	 * @author Edvin Tuzlakovic
	 * @since  04/05/2021
	 */
	
	private static final long serialVersionUID = -5748724373208240028L;
	
	public ApiNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	
	public ApiNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {		
		super(message, cause, enableSuppression, writableStackTrace);
		
	}
	
	public ApiNotFoundException(String message, Throwable cause) {		
		super(message, cause);		
	}
	
	public ApiNotFoundException(String message) {		
		super(message);		
	}
	
	public ApiNotFoundException(Throwable cause) {		
		super(cause);		
	}
}
