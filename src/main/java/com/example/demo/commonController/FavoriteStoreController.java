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
        log.info("User Info: {}", vo);
        
        boolean success = storeService.saveFavoriteStore(storeName, request);
        if (success) {
            log.info("즐겨찾기 추가 성공: {}", storeName);
            return ResponseEntity.ok("즐겨찾기 추가 성공");
        } else {
            log.error("즐겨찾기 추가 실패: {}", storeName);
            return ResponseEntity.status(500).body("즐겨찾기 추가 실패");
        }
    }


    
    @GetMapping("/favorites")
    public ResponseEntity<?> removeFav(@RequestParam(value = "storeName") String storeName, HttpServletRequest request) {
    	log.info(storeName);
    	storeService.removeFavoriteStore(storeName, request);
    	return ResponseEntity.ok().build(); 
    }

}
