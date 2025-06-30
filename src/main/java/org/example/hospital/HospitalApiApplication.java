package org.example.hospital;

import org.example.hospital.model.Patient;
import org.example.hospital.model.Role;
import org.example.hospital.model.Staff;
import org.example.hospital.repo.PatientRepository;
import org.example.hospital.repo.StaffRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class HospitalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApiApplication.class, args);
    }

    /**
     * Initializes sample data into the database when the application starts.
     */
    @Bean
    public CommandLineRunner demoData(StaffRepository staffRepository, PatientRepository patientRepository) {
        return args -> {
            // --- Sample Staff Data ---
            Staff drSmith = new Staff("Dr. Smith", Role.DOCTOR, "Cardiology");
            Staff drJones = new Staff("Dr. Jones", Role.DOCTOR, "Pediatrics");
            Staff nurseAnna = new Staff("Nurse Anna", Role.NURSE, "ER");
            Staff adminBob = new Staff("Admin Bob", Role.ADMIN, "Administration");
            Staff drClark = new Staff("Dr. Clark", Role.DOCTOR, "Oncology");
            Staff drDavis = new Staff("Dr. Davis", Role.DOCTOR, "Cardiology"); // Another doctor for cardiology

            drSmith = staffRepository.save(drSmith);
            drJones = staffRepository.save(drJones);
            nurseAnna = staffRepository.save(nurseAnna);
            adminBob = staffRepository.save(adminBob);
            drClark = staffRepository.save(drClark);
            drDavis = staffRepository.save(drDavis);

            // --- Sample Patient Data ---
            patientRepository.save(new Patient("John Doe", "Arrhythmia", new BigDecimal("4500.00"), drSmith));
            patientRepository.save(new Patient("Jane Roe", "Fever", new BigDecimal("800.50"), drJones));
            patientRepository.save(new Patient("Peter Pan", "Broken Arm", new BigDecimal("1200.00"), drJones));
            patientRepository.save(new Patient("Alice Wonderland", "Diabetes", new BigDecimal("3200.75"), drSmith));
            patientRepository.save(new Patient("Bob Builder", "Checkup", new BigDecimal("250.00"), drClark));
            patientRepository.save(new Patient("Charlie Chaplin", "Headache", new BigDecimal("150.00"), drJones));
            patientRepository.save(new Patient("Diana Prince", "Heart Murmur", new BigDecimal("6000.00"), drSmith));
            patientRepository.save(new Patient("Eve Harrington", "Flu", new BigDecimal("700.00"), drClark));
            patientRepository.save(new Patient("Frank Sinatra", "Cancer", new BigDecimal("15000.00"), drClark));
            patientRepository.save(new Patient("Grace Kelly", "Routine Checkup", new BigDecimal("300.00"), drDavis));
            patientRepository.save(new Patient("Harry Potter", "Magic Sickness", new BigDecimal("9000.00"), drDavis));
            patientRepository.save(new Patient("Ivy League", "Allergies", new BigDecimal("400.00"), drSmith));
            patientRepository.save(new Patient("Jack Sparrow", "Scurvy", new BigDecimal("1100.00"), drSmith)); // Dr. Smith now has 6 patients

            System.out.println("Sample data initialized!");
        };
    }
}