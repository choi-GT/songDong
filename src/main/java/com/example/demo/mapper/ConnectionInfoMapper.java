package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.ConnectionInfoVO;

@Mapper
public interface ConnectionInfoMapper {
	int  insert(ConnectionInfoVO connetionInfoVO);
	
}
