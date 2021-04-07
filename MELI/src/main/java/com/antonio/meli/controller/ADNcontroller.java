package com.antonio.meli.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonio.meli.exception.ADNexception;
import com.antonio.meli.exception.DBexception;
import com.antonio.meli.interfaxe.IADNinterfac;

@RestController
@RequestMapping("/")
public class ADNcontroller {
	
	@Autowired
	private IADNinterfac adnService;

	
	public boolean isMutant(@RequestBody Map<String,List<String>> dna) {
		List<String> lista= dna.get("dna");
		boolean isMutant= false;
		isMutant = adnService.isMutant(lista);
		return isMutant;
		
	}
	
	@PostMapping(value = "mutant/")
	public ResponseEntity<?> mutante(@RequestBody Map<String,List<String>> dna) {
		Map<String, Object> response = new HashMap();
		try {
			List<String> lista= dna.get("dna");
			
			Boolean isMutant=false;
			isMutant = adnService.isMutant(lista);
			if (isMutant) {
				 return new ResponseEntity<Boolean>(HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}catch (ADNexception adne) {
			response.put("mensaje", adne.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
		}catch (DBexception bde) {
			response.put("mensaje", bde.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
		}catch (Exception e) {
			response.put("mensaje", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
		}
	}		
}
