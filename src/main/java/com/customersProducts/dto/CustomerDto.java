package com.customersProducts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Schema(
        description = "CustomerDto Model Information"
)
public class CustomerDto implements Serializable {

    private Integer customerId;

    @NotBlank(message = "Name should not be empty")
    private String firstName;

    @NotBlank(message = "Last Name should not be empty")
    private String lastName;

    @NotBlank(message = "Office email should not be empty")
    @Email(message = "Invalid email")
    private String emailOffice;

    @NotBlank(message = "Personal email should not be empty")
    @Email(message = "Invalid email")
    private String emailPersonal;

    @NotBlank(message = "Phone number should not be empty")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Contact number must be 10 digits"
    )
    private String contactNo;

    @NotBlank(message = "Family Name should not be empty")
    private String familyName;

    @NotBlank(message = "Family Phone number should not be empty")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Contact family number must be 10 digits"
    )
    private String familyContactNo;

    private Timestamp createdDate;

    private Timestamp lastModifiedDate;
}
