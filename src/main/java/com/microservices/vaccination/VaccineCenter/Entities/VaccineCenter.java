package com.microservices.vaccination.VaccineCenter.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineCenter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String centerName;
	private String centerAddress;

}
