package br.com.tastemanager.repository;

import br.com.tastemanager.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByRestaurantId(Long restaurantId);

    boolean existsByRestaurantId(Long restaurantId);

}