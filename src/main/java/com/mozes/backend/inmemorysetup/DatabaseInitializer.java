package com.mozes.backend.inmemorysetup;

import com.mozes.backend.dto.UserDto;
import com.mozes.backend.dto.UserRoleDto;
import com.mozes.backend.models.User;
import com.mozes.backend.repositoryes.UserRepository;
import com.mozes.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        UserRoleDto adminRole = new UserRoleDto();
        adminRole.setRoles("ADMIN");
        UserDto adminUser = new UserDto();
        adminUser.setAddress("cim");
        adminUser.setEmail("htcmokka@gmail.com");
        adminUser.setName("Mózes");
        adminUser.setPassword("Mózes");
        adminUser.setUsername("mozes");
        adminUser.setUserRole(adminRole);

        userService.createUser(adminUser,adminRole);
    }
}