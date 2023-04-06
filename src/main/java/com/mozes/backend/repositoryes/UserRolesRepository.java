package com.mozes.backend.repositoryes;

import com.mozes.backend.models.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
}
