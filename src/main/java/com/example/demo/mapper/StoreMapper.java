package com.example.demo.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.StoreInfoVO;

@Mapper
public interface StoreMapper {
	void insertStore(StoreInfoVO storeInfoVO);
	List<HashMap<String, Object>> selectAll();
	StoreInfoVO selectOne(Long mgtNo);
	List<StoreInfoVO> selectList(StoreInfoVO e);
	
}
