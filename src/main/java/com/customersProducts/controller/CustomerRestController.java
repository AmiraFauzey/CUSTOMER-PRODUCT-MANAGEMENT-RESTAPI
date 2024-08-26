package com.customersProducts.controller;

import com.customersProducts.dto.CustomerDto;
import com.customersProducts.dto.CustomerResponse;
import com.customersProducts.exception.SystemException;
import com.customersProducts.service.CustomerService;
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
@RequestMapping(value = "/api/v1/customer")
@Tag(
        name = "CUSTOMER",
        description = "CUSTOMER REST API Specification"
)
public class CustomerRestController {

    private final CustomerService customerService;

    @PostMapping(value = "/create")
    @Operation(
            summary = "Create Customer REST API",
            description = "Create Customer REST API is used to save customer into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody CustomerDto dto){
        CustomerDto savedCustomer = customerService.createCustomer(dto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{customerId}")
    @Operation(
            summary = "Update Customer REST API",
            description = "Update Customer REST API is used to update customer into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody  CustomerDto dto, @PathVariable(name = "customerId") Integer customerId) throws SystemException {
        CustomerDto updateCustomer = customerService.updateCustomer(dto,customerId);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Customer By Id REST API",
            description = "Get Customer By Id REST API is used to get single customer from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = "customerId") Integer customerId){
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @Operation(
            summary = "Get All Customers REST API",
            description = "Get All Customers REST API is used to fetch all the customers from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public CustomerResponse getAllCustomers(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Delete Customer REST API",
            description = "Delete Customer REST API is used to delete a particular customer from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "customerId") Integer customerId){

        customerService.deleteCustomerById(customerId);

        return new ResponseEntity<>("Customer deleted successfully.", HttpStatus.OK);
    }
}
