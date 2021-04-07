package com.antonio.meli.repository;

import org.springframework.stereotype.Repository;

import com.antonio.meli.entity.ADN;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ADNRepository extends JpaRepository<ADN, Long> {
	
	public Optional<String> findByAdn(String adn);

}
