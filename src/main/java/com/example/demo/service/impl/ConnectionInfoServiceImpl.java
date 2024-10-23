package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ConnectionInfoMapper;
import com.example.demo.service.ConnectionInfoService;
import com.example.demo.vo.ConnectionInfoVO;

@Service
public class ConnectionInfoServiceImpl implements ConnectionInfoService{
	@Autowired
	private ConnectionInfoMapper connectionInfoMapper;
	
	
	

	@Override
	public int insert(ConnectionInfoVO connectionInfoVO) {
		// TODO Auto-generated method stub
		return connectionInfoMapper.insert(connectionInfoVO);
	}
}
