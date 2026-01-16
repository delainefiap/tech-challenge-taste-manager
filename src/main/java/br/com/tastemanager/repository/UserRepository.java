package br.com.tastemanager.repository;

import br.com.tastemanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Query("SELECT u.id FROM User u WHERE u.login = :login")
    Optional<Long> findIdByLogin(String login);

    @Query("SELECT COUNT(u) FROM User u WHERE u.userTypeId.id = :id")
    Long countByUserTypeId(Long id);
    List<User> findByNameContainingIgnoreCase(String name);
}
