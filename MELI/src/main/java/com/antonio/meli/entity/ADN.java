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
	private boolean mutante;
	

	@Temporal(TemporalType.DATE)
	@Column(name = "fechacreacion")
	private Date fechaCreacion;
	
	public ADN() {
		
	}
	
		
	public ADN(String adn, boolean mutante) {
		super();
		this.adn = adn;
		this.mutante = mutante;
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
	public boolean isMutante() {
		return mutante;
	}
	public void setMutante(boolean mutante) {
		this.mutante = mutante;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "ADN [id=" + id + ", adn=" + adn + ", mutante=" + mutante + ", fechaCreacion=" + fechaCreacion + "]";
	}
		
	
}
