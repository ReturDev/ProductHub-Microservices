package com.returdev.user.entities;

import com.returdev.user.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Entity class representing an authentication provider associated with a user.
 * This class maps to the "auth_providers" table in the database and stores
 * information about the provider's name, provider ID, and the associated user.
 *<p>
 * It also enforces a unique constraint on the combination of the "name" and "provider_id" columns.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "auth_providers",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"name", "provider_id"}
        )
)
public class AuthProviderEntity {

    /**
     * Unique identifier for the authentication provider entity.
     * This field is automatically generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the authentication provider (e.g., "Google", "Facebook").
     * This field is required and cannot be updated once set.
     * The value is stored as a string representation of the enum.
     */
    @Column(name = "name", nullable = false, updatable = false)
    @Enumerated(value = EnumType.STRING)
    private AuthProvider name;

    /**
     * The unique identifier assigned by the authentication provider to the user.
     * This field is required and cannot be updated once set.
     */
    @Column(name = "provider_id", nullable = false, updatable = false)
    private String providerId;

    /**
     * The unique identifier of the user to whom the authentication provider is linked.
     * This field is required and cannot be updated once set.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UUID userId;
}

