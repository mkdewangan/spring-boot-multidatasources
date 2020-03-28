package com.practise.multidatasources.mysql;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the patients database table.
 * 
 */
@Entity
@Table(name="patients")
@NamedQuery(name="Patient.findAll", query="SELECT p FROM Patient p")
public class Patient implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="patient_id")
	private Integer patientId;

	@Column(name="patient_contact")
	private String patientContact;

	@Column(name="patient_name")
	private String patientName;

	public Patient() {
	}

	public Integer getPatientId() {
		return this.patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getPatientContact() {
		return this.patientContact;
	}

	public void setPatientContact(String patientContact) {
		this.patientContact = patientContact;
	}

	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", patientContact=" + patientContact + ", patientName=" + patientName
				+ "]";
	}

}