package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.StoreMapper2;
import com.example.demo.service.StoreService2;
import com.example.demo.vo.MemberShipVO;
import com.example.demo.vo.StoreInfoVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


	@Service
	@Slf4j
	public class StoreService2Impl implements StoreService2 {

	    @Autowired
	    private StoreMapper2 storeMapper;

	    @Override
	    public List<StoreInfoVO> getAllStores() {
	        return storeMapper.getAllStores();
	    }
	    
	    @Override
	    public boolean saveFavoriteStore(String storeName, HttpServletRequest request) {
	        try {
	        	HttpSession session = request.getSession();
	        	MemberShipVO vo = (MemberShipVO) session.getAttribute("userInfo");
	        	HashMap<String, Object> requestMap = new HashMap<>();
	        	requestMap.put("storeName", storeName);
	        	requestMap.put("userIDX", vo.getIdx());
	        	log.info(requestMap.toString());
	            storeMapper.insertFavoriteStore(requestMap); // 매퍼에서 호출
	            return true;
	        } catch (Exception e) { 
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    @Override
	    public boolean removeFavoriteStore(String storeName) {
	        try {
	            storeMapper.deleteFavoriteStore(storeName); // 매퍼에서 호출
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

		@Override
		public List<StoreInfoVO> getFavoriteStores(Long userId) {
			
			return storeMapper.getFavoriteStores(userId);
		}

	}

