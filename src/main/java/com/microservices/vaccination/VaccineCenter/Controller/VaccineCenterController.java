package com.microservices.vaccination.VaccineCenter.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.vaccination.VaccineCenter.Entities.VaccineCenter;
import com.microservices.vaccination.VaccineCenter.Model.Citizen;
import com.microservices.vaccination.VaccineCenter.Model.RequiredResponse;
import com.microservices.vaccination.VaccineCenter.Repositories.CenterRepo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(path = "/center")
public class VaccineCenterController {

	@Autowired
	private CenterRepo centerRepo;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(method = RequestMethod.GET, path = "/test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("Hello, Wel-Come to my Vaccination Center", HttpStatus.OK);
	}

	@PostMapping(path = "/add")
	public ResponseEntity<VaccineCenter> addVaccineCenter(@RequestBody VaccineCenter newCenter) {

		VaccineCenter center = centerRepo.save(newCenter);
		return new ResponseEntity<>(center, HttpStatus.OK);
	}

	@GetMapping(path = "/id/{id}")
	@HystrixCommand(fallbackMethod = "handleCitizenDownTime")
	public ResponseEntity<RequiredResponse> getAllDataBasedOnCenterId(@PathVariable Integer id) {

		RequiredResponse requiredResponse = new RequiredResponse();

		// get Vaccination Center details

		VaccineCenter center = centerRepo.findById(id).get();
		requiredResponse.setCenter(center);

		// then get all citizens registered to Vaccination Center

		//List<Citizen> listOfCitizen = restTemplate.getForObject("http://localhost:9081/citizen/id/" + id,List.class);
		List<Citizen> listOfCitizen = restTemplate.getForObject("http://CITIZEN-SERVICE/citizen/id/" + id, List.class);
		requiredResponse.setListOfCitizen(listOfCitizen);

		return new ResponseEntity<RequiredResponse>(requiredResponse, HttpStatus.OK);
	}

	public ResponseEntity<RequiredResponse> handleCitizenDownTime(@PathVariable Integer id) {

		RequiredResponse requiredResponse = new RequiredResponse();

		// get Vaccination Center details only, if CITIZEN-SERVICE is Down.

		VaccineCenter center = centerRepo.findById(id).get();
		requiredResponse.setCenter(center);
		return new ResponseEntity<RequiredResponse>(requiredResponse, HttpStatus.OK);
	}
}
