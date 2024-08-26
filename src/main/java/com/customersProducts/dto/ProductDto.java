package com.customersProducts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Schema(
        description = "ProductDto Model Information"
)
public class ProductDto {

    private Integer bookId;

    @Size(min = 2, message = "Book title should have at least 10 characters")
    private String bookTitle;

    @NotNull(message = "Book price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Book price must be greater 0.00")
    private BigDecimal bookPrice;

    @NotNull(message = "Book quantity cannot be null")
    @Min(value = 1, message = "Book quantity must be one or greater")
    private Integer bookQuantity;

    private Timestamp createdDate;

    private Timestamp lastModifiedDate;
}
