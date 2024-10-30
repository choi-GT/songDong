package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.StoreInfoVO;

public interface CrudService<E> {

	List<E> selectList(E e);
	
	E selectOne(E e);
	
	void insert(E e);
	
	void update(E e);
	
	void delete(E e);
	
	List<StoreInfoVO> selectAll();
}
