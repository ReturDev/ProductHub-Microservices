package com.returdev.product.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a contact information record.
 * This includes details such as name, phone number, email, and observations.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contact_info")
public class ContactInfoEntity {

    /**
     * Unique identifier for each contact information record, generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the contact person.
     * This field must not be blank, must be unique, and has a length between 3 and 50 characters.
     */
    @NotBlank(message = "${validation.not_blank.message}")
    @Size(min = 3, max = 50, message = "${validation.size.message}")
    @Column(name = "name", unique = true, length = 50, nullable = false)
    private String name;

    /**
     * Additional observations about the contact.
     * This field cannot be null and has a maximum length of 150 characters.
     */
    @NotNull(message = "${validation.not_null.message}")
    @Size(max = 150, message = "${validation.size.max.message}")
    @Column(name = "observations", length = 150, nullable = false)
    private String observations;

    /**
     * The contact's phone number.
     * Must not be blank, must be unique, and must match the specified regex pattern
     * for phone numbers (8 to 15 digits, allowing optional country code).
     */
    @NotBlank(message = "${validation.not_blank.message}")
    @Size(min = 3, max = 15, message = "${validation.size.message}")
    @Pattern(
            regexp = "^\\+?[0-9. ()-]{8,15}$",
            message = "${validation.phone_format.message}"
    )
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    /**
     * The email address of the contact.
     * This field must follow the standard email format.
     */
    @Email(message = "${validation.email.message}")
    @Column(name = "email")
    private String email;

    /**
     * Indicates if this contact is for commercial purposes.
     * This field cannot be null.
     */
    @Column(name = "is_commercial", nullable = false)
    private boolean isCommercial;

    /**
     * The supplier to which this contact belongs.
     * This association is required.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

}

