package com.customersProducts.controller;

import com.customersProducts.dto.ProductDto;
import com.customersProducts.dto.ProductResponse;
import com.customersProducts.exception.SystemException;
import com.customersProducts.service.ProductService;
import com.customersProducts.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/product")
@Tag(
        name = "PRODUCT",
        description = "PRODUCT REST API Specification"
)
public class ProductRestController {

    private final ProductService productService;

    @PostMapping(value = "/create")
    @Operation(
            summary = "Create Customer REST API",
            description = "Create Customer REST API is used to save customer into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto dto){
        ProductDto savedProduct = productService.createProduct(dto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{bookId}")
    @Operation(
            summary = "Update Customer REST API",
            description = "Update Customer REST API is used to update customer into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<ProductDto> update(@Valid @RequestBody  ProductDto dto, @PathVariable(name = "bookId") Integer bookId){
        ProductDto updateProduct = productService.updateProduct(dto,bookId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Product By Id REST API",
            description = "Get Product By Id REST API is used to get single product from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/{bookId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(name = "bookId") Integer bookId){
        return ResponseEntity.ok(productService.getProductById(bookId));
    }

    @Operation(
            summary = "Get All Products REST API",
            description = "Get All Products REST API is used to fetch all the products from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public ProductResponse getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.getAllProducts(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Delete Product REST API",
            description = "Delete Product REST API is used to delete a particular Product from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "bookId") Integer bookId){

        productService.deleteProductById(bookId);

        return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
    }

}
