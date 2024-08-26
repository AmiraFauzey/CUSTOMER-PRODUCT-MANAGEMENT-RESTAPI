package com.customersProducts.service;

import com.customersProducts.dto.ProductDto;
import com.customersProducts.dto.ProductResponse;
import com.customersProducts.entity.Product;
import com.customersProducts.exception.SystemErrorCode;
import com.customersProducts.exception.SystemException;
import com.customersProducts.mapper.ProductMapper;
import com.customersProducts.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    //which service we want to test
    @InjectMocks
    private ProductService productService;

    //Declare the dependencies
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        //Given
        ProductDto dto = new ProductDto();
        dto.setBookTitle("Easy recipe books for the newbie");
        dto.setBookPrice(BigDecimal.valueOf(33.00));
        dto.setBookQuantity(100);

        Product product = new Product();
        product.setBookTitle("Easy recipe books for the newbie");
        product.setBookPrice(BigDecimal.valueOf(33.00));
        product.setBookQuantity(100);

        Product savedProduct = new Product();
        savedProduct.setBookTitle("Easy recipe books for the newbie");
        savedProduct.setBookPrice(BigDecimal.valueOf(33.00));
        savedProduct.setBookQuantity(100);

        //mock the calls
        when(productMapper.productDTOToProduct(dto)).
                thenReturn(product);
        when(productRepository.save(product)).
                thenReturn(savedProduct);
        when(productMapper.productToProductDTO(savedProduct)).
                thenReturn(dto);

        //when
        ProductDto responseDto = productService.createProduct(dto);

        //Then
        assertEquals(dto.getBookTitle(), responseDto.getBookTitle());
        assertEquals(dto.getBookPrice(), responseDto.getBookPrice());
        assertEquals(dto.getBookPrice(), responseDto.getBookPrice());

        verify(productMapper,times(1)).productDTOToProduct(dto);
        verify(productRepository, times(1)).save(product);
        verify(productMapper,times(1)).productToProductDTO(savedProduct);

    }

    @Test
    void updateProduct() {
        ProductDto dto = new ProductDto();
        dto.setBookTitle("Easy recipe books for the newbie");
        dto.setBookPrice(BigDecimal.valueOf(33.00));
        dto.setBookQuantity(100);

        Product existingProduct = new Product();
        existingProduct.setBookTitle("Easy recipe books for the newbie");
        existingProduct.setBookPrice(BigDecimal.valueOf(33.00));
        existingProduct.setBookQuantity(100);

        Product updatedProduct = new Product();
        existingProduct.setBookTitle("Easy recipe books for the newbie");
        existingProduct.setBookPrice(BigDecimal.valueOf(33.00));
        existingProduct.setBookQuantity(100);

        // Mock repository behavior
        when(productRepository.findById(3)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(productMapper.productToProductDTO(updatedProduct)).thenReturn(dto);

        // When
        ProductDto responseDto = productService.updateProduct(dto, 3);

        // Then
        assertNotNull(responseDto, "ResponseDto should not be null");
        assertEquals(dto.getBookTitle(), responseDto.getBookTitle());
        assertEquals(dto.getBookPrice(), responseDto.getBookPrice());
        assertEquals(dto.getBookQuantity(), responseDto.getBookQuantity());

        verify(productRepository, times(1)).findById(3);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).productToProductDTO(updatedProduct);
    }

    @Test
    void getProductById() {
        //Given
        Integer productId = 1;
        Product product = new Product();
        product.setBookTitle("Easy recipe books for the newbie");
        product.setBookPrice(BigDecimal.valueOf(33.00));
        product.setBookQuantity(100);

        ProductDto dto = new ProductDto();
        dto.setBookTitle("Easy recipe books for the newbie");
        dto.setBookPrice(BigDecimal.valueOf(33.00));
        dto.setBookQuantity(100);

        // Mock the repository and mapper
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.productToProductDTO(product)).thenReturn(dto);

        // When
        ProductDto result = productService.getProductById(productId);

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(dto.getBookTitle(), result.getBookTitle());
        assertEquals(dto.getBookPrice(), result.getBookPrice());
        assertEquals(dto.getBookQuantity(), result.getBookQuantity());

        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).productToProductDTO(product);

    }

    @Test
    void getAllProducts() {

        // Given
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setBookTitle("Easy recipe books for the newbie");
        product.setBookPrice(BigDecimal.valueOf(33.00));
        product.setBookQuantity(10);

        productList.add(product);

        ProductDto dto = new ProductDto();
        dto.setBookTitle("Easy recipe books for the newbie");
        dto.setBookPrice(BigDecimal.valueOf(33.00));
        dto.setBookQuantity(100);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bookTitle").ascending());
        Page<Product> pagedProducts = new PageImpl<>(productList, pageable, productList.size());

        // Mock the repository and mapper
        when(productRepository.findAll(pageable)).thenReturn(pagedProducts);
        when(productMapper.productToProductDTO(any(Product.class))).thenReturn(dto);

        // When
        ProductResponse response = productService.getAllProducts(0, 10, "bookTitle", "ASC");

        // Then
        assertNotNull(response);
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getTotalElements());
        assertTrue(response.isLast());
        assertEquals(1, response.getContent().size());
        assertEquals("Easy recipe books for the newbie", response.getContent().get(0).getBookTitle());
    }

    @Test
    void deleteProductById_ProductExists_ShouldDeleteProduct() {
        // Arrange
        Integer bookId = 1;
        Product product = new Product(); // Assuming you have a no-args constructor or a proper constructor for Product
        when(productRepository.findById(bookId)).thenReturn(Optional.of(product));

        // Act
        productService.deleteProductById(bookId);

        // Assert
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProductById_ProductDoesNotExist_ShouldThrowException() {
        // Arrange
        Integer bookId = 20;
        when(productRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        SystemException thrownException = assertThrows(SystemException.class, () -> productService.deleteProductById(bookId));

        // Verify exception details
        assertEquals(SystemErrorCode.PRODUCT_NOT_FOUND.getMessage(), thrownException.getMessage());
    }

}