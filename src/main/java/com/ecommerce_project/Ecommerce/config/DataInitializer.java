package com.ecommerce_project.Ecommerce.config;

import com.ecommerce_project.Ecommerce.entities.Role;
import com.ecommerce_project.Ecommerce.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements ApplicationRunner {

    private final RoleRepo roleRepository;

    @Autowired
    public DataInitializer(RoleRepo roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
            Optional<Role> admin = roleRepository.findByName("ROLE_ADMIN");
            if(admin.isEmpty()){
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
            Optional<Role> user = roleRepository.findByName("ROLE_USER");
            if(user.isEmpty()){
                roleRepository.save(new Role("ROLE_USER"));
            }
    }
}
