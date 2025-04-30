package ru.hpclab.hl.module1.apimodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hpclab.hl.module1.apimodule.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
