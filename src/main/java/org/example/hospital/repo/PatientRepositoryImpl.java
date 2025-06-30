package org.example.hospital.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.example.hospital.model.Patient;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Implementation of custom queries using native SQL.
 */
@Repository
public class PatientRepositoryImpl implements PatientRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Patient> findPatientsForDoctor(Long doctorId) {
        // Query para encontrar pacientes asignados a un doctor específico.
        // Usa `Patient.class` para que JPA mapee automáticamente los resultados a objetos Patient.
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM patients WHERE assigned_doctor_id = ?", Patient.class);
        query.setParameter(1, doctorId); // Asigna el doctorId al parámetro de la consulta
        return query.getResultList();
    }

    @Override
    public List<Object[]> averageBillPerDepartment() {
        // Query para calcular el promedio de las facturas por departamento de los doctores asignados.
        // Retorna un List<Object[]> donde cada Object[] contiene el nombre del departamento y el promedio.
        Query query = entityManager.createNativeQuery(
                "SELECT s.department, AVG(p.bill_amount) " +
                        "FROM patients p JOIN staff s ON p.assigned_doctor_id = s.id " +
                        "GROUP BY s.department");
        return query.getResultList();
    }

    @Override
    public List<Object[]> doctorsWithPatientCount(int minCount) {
        // Query para listar doctores que tienen más de 'minCount' pacientes.
        // Importante: `s.role = 'DOCTOR'` ahora usa el valor en MAYÚSCULAS para coincidir con el enum.
        // Retorna un List<Object[]> donde cada Object[] contiene el ID del doctor, su nombre y el conteo de pacientes.
        Query query = entityManager.createNativeQuery(
                "SELECT s.id, s.name, COUNT(p.id) " +
                        "FROM staff s JOIN patients p ON s.id = p.assigned_doctor_id " +
                        "WHERE s.role = 'DOCTOR' " + // <-- CORRECCIÓN: 'DOCTOR' en mayúsculas
                        "GROUP BY s.id, s.name " +
                        "HAVING COUNT(p.id) > ?"); // Usa `>` (mayor que)
        query.setParameter(1, minCount); // Asigna minCount al parámetro de la consulta
        return query.getResultList();
    }

    @Override
    public List<Patient> patientsAboveAvgBill() {
        // Query para encontrar pacientes cuya factura es superior al promedio general de facturas.
        // Usa `Patient.class` para que JPA mapee automáticamente los resultados a objetos Patient.
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM patients " +
                        "WHERE bill_amount > (SELECT AVG(bill_amount) FROM patients)",
                Patient.class);
        return query.getResultList();
    }
}