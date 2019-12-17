package ru.perm.v.restsecurity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

	@GetMapping
	public ResponseEntity<String> getDefault() {
		return new ResponseEntity<>("Не зарегистрирован", HttpStatus.OK);
	}
}
