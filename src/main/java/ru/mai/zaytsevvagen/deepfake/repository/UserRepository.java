package ru.mai.zaytsevvagen.deepfake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mai.zaytsevvagen.deepfake.entity.User;

import java.util.Optional;

/**
 * @author 1ommy
 * @version 19.03.2023
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT id FROM _user WHERE (firstname || lastname) = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);
}
