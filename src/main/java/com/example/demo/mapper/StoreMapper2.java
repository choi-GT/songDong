package com.example.demo.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.FavoriteStoreVO; // FavoriteStoreVO 추가
import com.example.demo.vo.StoreInfoVO;

@Mapper
public interface StoreMapper2 {
    List<StoreInfoVO> getAllStores(); 
    

    void insertFavoriteStore(HashMap<String, Object> map); 
    

    void deleteFavoriteStore(HashMap<String, Object> map);

	List<StoreInfoVO> getFavoriteStores(Long userId);
	
	List<FavoriteStoreVO> getAllFavoriteStores(); // XML에서 정의한 쿼리 호출
	
	List<HashMap<String, Object>> favoriteCount();
}
