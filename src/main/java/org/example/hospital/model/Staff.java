package org.example.hospital.model;

import jakarta.persistence.*; // Make sure this is imported
import java.util.Objects; // Good practice to include for equals/hashCode, though not strictly required by JPA for basic functionality

/**
 * Represents a staff member in the hospital.
 */
@Entity
@Table(name = "staff") // Explicitly define table name
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for PostgreSQL auto-incrementing BIGSERIAL
    private Long id;

    /**
     * Staff's full name.
     */
    @Column(nullable = false) // Add non-null constraint
    private String name;

    /**
     * Role of the staff (doctor, nurse, admin).
     */
    @Enumerated(EnumType.STRING) // Store enum as String in DB
    @Column(nullable = false) // Add non-null constraint
    private Role role; // The type is now the Role ENUM

    /**
     * Department where the staff works.
     */
    @Column(nullable = false) // Add non-null constraint
    private String department;

    // Default constructor for JPA
    public Staff() {
    }

    // Constructor with fields, now correctly accepting Role enum
    public Staff(String name, Role role, String department) {
        this.name = name;
        this.role = role;
        this.department = department;
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

    public Role getRole() { // Corrected return type
        return role;
    }

    public void setRole(Role role) { // Corrected parameter type
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // It's highly recommended to override equals() and hashCode() for JPA entities.
    // This is a basic implementation, usually based on the ID.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(id, staff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Also useful for logging and debugging
    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", department='" + department + '\'' +
                '}';
    }
}