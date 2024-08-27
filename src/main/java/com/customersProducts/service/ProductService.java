package com.customersProducts.service;

import com.customersProducts.dto.ProductDto;
import com.customersProducts.dto.ProductResponse;
import com.customersProducts.entity.Product;
import com.customersProducts.exception.SystemErrorCode;
import com.customersProducts.exception.SystemException;
import com.customersProducts.mapper.ProductMapper;
import com.customersProducts.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
public class ProductService {

    private final ProductRepository productRepository;
    @Autowired
    private final ProductMapper productMapper;

    /*To create Product*/
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "products", key = "#result.bookId")
    public ProductDto createProduct(ProductDto dto){
        Product product = productMapper.productDTOToProduct(dto);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDTO(savedProduct);
    }


    /*To update product*/
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "products", key = "#bookId")
    public ProductDto updateProduct(ProductDto dto, Integer bookId) throws SystemException {

        Product product = productRepository.findById(bookId)
                .orElseThrow(() -> new SystemException(SystemErrorCode.PRODUCT_NOT_FOUND));

        product.setBookTitle(dto.getBookTitle());
        product.setBookPrice(dto.getBookPrice());
        product.setBookQuantity(dto.getBookQuantity());

        Product updatedProduct = productRepository.save(product);

        return productMapper.productToProductDTO(updatedProduct);
    }

    /*To view product*/
    @Transactional(readOnly = true)
    @Cacheable(value = "products", key = "#bookId")
    public ProductDto getProductById(Integer bookId) throws SystemException {
        Product product = productRepository.findById(bookId).orElseThrow(()-> new SystemException(SystemErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.productToProductDTO(product);
    }

    /*To view all product*/
    public ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);
        //get content for page object
        List<Product> listOfProducts = products.getContent();
        List<ProductDto> contents = listOfProducts.stream().map(productMapper::productToProductDTO).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(contents);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
    }

    /*To delete Product*/
    @CacheEvict(value = "products", key = "#bookId")
    public void deleteProductById(Integer bookId) {
        // get Product by id from the database
        Product product = productRepository.findById(bookId).orElseThrow(() -> new SystemException(SystemErrorCode.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }

}
