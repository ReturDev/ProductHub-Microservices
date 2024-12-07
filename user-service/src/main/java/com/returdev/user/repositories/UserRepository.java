package com.returdev.user.repositories;

import com.returdev.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Repository interface for managing {@link UserEntity} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom queries
 * for interacting with the "users" table in the database.
 * </p>
 * <p>
 * The methods in this interface allow for operations such as finding a user by email,
 * updating user details (name, surnames, password), and managing user roles.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Finds a {@link UserEntity} by its email.
     *
     * @param email the email of the user
     * @return the {@link UserEntity} associated with the given email, or null if no user is found
     */
    Optional<UserEntity> findByEmail(String email);


    /**
     * Updates the name and surnames of a user in the database.
     * <p>
     * This query updates the {@link UserEntity} with the specified {@code id} by setting the new values for
     * the {@code name} and {@code surnames} fields.
     * </p>
     *
     * @param id The unique identifier of the user whose name and surnames are being updated.
     * @param newName The new value for the user's name.
     * @param newSurnames The new value for the user's surnames.
     * @return The number of rows affected by the update (should be 1 if the user exists and the update is successful).
     */
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity e SET e.name = :name, e.surnames = :surnames WHERE e.id = :id")
    int updateUserFullName(@Param("id") UUID id, @Param("name") String newName, @Param("surnames") String newSurnames);


    /**
     * Updates the hashed password of a user with the specified ID.
     *
     * @param id the ID of the user
     * @param newHashPassword the new hashed password to set for the user
     * @return the number of rows affected (should be 1 if the update is successful)
     */
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity e SET e.hashPassword = :password WHERE e.id = :id")
    int updateUserHashPassword(@Param("id") UUID id, @Param("password") String newHashPassword);

    /**
     * Deletes specific roles associated with a user in the database.
     * <p>
     * This query removes entries from the `user_roles` table where the `user_id` matches the provided {@code userId},
     * and the roles are present in the provided {@code roles} set.
     * </p>
     *
     * <p>
     * This operation is transactional and modifies the database directly using a native SQL query.
     * </p>
     *
     * @param userId The unique identifier of the user whose roles are to be deleted.
     * @param roles A set of role names to be removed from the user.
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_roles WHERE user_id = :userId AND role IN :roles", nativeQuery = true)
    void deleteUserRoles(@Param("userId") UUID userId, @Param("roles") Set<String> roles);


    /**
     * Adds a new role for the user with the specified ID.
     *
     * @param id the ID of the user to whom the role is to be assigned
     * @param newRole the role to assign to the user
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_roles(user_id, role) VALUES (:id, :role)", nativeQuery = true)
    void saveUserRole(@Param("id") UUID id, @Param("role") String newRole);

}

