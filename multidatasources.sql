
-- Postgres DDL

-- demo.patients definition

-- Drop table

-- DROP TABLE demo.patients;

CREATE TABLE demo.patients (
	patient_id serial NOT NULL,
	patient_name text NULL,
	patient_contact bpchar(12) NULL,
	user_id text NULL,
	CONSTRAINT pk PRIMARY KEY (patient_id)
);


-- demo.patients foreign keys

ALTER TABLE demo.patients ADD CONSTRAINT fk1 FOREIGN KEY (user_id) REFERENCES demo.users(user_id);



-- MySQL DDL

-- practise.patients definition

CREATE TABLE `patients` (
  `patient_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `patient_name` varchar(100) NOT NULL,
  `patient_contact` varchar(14) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;