package com.demoSpring;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@RestControllerAdvice
@OpenAPIDefinition(
	info = @Info(
	    title = "Hospital App",
	    version = "1.0.4",
	    description = "This App is regarding Hospital Management System",
	    contact = @Contact(
	        name = "Vedant Jadhav",
	        email = "vedantjadhav316@gmail.com",
	        url = "http://github.com/Vedant1347"
	    )
	)
)
public class ApplicationException {

	@ExceptionHandler(IdNotPresent.class)
	public ResponseEntity<ResponseStructure<Hospital>> idNotExists(IdNotPresent obj) {
		ResponseStructure<Hospital> res = new ResponseStructure<>();
		res.setLocaldatetime(LocalDateTime.now());
		res.setMessage(obj.getMessage());
		res.setStatuscode(HttpStatus.NOT_FOUND.value());
		ResponseEntity<ResponseStructure<Hospital>> rs = new ResponseEntity<ResponseStructure<Hospital>>(res,HttpStatus.FOUND);
		return rs;
	}
}