package org.example.hospital.sql;

/**
 * Contains native SQL queries for patient-related operations.
 */
public enum PatientQuery {

    FIND_PATIENTS_FOR_DOCTOR("""
        SELECT * FROM patients WHERE assigned_doctor_id = ?
    """),

    AVERAGE_BILL_PER_DEPARTMENT("""
        SELECT s.department, AVG(p.bill_amount)
        FROM patients p
        JOIN staff s ON p.assigned_doctor_id = s.id
        GROUP BY s.department
    """),

    DOCTORS_WITH_PATIENT_COUNT("""
        SELECT s.id, s.name, COUNT(p.id)
        FROM staff s
        JOIN patients p ON s.id = p.assigned_doctor_id
        WHERE s.role = 'doctor'
        GROUP BY s.id, s.name
        HAVING COUNT(p.id) > ?
    """),

    PATIENTS_ABOVE_AVG_BILL("""
        SELECT * FROM patients
        WHERE bill_amount > (SELECT AVG(bill_amount) FROM patients)
    """);

    private final String sql;

    PatientQuery(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
