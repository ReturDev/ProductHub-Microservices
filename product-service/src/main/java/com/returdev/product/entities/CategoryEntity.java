package com.returdev.product.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a category entity in the system.
 * This entity is mapped to the "category" table in the database.
 *
 * <p>Each category is uniquely identified by an ID and has a name, a summary,
 * and a list of associated products.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "category")
@Entity
public class CategoryEntity {

    /**
     * The unique identifier for the category.
     * This value is automatically generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the category.
     * Cannot be blank, must be unique, and has a length between 3 and 50 characters.
     */
    @NotBlank(message = "${validation.not_blank.message}")
    @Size(min = 3, max = 50, message = "${validation.size.message}")
    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    /**
     * A brief summary of the category.
     * Cannot be null, has a maximum length of 150 characters, and cannot be empty.
     */
    @NotNull(message = "${validation.not_null.message}")
    @Size(max = 150, message = "${validation.size.max.message}")
    @Column(name = "summary", nullable = false, length = 150)
    private String summary;

}

