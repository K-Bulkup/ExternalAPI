package com.external.asset.controller;

import com.external.asset.dto.TraineePortfolioDetailResponseDTO;
import com.external.asset.service.AssetService;
import com.external.asset.service.MappingService;
import com.external.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainee/portfolios")
public class ApiController {

    //테스트용
    @Autowired
    private MappingService mappingService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> test() {
//        @RequestHeader(value = "Authorization", required = false) String authorization, // 액세스토큰
//        @RequestBody(required = false) TraineePortfolioDetailRequestDTO reqDTO //핀테크번호

        // userid 가 1인 user 더미데이터매핑
        mappingService.mapDummyDataToUser(1L);

        // userid 가 1인 user의 자산 데이터 가져오기ㅣ
        TraineePortfolioDetailResponseDTO resDTO = assetService.getAllAssetData(1L);

        return ResponseEntity.ok(resDTO);
    }

}
