package com.service;

import java.util.List;

import com.po.Emp;

public interface EmpService {
	public void saveEntity(Emp t);
	public void updateEntity(Emp t);
	public void saveOrUpdateEntity(Emp t);
	public void deleteEntity(Emp t);
	public List<Emp> findAllEmp();
	public Emp findById(Integer id);
}
