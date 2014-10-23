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

	// ɾ��
	public void deleteEntity(Emp t) {
		dao.deleteEntity(t);
	}

	// ���
	public void saveEntity(Emp t) {
		dao.saveEntity(t);
	}

	public void saveOrUpdateEntity(Emp t) {
		dao.saveOrUpdateEntity(t);
	}

	// ����
	public void updateEntity(Emp t) {
		dao.updateEntity(t);
	}

	// ��������Ա����Ϣ
	public List<Emp> findAllEmp() {
		return dao.findAllEmp();
	}

	public Emp findById(Integer id) {
		return dao.findById(id);
	}

}
