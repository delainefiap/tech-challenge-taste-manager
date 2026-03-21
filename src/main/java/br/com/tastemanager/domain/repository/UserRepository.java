package br.com.tastemanager.domain.repository;

import br.com.tastemanager.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Query("SELECT u.id FROM User u WHERE u.login = :login")
    Optional<Long> findIdByLogin(String login);

    @Query("SELECT COUNT(u) FROM User u WHERE u.userTypeId.id = :id")
    Long countByUserTypeId(Long id);
    List<User> findByNameContainingIgnoreCase(String name);

    @Query("SELECT u FROM User u WHERE REPLACE(LOWER(u.name), ' ', '') LIKE LOWER(CONCAT('%', REPLACE(:name, ' ', ''), '%'))")
    List<User> findByNameIgnoreSpaces(@Param("name") String name);

}
