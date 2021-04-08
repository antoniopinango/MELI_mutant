package com.antonio.meli.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonio.meli.entity.Stats;
import com.antonio.meli.exception.ADNexception;
import com.antonio.meli.exception.DBexception;
import com.antonio.meli.interfaxe.IADNinterfac;
import com.antonio.meli.interfaxe.IStatsinterfac;

@RestController
@RequestMapping("/")
public class ADNcontroller {
	
	@Autowired
	private IADNinterfac adnService;
	@Autowired
	private IStatsinterfac statsService;

	
	public boolean isMutant(@RequestBody Map<String,List<String>> dna) {
		List<String> lista= dna.get("dna");
		boolean isMutant= false;
		isMutant = adnService.isMutant(lista);
		return isMutant;
		
	}
	
	/**
	 * @param dna
	 * @return
	 */
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
	
	/**
	 * @return
	 */
	@GetMapping("stats")
	public ResponseEntity<?> estadisticas(){
		Map<String, Object> response = new HashMap();
		Stats estadisticas= new Stats();
		try {
			estadisticas= statsService.getEstadistica();
			
			
		} catch (DBexception dbe) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", dbe.getMessage());
			//Se envia error a la vista
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ResponseEntity<Stats>(estadisticas, HttpStatus.OK);
	}
}
