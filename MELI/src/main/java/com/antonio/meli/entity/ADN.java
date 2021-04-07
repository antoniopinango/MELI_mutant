package com.antonio.meli.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="adn")
public class ADN {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "dna")
	private String adn;
	@Temporal(TemporalType.DATE)
	@Column(name = "fechacreacion")
	private Date fechaCreacion;
	
	public ADN() {
		
	}
	
	
	
	
	
	public ADN(String adn) {
		super();
		this.adn = adn;
	}





	@PrePersist
	private void preFechaCreacion() {
		this.fechaCreacion = new Date();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAdn() {
		return adn;
	}
	public void setAdn(String adn) {
		this.adn = adn;
	}
		
	@Override
	public String toString() {
		return "ADN [id=" + id + ", adn=" + adn + "]";
	}
}
