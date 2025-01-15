package com.ashar.job.recruitment.management.Repository;

import com.ashar.job.recruitment.management.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_roles ur WHERE ur.user_id = :uuid", nativeQuery = true)
    void deleteUserRoleMapping(UUID uuid);

    @Query(value = "Select * from users", nativeQuery = true)
    public List<User> testFindAll();

}
