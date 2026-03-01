package br.com.tastemanager.repository;

import br.com.tastemanager.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    boolean existsByName(String name);

    boolean existsByOwnerId(Long ownerId);

}