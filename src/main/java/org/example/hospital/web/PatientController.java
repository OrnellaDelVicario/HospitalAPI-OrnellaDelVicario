package org.example.hospital.web;

import org.example.hospital.model.Patient;
import org.example.hospital.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importar HttpStatus para @ResponseStatus
import org.springframework.http.ResponseEntity; // Importar ResponseEntity
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional; // Importar Optional
import java.util.stream.Collectors; // Necesario si usas StreamSupport para getAllPatients
import java.util.stream.StreamSupport; // Necesario si usas StreamSupport para getAllPatients

/**
 * Handles HTTP requests related to hospital patients.
 */
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patientRepository; // Usar final es buena práctica con @Autowired

    @Autowired
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Registers a new patient.
     * Handles POST requests to /patients.
     * @param patient The Patient object to be registered (from request body).
     * @return The registered Patient object (with generated ID), and HTTP status 201 Created.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Indica que el status por defecto será 201 Created
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    /**
     * Returns all patients.
     * Handles GET requests to /patients.
     * @return A list of all Patient objects.
     */
    @GetMapping
    public List<Patient> getAllPatients() {
        // Converting Iterable to List for consistent return type
        return StreamSupport.stream(patientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Returns a patient by ID.
     * Handles GET requests to /patients/{id}.
     * @param id The ID of the patient to retrieve.
     * @return ResponseEntity with Patient object if found (200 OK), or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.map(ResponseEntity::ok) // Si el paciente está presente, retorna 200 OK con el paciente
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no está presente, retorna 404 Not Found
    }

    /**
     * Updates an existing patient.
     * Handles PUT requests to /patients/{id}.
     * @param id The ID of the patient to update.
     * @param patientDetails The updated patient details (from request body).
     * @return ResponseEntity with the updated Patient object (200 OK), or 404 Not Found if ID not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        return patientRepository.findById(id) // Busca el paciente existente por ID
                .map(patient -> { // Si lo encuentra, actualiza sus propiedades
                    patient.setName(patientDetails.getName());
                    patient.setDiagnosis(patientDetails.getDiagnosis());
                    patient.setBillAmount(patientDetails.getBillAmount());
                    // Asegúrate de que assignedDoctor no sea nulo si no lo vas a cambiar,
                    // o de que el frontend envíe el ID del doctor
                    if (patientDetails.getAssignedDoctor() != null && patientDetails.getAssignedDoctor().getId() != null) {
                        // Aquí deberías buscar el doctor por su ID para asegurarte de que existe
                        // Para simplificar, asumimos que el ID del doctor enviado en patientDetails es válido
                        // En una app real, buscarías el doctorRepository.findById()
                        patient.setAssignedDoctor(patientDetails.getAssignedDoctor());
                    }
                    return ResponseEntity.ok(patientRepository.save(patient)); // Guarda y retorna el paciente actualizado con 200 OK
                }).orElseGet(() -> ResponseEntity.notFound().build()); // Si no lo encuentra, retorna 404 Not Found
    }

    /**
     * Deletes a patient by ID.
     * Handles DELETE requests to /patients/{id}.
     * @param id The ID of the patient to delete.
     * @return ResponseEntity with 204 No Content if successful, or 404 Not Found if ID not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (patientRepository.existsById(id)) { // Verifica si el paciente existe
            patientRepository.deleteById(id); // Elimínalo
            return ResponseEntity.noContent().build(); // Retorna 204 No Content (éxito sin contenido)
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    /**
     * Returns patients assigned to a specific doctor.
     * Handles GET requests to /patients/for-doctor?doctorId={id}.
     * @param doctorId The ID of the doctor.
     * @return A list of patients assigned to the specified doctor.
     */
    @GetMapping("/for-doctor")
    public List<Patient> getPatientsForDoctor(@RequestParam Long doctorId) {
        return patientRepository.findPatientsForDoctor(doctorId);
    }

    /**
     * Returns patients with a bill amount above the average.
     * Handles GET requests to /patients/above-avg-bill.
     * @return A list of patients whose bill amount is above the average.
     */
    @GetMapping("/above-avg-bill")
    public List<Patient> getPatientsAboveAverageBill() {
        return patientRepository.patientsAboveAvgBill();
    }

    /**
     * Returns average bill per department.
     * Handles GET requests to /patients/stats/avg-bill-per-department.
     * @return A list of Object arrays, each containing department name and average bill.
     */
    @GetMapping("/stats/avg-bill-per-department")
    public List<Object[]> getAvgBillPerDepartment() {
        return patientRepository.averageBillPerDepartment();
    }

    /**
     * Returns doctors with more than minCount patients.
     * Handles GET requests to /patients/stats/doctors-with-many-patients?minCount={count}.
     * @param minCount The minimum number of patients a doctor must have.
     * @return A list of Object arrays, each containing doctor ID, name, and patient count.
     */
    @GetMapping("/stats/doctors-with-many-patients")
    public List<Object[]> getDoctorsWithManyPatients(@RequestParam int minCount) {
        return patientRepository.doctorsWithPatientCount(minCount);
    }
}