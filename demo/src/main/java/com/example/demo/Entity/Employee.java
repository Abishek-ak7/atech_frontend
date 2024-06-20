package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employee_id;
    @Column(name="email",length = 50)
    private String email;

    @Column(name = "pass",length = 25)
    private String password;


   @Column(name = "name",length = 25)
   private String name;



    public Employee(int employee_id, String email, String password,String name) {
        this.employee_id = employee_id;
        this.email = email;
        this.password = password;

    }

    public Employee() {
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
    public String getName() {return name;
    }

    public void setName(String name) {this.name = name;
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
}