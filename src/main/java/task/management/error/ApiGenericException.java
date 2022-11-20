package task.management.error;
 
public class ApiGenericException extends RuntimeException  {

	/**
	 * @author Edvin Tuzlakovic
	 * @since  28/09/2021
	 */
	
	private static final long serialVersionUID = -5748724373208240028L;
	
	public ApiGenericException() {
		// TODO Auto-generated constructor stub
	}
	
	public ApiGenericException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {		
		super(message, cause, enableSuppression, writableStackTrace);
		
	}
	
	public ApiGenericException(String message, Throwable cause) {		
		super(message, cause);		
	}
	
	public ApiGenericException(String message) {		
		super(message);		
	}
	
	public ApiGenericException(Throwable cause) {		
		super(cause);		
	}
}
