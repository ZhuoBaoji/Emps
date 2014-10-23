package com.dao;

import java.util.List;

import com.po.Emp;

public interface BaseDao<T> {
	// Ð´²Ù×÷
	public void saveEntity(T t);

	public void updateEntity(T t);

	public void saveOrUpdateEntity(T t);

	public void deleteEntity(T t);

	public List<Emp> findAllEmp();

	public Emp findById(Integer id);
}
