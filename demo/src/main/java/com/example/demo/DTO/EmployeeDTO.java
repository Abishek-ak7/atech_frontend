package com.example.demo.DTO;

import jakarta.persistence.*;


@Entity
@Table(name = "employees")
public class EmployeeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "email", length = 30, nullable = false)
    private String email;

    @Column(name = "password", length = 25, nullable = false)
    private String password;

   @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "reset_token", length = 255, nullable = false)
    private String resetToken;
    // Constructors
    public EmployeeDTO() {
    }

    // Getters and Setters
    public int getId() {
        return employeeId;
    }

    public void setId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   public String getName() {
     return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
