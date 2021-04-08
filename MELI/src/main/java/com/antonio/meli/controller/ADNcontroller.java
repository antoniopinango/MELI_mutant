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
	
	
	/**
	 * Inyeccion de dependencia de las clases de servicio
	 */
	@Autowired
	private IADNinterfac adnService;
	@Autowired
	private IStatsinterfac statsService;

		
	/**
	 * metodo para validar si un ADN es mutante o humano
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
	 * metodo para obtener las estadisticas del adn en bd
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
		if (estadisticas== null) {
			response.put("mensaje", "No se pudo calcular las estadisticas, no hay datos en BD");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Stats>(estadisticas, HttpStatus.OK);
	}
}
