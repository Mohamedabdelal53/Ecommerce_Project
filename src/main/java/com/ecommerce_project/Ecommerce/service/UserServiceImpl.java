package com.ecommerce_project.Ecommerce.service;

import com.ecommerce_project.Ecommerce.DTO.LoginDTO;
import com.ecommerce_project.Ecommerce.DTO.UserDTO;

import java.util.List;

public interface UserServiceImpl {
    String addUser(UserDTO userDTO);

    String login(LoginDTO loginDTO);

    List<UserDTO> getAllUsers();

    String deleteUser(Long id);

    UserDTO getMyUser(Long id);

    String updateMyUser(Long id, UserDTO userDTO);
}
