package com.wesleyprado.trabalhouna.dto;

import com.wesleyprado.trabalhouna.services.validation.Password;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RegisterPasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Can't be blank.")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Can't be blank.")
    @CPF(message = "Invalid CPF")
    private String cpf;

    @NotBlank(message = "Can't be blank.")
    private String token;

    @NotBlank(message = "Can't be blank.")
    @Password
    private String password;

    public RegisterPasswordDTO() {
    }

    public RegisterPasswordDTO(String email, String cpf, String token, String password) {
        this.email = email;
        this.cpf = cpf;
        this.token = token;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
