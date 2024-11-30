package com.returdev.user.enums;


/**
 * Enum representing the different roles a user can have in the system.
 * <p>
 * This enum defines a set of user roles with varying levels of privileges.
 * </p>
 * <ul>
 *     <li>{@code GOD} - A super user with the highest level of privileges.</li>
 *     <li>{@code ADMIN} - A user with administrative privileges.</li>
 *     <li>{@code MANAGER} - A user with managerial privileges.</li>
 *     <li>{@code USER} - A standard user with basic privileges.</li>
 *     <li>{@code SUPPORT} - A user with support privileges, typically for handling customer inquiries.</li>
 * </ul>
 * These roles help determine access control and authorization across the system.
 */
public enum UserRole {
    /**
     * The GOD role represents a superuser with the highest level of privileges,
     * capable of performing all system operations.
     */
    GOD,

    /**
     * The ADMIN role represents an administrator with full access to manage users,
     * configurations, and other sensitive system resources.
     */
    ADMIN,

    /**
     * The MANAGER role represents a user with management-level access, typically
     * to oversee specific areas or teams within the system.
     */
    MANAGER,

    /**
     * The USER role represents a standard user with basic access to the system's
     * features, typically with limited permissions.
     */
    USER,

    /**
     * The SUPPORT role represents a user with privileges to manage customer inquiries,
     * assist users, and access support-related tools.
     */
    SUPPORT
}

