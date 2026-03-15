package br.com.tastemanager.domain.repository;

import br.com.tastemanager.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByRestaurantIdOrderByRestaurantItemNumberAsc(Long restaurantId);

    Optional<Menu> findTopByRestaurantIdOrderByRestaurantItemNumberDesc(Long restaurantId);

    @Query("SELECT m FROM Menu m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Menu> findByNameContainingIgnoreCase(@Param("name") String name);


    List<Menu> findByAvailableOnlyAtRestaurant(Boolean availableOnlyAtRestaurant);


    List<Menu> findByRestaurantIdAndAvailableOnlyAtRestaurant(Long restaurantId, Boolean availableOnlyAtRestaurant);

    boolean existsByNameAndRestaurantId(String name, Long restaurantId);

    Optional<Menu> findByIdAndRestaurantId(Long id, Long restaurantId);

    boolean existsByRestaurantId(Long restaurantId);

    Optional<Menu> findByRestaurantIdAndRestaurantItemNumber(Long restaurantId, Long restaurantItemNumber);
}
