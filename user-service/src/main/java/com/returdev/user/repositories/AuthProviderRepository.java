package com.returdev.user.repositories;

import com.returdev.user.entities.AuthProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing {@link AuthProviderEntity} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing CRUD operations
 * and additional methods for interacting with the "auth_providers" table in the database.
 * </p>
 * <p>
 * Spring Data JPA automatically provides the implementation for this repository
 * and enables query derivation based on method names.
 * </p>
 */
@Repository
public interface AuthProviderRepository extends JpaRepository<AuthProviderEntity, Long> {}
