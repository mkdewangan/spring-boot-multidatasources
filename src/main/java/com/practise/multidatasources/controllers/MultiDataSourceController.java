package com.practise.multidatasources.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.practise.multidatasources.mysql.Patient;
import com.practise.multidatasources.postgres.PatientPg;
import com.practise.multidatasources.services.MultiDataSourceService;


@RestController
public class MultiDataSourceController {
	
	@Autowired
    private MultiDataSourceService service;
	
	@GetMapping("/")
	public String home() {
		
		return "Spring Boot with Multi Datsources";
	}
	
	@GetMapping("/patientsMySql")
    public List<Patient> getPatientsMySql() {
		
		System.out.println("PatientController.getPatients()");
		
        
		
		return service.getPatientsMySql();
    }
	
	@GetMapping("/patientsPostgres")
    public List<PatientPg> getPatientsPostgres() {
		
		System.out.println("PatientController.getPatients()");
		
      
		
		return service.getPatientsPostgres();
    }
	
	@PostMapping("/patient")
	public String create(@Valid @RequestBody Patient patient) {
		
		return service.create(patient);
	}

}
