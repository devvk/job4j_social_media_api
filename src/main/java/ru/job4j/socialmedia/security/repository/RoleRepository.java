package ru.job4j.socialmedia.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.Role;
import ru.job4j.socialmedia.model.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName name);
}
