package com.practise.multidatasources.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

	@Transactional
	public String create(Patient patient) {
		
		patientRepo.save(patient);
		
		PatientPg patientPg = new PatientPg();
		
		patientPg.setPatientName(patient.getPatientName());
		
		patientPg.setPaitientContact(patient.getPatientContact());
		
		patientRepoPg.save(patientPg);

		return "Success";
	}
	
	
	//@Transactional
	public String create2(Patient patient) {
		
		patientRepo.save(patient);
		
		if(true) {
			throw new RuntimeException("Some exception came after mysql persist. No rollback for mysql persist.");
		}
		PatientPg patientPg = new PatientPg();
		
		patientPg.setPatientName(patient.getPatientName());
		
		patientPg.setPaitientContact(patient.getPatientContact());
		
		patientRepoPg.save(patientPg);

		return "Success";
	}
	
	@Transactional  
	public String create3(Patient patient) {
		
		patientRepo.save(patient);
		
		if(true) {
			throw new RuntimeException("Some exception came after mysql persist. MySQL persist should rollback.");
		}
		PatientPg patientPg = new PatientPg();
		
		patientPg.setPatientName(patient.getPatientName());
		
		patientPg.setPaitientContact(patient.getPatientContact());
		
		patientRepoPg.save(patientPg);

		return "Success";
	}

}
