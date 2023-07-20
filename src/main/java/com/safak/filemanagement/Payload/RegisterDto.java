package com.safak.filemanagement.Payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;

public class RegisterDto {

    @NotNull(message = "Username field should not be null")
    private String username;
    @NotNull(message = "Email field should not be null")
    private String email;
    @NotNull(message = "Password field should not be null")
    private String password;

    @JsonIgnore
    private boolean isActive=true;

    public RegisterDto() {
    }

    public RegisterDto(String username, String email, String password, boolean isActive) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
