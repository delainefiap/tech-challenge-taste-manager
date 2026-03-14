package br.com.tastemanager.domain.repository;

import br.com.tastemanager.domain.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);


    @Query("SELECT m FROM MenuItem m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<MenuItem> findByNameContainingIgnoreCase(@Param("name") String name);


    List<MenuItem> findByAvailableOnlyAtRestaurant(Boolean availableOnlyAtRestaurant);


    List<MenuItem> findByRestaurantIdAndAvailableOnlyAtRestaurant(Long restaurantId, Boolean availableOnlyAtRestaurant);

    boolean existsByNameAndRestaurantId(String name, Long restaurantId);

    Optional<MenuItem> findByIdAndRestaurantId(Long id, Long restaurantId);

    boolean existsByRestaurantId(Long restaurantId);
}
