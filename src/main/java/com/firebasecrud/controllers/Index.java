package com.firebasecrud.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {

	@GetMapping("/")
	public ResponseEntity<String> index(){
		return ResponseEntity.ok().body("Tudo OK!!!");
	}
}
