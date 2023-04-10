package com.example.dto;

import java.io.Serializable;

public class SignupRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 10, max = 10)
    private String phone;

    @NotNull
    @Lob
    private byte[] image;

    public SignupRequest() {
    }

    public SignupRequest(String name, String email, String password, String phone, byte[] image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
