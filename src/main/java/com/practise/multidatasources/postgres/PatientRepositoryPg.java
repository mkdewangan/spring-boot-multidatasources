package com.practise.multidatasources.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepositoryPg extends JpaRepository<PatientPg, Integer> {

}
