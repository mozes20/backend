package com.mozes.backend.services;

import com.mozes.backend.dto.UserDto;
import com.mozes.backend.dto.UserRoleDto;
import com.mozes.backend.models.User;
import com.mozes.backend.models.UserRoles;
import com.mozes.backend.repositoryes.UserRepository;
import com.mozes.backend.repositoryes.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    public void createUser(UserDto userDto, UserRoleDto userRoleDto) {
        User user = new User(userDto.getAddress(),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getPassword(),
                userDto.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        UserRoles userRole = new UserRoles(userRoleDto.getRoles(), user);
        userRolesRepository.save(userRole);
    }

    public List<User> getUsers() {
        return userRepository.findAllByDeletedIsFalse();
    }

    public void softDeleteUser(long id) {
        User user = userRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDeleted(true);
        userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
