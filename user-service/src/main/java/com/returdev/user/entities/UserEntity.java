package com.returdev.user.entities;

import com.returdev.user.util.converter.UUIDBinaryConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;


/**
 * Entity class representing a user in the system.
 * This class maps to the "users" table in the database and contains user-related information such as name, email, password,
 * roles, and authentication providers.
 * <p>
 * The class is annotated with JPA annotations to define the entity's structure and relationships.
 * It also includes lifecycle hooks to handle timestamping for creation and updates.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * Unique identifier for the user, stored as a binary UUID.
     * This field is generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "Binary(16)")
    @Convert(converter = UUIDBinaryConverter.class)
    private UUID id;

    /**
     * The name of the user.
     * This field is required and can have a maximum length of 50 characters.
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * The surname(s) of the user.
     * This field is required and can have a maximum length of 150 characters.
     */
    @Column(name = "surnames", nullable = false, length = 150)
    private String surnames;

    /**
     * The email address of the user.
     * This field is required, must be unique, and is used for user identification.
     */
    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    /**
     * The hashed password of the user.
     */
    @Column(name = "password")
    private String hashPassword;

    /**
     * The timestamp of when the user was created.
     * This field is automatically set to the current time when the entity is persisted.
     */
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    /**
     * The timestamp of the last update made to the user entity.
     * This field is automatically updated whenever the entity is modified.
     */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * A set of roles associated with the user.
     * The roles are stored in the "user_roles" table and fetched eagerly.
     * Each role is an enumerated value.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * A set of authentication providers associated with the user.
     * This represents external authentication providers used for user login (e.g., Google, Facebook).
     * The set of providers will be removed if the user entity is deleted.
     */
    @OneToMany(
            cascade = {CascadeType.REMOVE},
            orphanRemoval = true
    )
    private Set<AuthProviderEntity> authProviders;

    /**
     * Lifecycle hook method that is called before the entity is persisted (saved).
     * Sets the creation and last update timestamps to the current time.
     */
    @PrePersist
    private void prePersist() {
        Instant instant = Instant.now();
        this.createdAt = instant;
        this.updatedAt = instant;
    }

    /**
     * Lifecycle hook method that is called before the entity is updated.
     * Sets the last update timestamp to the current time.
     */
    @PreUpdate
    private void preUpdate(){
        this.updatedAt = Instant.now();
    }
}


