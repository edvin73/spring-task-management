package task.management.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
 

@ControllerAdvice
public class ApiRestExceptionHandler {
	
	//add an exception handler for CustomNotFoundException
		@ExceptionHandler
		public ResponseEntity<?> handleException(ApiNotFoundException e){
			
			//Create CustomErrorResponse object
			ApiErrorResponse error = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), 
																e.getMessage(), 
																LocalDateTime.now() );
			//return ResponseEntity		
			return new ResponseEntity<ApiErrorResponse>(error, HttpStatus.NOT_FOUND);		 
		}
		
		//Add another exception handler to catch any exception (catch all)
		@ExceptionHandler
		public ResponseEntity<?> handleException(ApiGenericException e){
			
			//Create CustomErrorResponse object
			ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), 
																e.getMessage(), 
																LocalDateTime.now());
			//return ResponseEntity		
			return new ResponseEntity<ApiErrorResponse>(error, HttpStatus.BAD_REQUEST);		 
		}
		
		//Add another exception handler to catch any exception (catch all)
		@ExceptionHandler
		public ResponseEntity<?> handleException(Exception e){
			
			//Create CustomErrorResponse object
			ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), 
																e.getMessage(), 
																LocalDateTime.now());
			//return ResponseEntity		
			return new ResponseEntity<ApiErrorResponse>(error, HttpStatus.BAD_REQUEST);		 
		}
}
