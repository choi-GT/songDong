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
    
//    @Insert("MERGE INTO SEMI.FAVORITE_STORES USING DUAL " +
//            "ON (STORE_NAME = #{storeName}) " +
//            "WHEN MATCHED THEN " +
//            "    UPDATE SET COUNT = COUNT + 1 " +
//            "WHEN NOT MATCHED THEN " +
//            "    INSERT (STORE_NAME, COUNT) VALUES (#{storeName}, 1)"
//            )
    void insertFavoriteStore(HashMap<String, Object> map); 
    
    @Delete("DELETE FROM SEMI.FAVORITE_STORES WHERE STORE_NAME = #{storeName}")
    void deleteFavoriteStore(String storeName);

	List<StoreInfoVO> getFavoriteStores(Long userId);
}
