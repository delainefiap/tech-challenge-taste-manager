package br.com.tastemanager.repository;

import br.com.tastemanager.entity.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemMenuRepository extends JpaRepository<ItemMenu, Long> {

    List<ItemMenu> findByMenuRestaurantId(Long restaurantId);

    Optional<ItemMenu> findByMenuMenuIdAndItemMenuId(Long menuId, Long itemId);

    @Query("SELECT COUNT(i) FROM ItemMenu i WHERE i.menu.restaurant.id = :restaurantId")
    Long countByRestaurantId(@Param("restaurantId") Long restaurantId);

    List<ItemMenu> findByNameContainingIgnoreCase(String name);

    @Query("SELECT i FROM ItemMenu i WHERE i.menu.restaurant.owner.id = :ownerId")
    List<ItemMenu> findByRestaurantOwnerId(@Param("ownerId") Long ownerId);

    List<ItemMenu> findByAvailableOnlyAtRestaurant(boolean availableOnlyAtRestaurant);
}
