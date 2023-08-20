package com.adan.productservice.service;

import com.adan.productservice.dto.ProductRequest;
import com.adan.productservice.dto.ProductResponse;
import com.adan.productservice.entity.Product;
import com.adan.productservice.exception.ProductNotFoundException;
import com.adan.productservice.repository.ProductRepository;
import com.adan.productservice.user.UserInfo;
import com.adan.productservice.user.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} added", product.getId());
    }
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }
    public void deleteProductById(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            log.info("Product {} deleted", id);
        } else {
            log.warn("Product with ID {} not found", id);
            throw new ProductNotFoundException("Product not found");
        }
    }

    public void updateProduct(String id, ProductRequest updatedProductRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (updatedProductRequest.getName() != null) {
            product.setName(updatedProductRequest.getName());
        }
        if (updatedProductRequest.getDescription() != null) {
            product.setDescription(updatedProductRequest.getDescription());
        }
        if (updatedProductRequest.getPrice() != null) {
            product.setPrice(updatedProductRequest.getPrice());
        }
        productRepository.save(product);
        log.info("Product {} updated", id);
    }
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return mapToProductResponse(product);
    }
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "user added to system ";
    }
}
