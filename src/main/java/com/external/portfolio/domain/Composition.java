package com.external.portfolio.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Composition {
    private String fintechUseNum;
    private Map<String, Double> assetComposition;
}
