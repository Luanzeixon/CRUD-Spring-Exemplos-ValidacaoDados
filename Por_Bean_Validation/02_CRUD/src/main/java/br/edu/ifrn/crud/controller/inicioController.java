package br.edu.ifrn.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class inicioController {
	
	@GetMapping("/")
	public String inicio() {
		return "inicio";
	}
	
}
