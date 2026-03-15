package br.com.tastemanager.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "restaurant_item_number", nullable = false)
    private Long restaurantItemNumber;

    @NotBlank(message = "O nome não pode estar em branco")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Você deve fornecer uma descrição")
    @Column(nullable = false, length = 500)
    private String description;

    @Positive(message = "O preço deve ser um valor positivo")
    @Column(nullable = false)
    private Double price;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "available_only_at_restaurant", nullable = false)
    private Boolean availableOnlyAtRestaurant = false;
}
