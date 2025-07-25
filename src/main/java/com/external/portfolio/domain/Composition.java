package com.external.portfolio.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Composition {
    private Long compositionId;
    private Long userId;
    private Map<String, Double> assetComposition;
}
