package com.apiproduct.services;

import com.apiproduct.entities.Product;
import com.apiproduct.dto.RecoveryProductDto;
import com.apiproduct.mapper.ProductMapper;
import com.apiproduct.repositories.ProductRepository;
import com.apiproduct.repositories.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private ProductMapper productMapper;

    // Método responsável por retornar todos os produtos
    public List<RecoveryProductDto> getProducts() {
        // Retorna todos os produtos salvos no banco
        List<Product> products = productRepository.findAll();

        // Retornando e mapeando os produtos para uma lista do tipo RecoveryProductDto
        return products.stream().map(product -> productMapper.mapProductToRecoveryProductDto(product)).toList();
    }

    // Método responsável por retornar o produto por id
    public RecoveryProductDto getProductById(Long productId) {
        // Producra por um produto salvo no banco
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        // Retornando e mapeando os produtos para o tipo RecoveryProductDto
        return productMapper.mapProductToRecoveryProductDto(product);
    }
}
