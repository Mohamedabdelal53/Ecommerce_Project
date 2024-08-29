package com.ecommerce_project.Ecommerce.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RegisterationDTO {
    private String username;
    private String password;
    private String email;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

}
