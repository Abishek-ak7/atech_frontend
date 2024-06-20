package com.example.demo.DTO;

public class EmailRequestDTO {
    private String email;
    public EmailRequestDTO() {}

    public EmailRequestDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
