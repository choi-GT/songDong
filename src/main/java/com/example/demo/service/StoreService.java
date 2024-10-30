package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.StoreInfoMapper;
import com.example.demo.mapper.StoreMapper;
import com.example.demo.vo.StoreInfoVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreService {
		private final StoreMapper storeMapper;
		private final StoreInfoMapper storeInfoMapper;
		
		   public StoreService(StoreMapper storeMapper, StoreInfoMapper storeInfoMapper) {
		        this.storeMapper = storeMapper;
		        this.storeInfoMapper = storeInfoMapper; // StoreInfoMapper 주입
		    }


	//모든 좌표를 가져오기
	public List<HashMap<String, Object>> getAllStore() {
		// TODO Auto-generated method stub
		return storeMapper.selectAll();
	}

	public StoreInfoVO getStoreByAddress(String sitewhladdr) {
		// TODO Auto-generated method stub
		return null;
	}
	
    public List<HashMap<String, Object>> getShopsByKeyword(String keyword) {
        return storeInfoMapper.selectShopByShopName(keyword);
    }

}