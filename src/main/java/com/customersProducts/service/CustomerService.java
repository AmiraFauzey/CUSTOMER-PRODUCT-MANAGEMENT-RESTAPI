package com.customersProducts.service;

import com.customersProducts.dto.CustomerDto;
import com.customersProducts.dto.CustomerResponse;
import com.customersProducts.entity.Customer;
import com.customersProducts.exception.SystemErrorCode;
import com.customersProducts.exception.SystemException;
import com.customersProducts.mapper.CustomerMapper;
import com.customersProducts.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    @Autowired
    private final CustomerMapper customerMapper;

    /*To create customer*/
    @Transactional(rollbackFor = Exception.class)
    public CustomerDto createCustomer(CustomerDto dto){
        Customer customer = customerMapper.customerDTOToCustomer(dto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }


    /*To update customer*/
    @Transactional(rollbackFor = Exception.class)
    public CustomerDto updateCustomer(CustomerDto dto, Integer customerId) throws SystemException{

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new SystemException(SystemErrorCode.CUSTOMER_NOT_FOUND));

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmailOffice(dto.getEmailOffice());
        customer.setEmailPersonal(dto.getEmailPersonal());
        customer.setFamilyName(dto.getFamilyName());
        customer.setContactNo(dto.getContactNo());
        customer.setFamilyContactNo(dto.getFamilyContactNo());

        Customer updatedCustomer = customerRepository.save(customer);

        return customerMapper.customerToCustomerDTO(updatedCustomer);
    }

    /*To view customer*/
    @Transactional(readOnly = true)
    public CustomerDto getCustomerById(Integer customerId) throws SystemException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new SystemException(SystemErrorCode.CUSTOMER_NOT_FOUND));
        return customerMapper.customerToCustomerDTO(customer);
    }

    /*To view all customer*/
    public CustomerResponse getAllCustomers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        Page<Customer> customers = customerRepository.findAll(pageable);
        //get content for page object
        List<Customer> listOfCustomers = customers.getContent();
        List<CustomerDto> contents = listOfCustomers.stream().map(customerMapper::customerToCustomerDTO).collect(Collectors.toList());

        CustomerResponse custResponse = new CustomerResponse();
        custResponse.setContent(contents);
        custResponse.setPageNo(customers.getNumber());
        custResponse.setPageSize(customers.getSize());
        custResponse.setTotalElements(customers.getTotalElements());
        custResponse.setTotalPages(customers.getTotalPages());
        custResponse.setLast(customers.isLast());

        return custResponse;
    }

    /*To delete customer*/
    public void deleteCustomerById(Integer customerId) {
        // get customer by id from the database
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new SystemException(SystemErrorCode.CUSTOMER_NOT_FOUND));
        customerRepository.delete(customer);
    }
}
