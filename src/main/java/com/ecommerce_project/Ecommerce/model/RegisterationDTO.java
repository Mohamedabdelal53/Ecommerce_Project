package com.ecommerce_project.Ecommerce.model;

import com.ecommerce_project.Ecommerce.entities.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterationDTO {
    private String username;
    private String password;
    private String email;
    private Address Address;

}
