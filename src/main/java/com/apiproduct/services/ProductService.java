package com.apiproduct.services;

import com.apiproduct.dto.CreateProductDto;
import com.apiproduct.dto.CreateProductVariationDto;
import com.apiproduct.entities.Product;
import com.apiproduct.dto.RecoveryProductDto;
import com.apiproduct.entities.ProductVariation;
import com.apiproduct.entities.enums.Category;
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

    // Método responsável por criar um produto
    public RecoveryProductDto createProduct(CreateProductDto createProductDto) {
        /*
        Converte a lista de ProductVariationDto em uma lista de ProductVariation,
        utilizando o ProductMapper para fazer o mapeamento de cada elemento da lista.
         */
        List<ProductVariation> productVariations =  createProductDto.productVariations().stream()
                .map(productVariationDto -> productMapper.mapCreateProductVariationDtoToProductVariation(productVariationDto))
                .toList();

        // Cria um produto através dos dados do DTO
        Product product = Product.builder()
                .name(createProductDto.name())
                .description(createProductDto.description())
                .category(Category.valueOf(createProductDto.category().toUpperCase()))
                .productVariations(productVariations)
                .available(createProductDto.available())
                .build();

        /*
        Se o produto estiver com o available = false, por padrão todas as variações do produto devem estar com available false também,
        porque não faria sentido o produto estar estar indisponível e as variações daquele produto estarem disponíveis
         */
        if (!product.getAvailable() && product.getProductVariations().stream().anyMatch(ProductVariation::getAvailable)) {
            throw new RuntimeException("A variação de tamanho não pode estar disponível se o produto estiver indisponível.");
        }

        // Relaciona cada variação de produto com o produto
        productVariations.forEach(productVariation -> productVariation.setProduct(product));

        // Salva produto
        Product productSaved = productRepository.save(product);

        // Retornando e mapeando os produtos para o tipo RecoveryProductDto
        return productMapper.mapProductToRecoveryProductDto(productSaved);
    }

    // Método responsável por criar uma variação de produto
    public RecoveryProductDto createProductVariation(Long productId, CreateProductVariationDto createProductVariationDto) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        // Converte o DTO de criação da variação de produto para uma entidade ProductVariation
        ProductVariation productVariation = productMapper.mapCreateProductVariationDtoToProductVariation(createProductVariationDto);

        // Associa a variação de produto ao produto e salva a variação no banco de dados
        productVariation.setProduct(product);
        ProductVariation productVariationSaved = productVariationRepository.save(productVariation);

        // Adiciona a variação de produto ao produto e salva o produto no banco de dados
        product.getProductVariations().add(productVariationSaved);
        productRepository.save(product);

        // Retornando e mapeando os produtos para o tipo RecoveryProductDto
        return productMapper.mapProductToRecoveryProductDto(productVariationSaved.getProduct());
    }

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
