package com.microservices.vaccination.VaccineCenter.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.microservices.vaccination.VaccineCenter.Entities.VaccineCenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequiredResponse {
	
	private VaccineCenter center;
	private List<Citizen> listOfCitizen;

}
