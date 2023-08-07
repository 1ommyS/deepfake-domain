package ru.mai.zaytsevvagen.deepfake.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mai.zaytsevvagen.deepfake.entity.File;
import ru.mai.zaytsevvagen.deepfake.entity.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 1ommy
 * @version 19.03.2023
 */
@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findAll();

    List<File> findByDeleteAtLessThanEqual(LocalDateTime now);

    List<File> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE File f SET f.deepfakePath = :newPath WHERE f.id = :id")
    int updateDeepfakePathById(@Param("id") Integer id, @Param("newPath") String newPath);
}
