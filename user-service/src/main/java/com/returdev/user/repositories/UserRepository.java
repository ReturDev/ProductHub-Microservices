package com.returdev.user.repositories;

import com.returdev.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    UserEntity findUserEntityByEmail(String email);

    /**
     * Updates the name of a user with the specified ID.
     *
     * @param id the ID of the user
     * @param newName the new name to set for the user
     * @return the number of rows affected (should be 1 if the update is successful)
     */
    @Query("UPDATE UserEntity e SET e.name = :name WHERE e.id = :id")
    int updateUserName(@Param("id") UUID id, @Param("name") String newName);

    /**
     * Updates the surnames of a user with the specified ID.
     *
     * @param id the ID of the user
     * @param newSurnames the new surnames to set for the user
     * @return the number of rows affected (should be 1 if the update is successful)
     */
    @Query("UPDATE UserEntity e SET e.surnames = :surnames WHERE e.id = :id")
    int updateUserSurnames(@Param("id") UUID id, @Param("surnames") String newSurnames);

    /**
     * Updates the hashed password of a user with the specified ID.
     *
     * @param id the ID of the user
     * @param newHashPassword the new hashed password to set for the user
     * @return the number of rows affected (should be 1 if the update is successful)
     */
    @Query("UPDATE UserEntity e SET e.hashPassword = :password WHERE e.id = :id")
    int updateUserHashPassword(@Param("id") UUID id, @Param("password") String newHashPassword);

    /**
     * Deletes the roles associated with the user having the specified ID.
     *
     * @param id the ID of the user whose roles are to be deleted
     * @return the number of rows affected (should be > 0 if roles are deleted)
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_roles WHERE id = :id", nativeQuery = true)
    int deleteUserRoles(@Param("id") UUID id);

    /**
     * Adds a new role for the user with the specified ID.
     *
     * @param id the ID of the user to whom the role is to be assigned
     * @param newRole the role to assign to the user
     * @return the number of rows affected (should be 1 if the role is added successfully)
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_roles(user_id, role) VALUES (:id, :role)", nativeQuery = true)
    int saveUserRole(@Param("id") UUID id, @Param("role") String newRole);

}

