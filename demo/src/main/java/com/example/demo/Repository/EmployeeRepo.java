package com.example.demo.Repository;

import com.example.demo.DTO.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<EmployeeDTO, String> {
    Optional<EmployeeDTO> findByEmailAndPassword(String email, String password);
    Optional<EmployeeDTO> findByEmail(String email);
    Optional<EmployeeDTO> findByResetToken(String resetToken);
}
