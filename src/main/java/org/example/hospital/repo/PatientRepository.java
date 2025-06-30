package org.example.hospital.repo;

import org.example.hospital.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for basic CRUD operations on Patient + custom queries.
 */
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>, PatientRepositoryCustom {
    // Incluye métodos custom definidos en PatientRepositoryCustom
}
