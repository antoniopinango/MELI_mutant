package com.antonio.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonio.meli.DTO.Stats;
import com.antonio.meli.exception.DBexception;
import com.antonio.meli.interfaxe.IStatsinterfac;
import com.antonio.meli.repository.ADNRepository;

@Service
public class StatsService implements IStatsinterfac{
	
	@Autowired
	ADNRepository repo;

	/**
	 * metodo para recuperar las estadisticas del ADN en bd
	 *
	 */
	@Override
	public Stats getEstadistica() throws DBexception{
		Stats estadistica= new Stats();
		Long nroMutantes=repo.countByMutante(true);
		Long nroHumanos=repo.countByMutante(false);
		
		estadistica.setCount_human_dna(nroHumanos);
		estadistica.setCount_mutant_dna(nroMutantes);
		estadistica.setRatio(getRatio(nroMutantes, nroHumanos));
		if(estadistica.getCount_mutant_dna()== null) {
			throw new DBexception("No se pudo calcular las estadisticas, no hay datos en BD");
		}
		return estadistica;
	}
	
	/**
	 * metodo para calcular el ratio
	 * @param nroMutantes
	 * @param nroHumanos
	 * @return
	 */
	private Float getRatio(Long nroMutantes, Long nroHumanos) {
		Long sumatoria= nroMutantes + nroHumanos;
		Float ratio=  (float) ((nroMutantes*100)/ sumatoria)/100;
		return ratio;
	}

	
}
