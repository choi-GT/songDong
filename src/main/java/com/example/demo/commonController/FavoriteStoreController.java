package com.example.demo.commonController;

import com.example.demo.service.StoreService2;
import com.example.demo.vo.FavoriteStoreVO;
import com.example.demo.vo.MemberShipVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@Slf4j
public class FavoriteStoreController {

    @Autowired
    private StoreService2 storeService;

    @PostMapping("/favorites")
    public ResponseEntity<?> addFavorite(@RequestBody String storeName, HttpServletRequest request) {
        HttpSession se = request.getSession();
        MemberShipVO vo = (MemberShipVO) se.getAttribute("userInfo");
        log.info(vo.toString());
//        log.info(String.valueOf(userId));
        boolean success = storeService.saveFavoriteStore(storeName, request); // 즐겨찾기 추가
//        return success ? ResponseEntity.ok().build() : ResponseEntity.status(500).build();
    	return null; 
    }

    @DeleteMapping("/favorites")
    public ResponseEntity<Void> removeFavorite(@RequestBody String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FavoriteStoreVO favoriteStore = objectMapper.readValue(json, FavoriteStoreVO.class);
            boolean success = storeService.removeFavoriteStore(favoriteStore.getStoreName());
            return success ? ResponseEntity.ok().build() : ResponseEntity.status(500).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
