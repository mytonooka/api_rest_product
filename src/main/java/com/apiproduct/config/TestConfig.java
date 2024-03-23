package com.apiproduct.config;

import com.apiproduct.entities.Product;
import com.apiproduct.entities.ProductVariation;
import com.apiproduct.repositories.ProductRepository;
import com.apiproduct.repositories.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.apiproduct.entities.enums.Category.HAMBURGER;


@Configuration
@Profile("mysql")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Override
    public void run(String... args) throws Exception {

        Product p1 = new Product(null, "Hamburger Master","Eat our delicious Hamburger Master Chief",HAMBURGER,null,true);
        productRepository.save(p1);

        ProductVariation pv1 = new ProductVariation(null, "P", "X-burger with mayonnaise",true, BigDecimal.valueOf(20.0), p1);
        ProductVariation pv2 = new ProductVariation(null, "M", "X-burger with bacon and mayonnaise",true, BigDecimal.valueOf(25.0), p1);
        ProductVariation pv3 = new ProductVariation(null, "G", "X-burger with bacon, salad and mayonnaise",true, BigDecimal.valueOf(30.0), p1);

        productVariationRepository.saveAll(Arrays.asList(pv1, pv2, pv3));

    }

}
