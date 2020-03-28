package com.practise.multidatasources.postgres;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;



import java.util.List;


/**
 * The persistent class for the "Patients" database table.
 * 
 */
@Entity
@Table(name="patients", schema="demo")
@NamedQuery(name="Patient.findAllPg", query="SELECT p FROM PatientPg p")
public class PatientPg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="patient_id")
	private Integer patientId;

	@Column(name="patient_contact")
	private String patientContact;

	@Column(name="patient_name")
	private String patientName;
	
//	//bi-directional many-to-one association to Appointment
//		@OneToMany(mappedBy="patient")
//		private List<Appointment> appointments;
	
	@Column(name="user_id")
	private String userId;

//	//bi-directional many-to-one association to Appointment
//	@OneToMany(mappedBy="patient")
//	private List<Appointment> appointments;

	public PatientPg() {
	}

	public Integer getPatientId() {
		return this.patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getPaitientContact() {
		return this.patientContact;
	}

	public void setPaitientContact(String paitientContact) {
		this.patientContact = paitientContact;
	}

	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientPg other = (PatientPg) obj;
		
		
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else if (!patientId.equals(other.patientId))
			return false;
		
		return true;
	}

//	public List<Appointment> getAppointments() {
//		return this.appointments;
//	}
//
//	public void setAppointments(List<Appointment> appointments) {
//		this.appointments = appointments;
//	}
	
//
//	public Appointment addAppointment(Appointment appointment) {
//		getAppointments().add(appointment);
//		appointment.setPatient(this);
//
//		return appointment;
//	}
//
//	public Appointment removeAppointment(Appointment appointment) {
//		getAppointments().remove(appointment);
//		appointment.setPatient(null);
//
//		return appointment;
//	}

}