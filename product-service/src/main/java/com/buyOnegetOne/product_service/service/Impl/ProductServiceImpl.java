package com.buyOnegetOne.product_service.service.Impl;

import com.buyOnegetOne.product_service.dto.ProductRequest;
import com.buyOnegetOne.product_service.dto.ProductResponse;
import com.buyOnegetOne.product_service.entity.Product;
import com.buyOnegetOne.product_service.mapper.ProductMapper;
import com.buyOnegetOne.product_service.repo.ProductRepository;
import com.buyOnegetOne.product_service.service.ProductService;
import com.buyOnegetOne.product_service.util.MessageConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final MessageSource messageSource;

    @Override
    @Transactional
    public ResponseEntity<Object> addProduct(ProductRequest productRequest) {
        try{
            Optional<Product> existProduct=productRepository.findByName(productRequest.getName());

            if(existProduct.isPresent()){
                log.info("Product with name {} already exists",productRequest.getName());

                return ResponseEntity.status(HttpStatus.CONFLICT).body(messageSource.getMessage(MessageConstant.PRODUCT_ALREADY_EXISTS
                        ,null,null));
            }
            else{
                Product product=new Product();

                ProductMapper.DtoToEntity(product,productRequest);

                productRepository.save(product);

                ProductResponse productResponse=ProductMapper.EntityToDto(product);
                productResponse.setMessage(messageSource.getMessage(MessageConstant.PRODUCT_SAVE_SUCCESS
                        ,null,null));

                log.info("Product {} is save successfully", product.getName());
                return ResponseEntity.status(HttpStatus.CREATED).
                        body(productResponse);
            }

        }

        catch (Exception e){
            log.error(String.valueOf(e));
            throw e;
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();

            return ResponseEntity.status(HttpStatus.OK).
                    body(products.stream().map(ProductMapper::EntityToDto).toList());
        }
        catch (Exception ex){
            log.error(String.valueOf(ex));
            throw ex;
        }
    }

}
