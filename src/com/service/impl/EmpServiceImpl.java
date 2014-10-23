package com.service.impl;

import java.util.List;

import com.dao.impl.EmpDaoImpl;
import com.po.Emp;
import com.service.EmpService;

public class EmpServiceImpl implements EmpService {
	private EmpDaoImpl dao;

	public EmpDaoImpl getDao() {
		return dao;
	}

	public void setDao(EmpDaoImpl dao) {
		this.dao = dao;
	}

	// 删除
	public void deleteEntity(Emp t) {
		dao.deleteEntity(t);
	}

	// 添加
	public void saveEntity(Emp t) {
		dao.saveEntity(t);
	}

	public void saveOrUpdateEntity(Emp t) {
		dao.saveOrUpdateEntity(t);
	}

	// 更新
	public void updateEntity(Emp t) {
		dao.updateEntity(t);
	}

	// 超找所有员工信息
	public List<Emp> findAllEmp() {
		return dao.findAllEmp();
	}

	public Emp findById(Integer id) {
		return dao.findById(id);
	}

}
