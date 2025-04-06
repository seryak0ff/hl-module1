package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hpclab.hl.module1.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
