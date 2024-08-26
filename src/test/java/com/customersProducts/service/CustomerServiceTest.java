package com.customersProducts.service;

import com.customersProducts.dto.CustomerDto;
import com.customersProducts.dto.CustomerResponse;
import com.customersProducts.entity.Customer;
import com.customersProducts.exception.SystemErrorCode;
import com.customersProducts.exception.SystemException;
import com.customersProducts.mapper.CustomerMapper;
import com.customersProducts.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    //which service we want to test
    @InjectMocks
    private CustomerService customerService;

    //Declare the dependencies
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer() {
        //Given
        CustomerDto dto = new CustomerDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setContactNo("0138879967");
        dto.setEmailPersonal("john.doe@gmail.com");
        dto.setEmailOffice("john.doe@maybank.com");
        dto.setFamilyContactNo("0137689967");
        dto.setFamilyName("Doe");

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setContactNo("0138879967");
        customer.setEmailPersonal("john.doe@gmail.com");
        customer.setEmailOffice("john.doe@maybank.com");
        customer.setFamilyContactNo("0137689967");
        customer.setFamilyName("Doe");

        Customer Savedcustomer = new Customer();
        Savedcustomer.setFirstName("John");
        Savedcustomer.setLastName("Doe");
        Savedcustomer.setContactNo("0138879967");
        Savedcustomer.setEmailPersonal("john.doe@gmail.com");
        Savedcustomer.setEmailOffice("john.doe@maybank.com");
        Savedcustomer.setFamilyContactNo("0137689967");
        Savedcustomer.setFamilyName("Doe");
        Savedcustomer.setCustomerId(1);


        //mock the calls
        when(customerMapper.customerDTOToCustomer(dto)).
                thenReturn(customer);
        when(customerRepository.save(customer)).
                thenReturn(Savedcustomer);
        when(customerMapper.customerToCustomerDTO(Savedcustomer)).
                thenReturn(dto);


        //When
        CustomerDto responseDto = customerService.createCustomer(dto);

        //Then
        assertEquals(dto.getFirstName(), responseDto.getFirstName());
        assertEquals(dto.getLastName(), responseDto.getLastName());
        assertEquals(dto.getContactNo(), responseDto.getContactNo());
        assertEquals(dto.getEmailPersonal(), responseDto.getEmailPersonal());
        assertEquals(dto.getEmailOffice(), responseDto.getEmailOffice());
        assertEquals(dto.getFamilyContactNo(), responseDto.getFamilyContactNo());
        assertEquals(dto.getFamilyName(), responseDto.getFamilyName());

        verify(customerMapper,times(1)).customerDTOToCustomer(dto);
        verify(customerRepository, times(1)).save(customer);
        verify(customerMapper,times(1)).customerToCustomerDTO(Savedcustomer);
    }

    @Test
    void updateCustomer() {
        // Given
        CustomerDto dto = new CustomerDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setContactNo("0138879967");
        dto.setEmailPersonal("john.doe@gmail.com");
        dto.setEmailOffice("john.doe@maybank.com");
        dto.setFamilyContactNo("0137689967");
        dto.setFamilyName("Doe");

        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(3);
        existingCustomer.setFirstName("Awang salleh");
        existingCustomer.setLastName("ahmad ali");
        existingCustomer.setContactNo("0127383638");
        existingCustomer.setEmailPersonal("awang.salleh@gmail.com");
        existingCustomer.setEmailOffice("awang.salleh@tnb.com.my");
        existingCustomer.setFamilyContactNo("019838383");
        existingCustomer.setFamilyName("awang salleh");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(3);
        updatedCustomer.setFirstName("John");
        updatedCustomer.setLastName("Doe");
        updatedCustomer.setContactNo("0138879967");
        updatedCustomer.setEmailPersonal("john.doe@gmail.com");
        updatedCustomer.setEmailOffice("john.doe@maybank.com");
        updatedCustomer.setFamilyContactNo("0137689967");
        updatedCustomer.setFamilyName("Doe");

        // Mock repository behavior
        when(customerRepository.findById(3)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);
        when(customerMapper.customerToCustomerDTO(updatedCustomer)).thenReturn(dto);

        // When
        CustomerDto responseDto = customerService.updateCustomer(dto, 3);

        // Then
        assertNotNull(responseDto, "ResponseDto should not be null");
        assertEquals(dto.getFirstName(), responseDto.getFirstName());
        assertEquals(dto.getLastName(), responseDto.getLastName());
        assertEquals(dto.getContactNo(), responseDto.getContactNo());
        assertEquals(dto.getEmailPersonal(), responseDto.getEmailPersonal());
        assertEquals(dto.getEmailOffice(), responseDto.getEmailOffice());
        assertEquals(dto.getFamilyContactNo(), responseDto.getFamilyContactNo());
        assertEquals(dto.getFamilyName(), responseDto.getFamilyName());

        verify(customerRepository, times(1)).findById(3);
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(customerMapper, times(1)).customerToCustomerDTO(updatedCustomer);
    }

    @Test
    void getCustomerById() {
        // Given
        Integer customerId = 10;
        Customer customer = new Customer();
        customer.setFirstName("kuntum");
        customer.setLastName("mawar");
        customer.setContactNo("0139879927");
        customer.setEmailPersonal("kuntum@gmail.com");
        customer.setEmailOffice("kuntum@gmail.com");
        customer.setFamilyContactNo("0198282278");
        customer.setFamilyName("awang");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("kuntum");
        customerDto.setLastName("mawar");
        customerDto.setContactNo("0139879927");
        customerDto.setEmailPersonal("kuntum@gmail.com");
        customerDto.setEmailOffice("kuntum@gmail.com");
        customerDto.setFamilyContactNo("0198282278");
        customerDto.setFamilyName("awang");

        // Mock the repository and mapper
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDto);

        // When
        CustomerDto result = customerService.getCustomerById(customerId);

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(customerDto.getFirstName(), result.getFirstName());
        assertEquals(customerDto.getLastName(), result.getLastName());
        assertEquals(customerDto.getContactNo(), result.getContactNo());
        assertEquals(customerDto.getEmailPersonal(), result.getEmailPersonal());
        assertEquals(customerDto.getEmailOffice(), result.getEmailOffice());
        assertEquals(customerDto.getFamilyContactNo(), result.getFamilyContactNo());
        assertEquals(customerDto.getFamilyName(), result.getFamilyName());

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerMapper, times(1)).customerToCustomerDTO(customer);
    }

    @Test
    void getAllCustomers() {
        // Given
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setContactNo("0139876789");
        customer.setEmailPersonal("john.doe@gmail.com");
        customer.setEmailOffice("john.doe@maybank.com");
        customer.setFamilyName("stewart");
        customer.setFamilyContactNo("01298788987");
        customerList.add(customer);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("John");
        customerDto.setLastName("Doe");
        customerDto.setContactNo("0139876789");
        customerDto.setEmailPersonal("john.doe@gmail.com");
        customerDto.setEmailOffice("john.doe@maybank.com");
        customerDto.setFamilyContactNo("01298788987");
        customerDto.setFamilyName("stewart");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("lastName").ascending());
        Page<Customer> pagedCustomers = new PageImpl<>(customerList, pageable, customerList.size());

        // Mock the repository and mapper
        when(customerRepository.findAll(pageable)).thenReturn(pagedCustomers);
        when(customerMapper.customerToCustomerDTO(any(Customer.class))).thenReturn(customerDto);

        // When
        CustomerResponse response = customerService.getAllCustomers(0, 10, "lastName", "ASC");

        // Then
        assertNotNull(response);
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getTotalElements());
        assertTrue(response.isLast());
        assertEquals(1, response.getContent().size());
        assertEquals("John", response.getContent().get(0).getFirstName());
        assertEquals("Doe", response.getContent().get(0).getLastName());
    }

    @Test
    void deleteCustomerById_success() {
        Integer customerId = 1;
        Customer customer = new Customer(); // Initialize with test data
        CustomerDto customerDto = new CustomerDto(); // Initialize with expected DTO data

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals(customerDto, result);
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerMapper, times(1)).customerToCustomerDTO(customer);
    }

    @Test
    void deleteCustomerById_CustomerDoesNotExist_ShouldThrowException() {
        // Arrange
        Integer customerId = 1;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        SystemException thrownException = assertThrows(SystemException.class, () -> customerService.deleteCustomerById(customerId));

        // Verify exception details
        assertEquals(SystemErrorCode.CUSTOMER_NOT_FOUND.getMessage(), thrownException.getMessage());
    }

}