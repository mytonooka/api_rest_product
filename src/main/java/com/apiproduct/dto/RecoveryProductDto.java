package com.apiproduct.dto;

import java.util.List;

public record RecoveryProductDto(

        Long id,

        String name,

        String description,

        String category,

        List<CreateProductVariationDto> productVariations,

        Boolean available
        
) {
}
