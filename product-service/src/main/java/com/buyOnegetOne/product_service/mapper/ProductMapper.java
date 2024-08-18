package com.buyOnegetOne.product_service.mapper;

import com.buyOnegetOne.product_service.dto.ProductRequest;
import com.buyOnegetOne.product_service.dto.ProductResponse;
import com.buyOnegetOne.product_service.entity.Product;

public class ProductMapper {
    private ProductMapper() {
    }


    public static void DtoToEntity(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice((productRequest.getPrice()));
    }

    public static ProductResponse EntityToDto(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(String.valueOf(product.getPrice()))
                .build();

    }
}
