package org.example.hospital.repo;

import org.example.hospital.model.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for basic CRUD operations on Staff.
 */
@Repository
public interface StaffRepository extends CrudRepository<Staff, Long> {
    // CRUD listo: findAll, save, findById, deleteById
}
