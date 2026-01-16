package br.com.tastemanager.repository;

import br.com.tastemanager.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImpTest {

//    private UserRepositoryImp userRepository;
//    private JdbcClient jdbcClient;
//
//    @BeforeEach
//    void setUp() {
//        DataSource dataSource = new DriverManagerDataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
//        jdbcClient = JdbcClient.create(dataSource);
//        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
//        userRepository = new UserRepositoryImp(jdbcClient, clock);
//
//        jdbcClient.sql("DROP TABLE IF EXISTS users").update();
//
//        jdbcClient.sql("""
//            CREATE TABLE users (
//                id BIGINT AUTO_INCREMENT PRIMARY KEY,
//                name VARCHAR(255),
//                email VARCHAR(255),
//                login VARCHAR(255),
//                password VARCHAR(255),
//                created_at TIMESTAMP,
//                last_update TIMESTAMP,
//                type_person VARCHAR(50),
//                address VARCHAR(255)
//            )
//        """).update();
//    }
//
//    @Test
//    void shouldSaveUser() {
//        User user = new User();
//        user.setName("John Doe");
//        user.setEmail("john.doe@example.com");
//        user.setLogin("johndoe");
//        user.setPassword("password123");
//        user.setTypePerson("Individual");
//        user.setAddress("123 Main St");
//
////        Integer rowsAffected = userRepository.save(user);
////        assertEquals(1, rowsAffected);
//    }
//
//    @Test
//    void shouldFindUserById() {
//        jdbcClient.sql("""
//            INSERT INTO users (name, email, login, password, created_at, type_person, address)
//            VALUES ('John Doe', 'john.doe@example.com', 'johndoe', 'password123', NOW(), 'Individual', '123 Main St')
//        """).update();
//
////        Optional<User> user = userRepository.findById(1L);
////        assertTrue(user.isPresent());
////        assertEquals("John Doe", user.get().getName());
//    }
//
//    @Test
//    void shouldUpdateUser() {
//        jdbcClient.sql("""
//            INSERT INTO users (name, email, login, password, created_at, type_person, address)
//            VALUES ('John Doe', 'john.doe@example.com', 'johndoe', 'password123', NOW(), 'Individual', '123 Main St')
//        """).update();
//
//        User updatedUser = new User();
//        updatedUser.setName("Jane Doe");
//        updatedUser.setEmail("jane.doe@example.com");
//        updatedUser.setTypePerson("Corporate");
//        updatedUser.setAddress("456 Elm St");
//
//        Integer rowsAffected = userRepository.updateUser(1L, updatedUser);
//        assertEquals(1, rowsAffected);
//
//        Optional<User> user = userRepository.findById(1L);
//        assertTrue(user.isPresent());
//        assertEquals("Jane Doe", user.get().getName());
//    }
//
//    @Test
//    void shouldDeleteUser() {
//        jdbcClient.sql("""
//            INSERT INTO users (name, email, login, password, created_at, type_person, address)
//            VALUES ('John Doe', 'john.doe@example.com', 'johndoe', 'password123', NOW(), 'Individual', '123 Main St')
//        """).update();
//
//        Integer rowsAffected = userRepository.deleteUser(1L);
//        assertEquals(1, rowsAffected);
//
//        Optional<User> user = userRepository.findById(1L);
//        assertFalse(user.isPresent());
//    }
//
//    @Test
//    void shouldFindUserByLogin() {
//        jdbcClient.sql("""
//            INSERT INTO users (name, email, login, password, created_at, type_person, address)
//            VALUES ('John Doe', 'john.doe@example.com', 'johndoe', 'password123', NOW(), 'Individual', '123 Main St')
//        """).update();
//
//        Optional<User> user = userRepository.findUserByLogin("johndoe");
//        assertTrue(user.isPresent());
//        assertEquals("John Doe", user.get().getName());
//    }
//
//    @Test
//    void shouldFindIdByLogin() {
//        jdbcClient.sql("""
//            INSERT INTO users (name, email, login, password, created_at, type_person, address)
//            VALUES ('John Doe', 'john.doe@example.com', 'johndoe', 'password123', NOW(), 'Individual', '123 Main St')
//        """).update();
//
//        Optional<Long> userId = userRepository.findIdByLogin("johndoe");
//        assertTrue(userId.isPresent());
//        assertEquals(1L, userId.get());
//    }
//
//    @Test
//    void shouldFindAllUsers() {
//        jdbcClient.sql("""
//            INSERT INTO users (name, email, login, password, created_at, type_person, address)
//            VALUES
//            ('John Doe', 'john.doe@example.com', 'johndoe', 'password123', NOW(), 'Individual', '123 Main St'),
//            ('Jane Doe', 'jane.doe@example.com', 'janedoe', 'password456', NOW(), 'Corporate', '456 Elm St')
//        """).update();
//
//        List<User> users = userRepository.findAll(10, 0);
//        assertEquals(2, users.size());
//    }
}