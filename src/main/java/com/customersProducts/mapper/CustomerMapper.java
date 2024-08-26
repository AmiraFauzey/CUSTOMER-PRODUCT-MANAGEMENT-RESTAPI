package com.customersProducts.mapper;

import com.customersProducts.dto.CustomerDto;
import com.customersProducts.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto customerToCustomerDTO(Customer customer);
    Customer customerDTOToCustomer(CustomerDto customerDto);
}
