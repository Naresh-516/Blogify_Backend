package com.nt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(EmailAlreadyExistsException.class)
   public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
	return new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
   }
   @ExceptionHandler(UserNotFoundException.class)
   public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex){
	return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
   }
   @ExceptionHandler(PasswordMismatchException.class)
   public ResponseEntity<String> handlePasswordMismatchException(PasswordMismatchException ex){
	   return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_ACCEPTABLE);
   }
   @ExceptionHandler(BlogNotFoundException.class)
   public ResponseEntity<String> handleBlogNotFoundException(BlogNotFoundException ex){
	   return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
   }
   @ExceptionHandler(InvalidCredentialsException.class)
   public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex){
	   return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
   }
   @ExceptionHandler(Exception.class)
   public ResponseEntity<String> handleGlobalExceptions(Exception ex){
	   return new ResponseEntity<String>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
   }
   
}
	