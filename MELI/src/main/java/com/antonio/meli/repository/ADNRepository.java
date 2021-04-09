package com.antonio.meli.repository;

import org.springframework.stereotype.Repository;

import com.antonio.meli.entity.ADN;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ADNRepository extends JpaRepository<ADN, Long> {
	
	/**
	 * metodo para buscar cuantos registros 
	 * hay en BD por mutante o humano
	 * @param mutante
	 * @return
	 */
	public Long countByMutante(boolean mutante);
	public ADN findByAdn(String adn);
	public boolean existsByAdn(String adn);
}
