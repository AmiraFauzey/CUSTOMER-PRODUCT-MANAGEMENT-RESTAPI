package com.customersProducts.mapper;

import com.customersProducts.dto.ProductDto;
import com.customersProducts.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productToProductDTO(Product product);
    Product productDTOToProduct(ProductDto productDto);
}
