package com.mozes.backend.repositoryes;

import com.mozes.backend.dto.UserResponsDto;
import com.mozes.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByDeletedIsFalse();
    Optional<User> findByIdAndDeletedIsFalse(Long id);

    User findByUsername(String username);
}
