package org.example.hospital.web;

import org.example.hospital.model.Staff;
import org.example.hospital.repo.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    /**
     * Handles GET requests to /staff
     * Retrieves all staff members.
     * @return A list of Staff objects.
     */
    @GetMapping
    public List<Staff> getAllStaff() {
        return StreamSupport.stream(staffRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Handles GET requests to /staff/{id}
     * Retrieves a single staff member by ID.
     * @param id The ID of the staff member to retrieve.
     * @return ResponseEntity with Staff object if found, or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        return staff.map(ResponseEntity::ok) // If staff is present, return 200 OK with staff object
                .orElseGet(() -> ResponseEntity.notFound().build()); // Else return 404 Not Found
    }

    /**
     * Handles POST requests to /staff
     * Registers a new staff member.
     * @param staff The Staff object to be registered (from request body).
     * @return The registered Staff object (with generated ID), and HTTP status 201 Created.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Sets the HTTP status to 201 Created
    public Staff registerStaff(@RequestBody Staff staff) {
        return staffRepository.save(staff);
    }

    /**
     * Handles PUT requests to /staff/{id}
     * Updates an existing staff member.
     * @param id The ID of the staff member to update.
     * @param staffDetails The updated staff details (from request body).
     * @return ResponseEntity with the updated Staff object, or 404 Not Found if ID not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staffDetails) {
        return staffRepository.findById(id) // Find the existing staff member by ID
                .map(staff -> { // If found, update its properties
                    staff.setName(staffDetails.getName());
                    staff.setRole(staffDetails.getRole());
                    staff.setDepartment(staffDetails.getDepartment());
                    return ResponseEntity.ok(staffRepository.save(staff)); // Save and return updated staff with 200 OK
                }).orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }

    /**
     * Handles DELETE requests to /staff/{id}
     * Deletes a staff member by ID.
     * @param id The ID of the staff member to delete.
     * @return ResponseEntity with 204 No Content if successful, or 404 Not Found if ID not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        if (staffRepository.existsById(id)) { // Check if the staff member exists
            staffRepository.deleteById(id); // Delete it
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
    }
}