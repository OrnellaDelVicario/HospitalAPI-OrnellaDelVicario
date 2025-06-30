package org.example.hospital.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Represents a patient in the hospital system.
 */
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Patient's full name.
     */
    private String name;

    /**
     * Diagnosis for the patient.
     */
    private String diagnosis;

    /**
     * Total bill amount in euros.
     */
    @Column(name = "bill_amount")
    private BigDecimal billAmount;

    /**
     * The doctor assigned to the patient.
     */
    @ManyToOne
    @JoinColumn(name = "assigned_doctor_id")
    private Staff assignedDoctor;

    public Patient() {}

    public Patient(String name, String diagnosis, BigDecimal billAmount, Staff assignedDoctor) {
        this.name = name;
        this.diagnosis = diagnosis;
        this.billAmount = billAmount;
        this.assignedDoctor = assignedDoctor;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public Staff getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Staff assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }
}
