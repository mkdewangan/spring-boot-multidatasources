package com.practise.multidatasources.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.practise.multidatasources.mysql.Patient;
import com.practise.multidatasources.mysql.PatientRepository;
import com.practise.multidatasources.postgres.PatientPg;
import com.practise.multidatasources.postgres.PatientRepositoryPg;

@Service
public class MultiDataSourceService {
	
	@Autowired
	private PatientRepository patientRepo;

	@Autowired
	private PatientRepositoryPg patientRepoPg;
	
	
	public List<PatientPg> getPatientsPostgres() {

		System.out.println("PatientController.getPatients()");

		return patientRepoPg.findAll();
	}

	public List<Patient> getPatientsMySql() {

		System.out.println("PatientController.getPatients()");

		return patientRepo.findAll();
	}

	
	public String create(Patient patient) {
		
		patientRepo.save(patient);
		
		PatientPg patientPg = new PatientPg();
		
		patientPg.setPatientName(patient.getPatientName());
		
		patientPg.setPaitientContact(patient.getPatientContact());
		
		patientRepoPg.save(patientPg);

		return "Success";
	}
	

}
