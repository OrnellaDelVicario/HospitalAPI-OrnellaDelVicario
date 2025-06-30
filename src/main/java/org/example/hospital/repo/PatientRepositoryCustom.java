package org.example.hospital.repo;

import org.example.hospital.model.Patient;

import java.util.List;

/**
 * Custom repository interface with advanced SQL queries.
 */
public interface PatientRepositoryCustom {

    List<Patient> findPatientsForDoctor(Long doctorId);

    List<Object[]> averageBillPerDepartment();

    List<Object[]> doctorsWithPatientCount(int minCount);

    List<Patient> patientsAboveAvgBill();
}
