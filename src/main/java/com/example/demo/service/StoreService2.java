package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.StoreInfoVO;

import jakarta.servlet.http.HttpServletRequest;

public interface StoreService2 {
	List<StoreInfoVO> getAllStores();
	
	boolean saveFavoriteStore(String storeName, HttpServletRequest request); // 즐겨찾기 저장 메서드 추가
	
	void removeFavoriteStore(String storeName, HttpServletRequest request); // 즐겨찾기 삭제 메서드 추가  
	
	List<StoreInfoVO> getFavoriteStores(Long userId);
	
	
}
