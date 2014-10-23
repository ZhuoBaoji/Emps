package com.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.BaseDao;
import com.po.Emp;

public class EmpDaoImpl extends HibernateDaoSupport implements BaseDao<Emp> {

	public void deleteEntity(Emp t) {
		this.getHibernateTemplate().delete(t);
	}

	public void saveEntity(Emp t) {
		this.getHibernateTemplate().save(t);
	}

	public void saveOrUpdateEntity(Emp t) {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	public void updateEntity(Emp t) {
		this.getHibernateTemplate().update(t);
	}

	public List<Emp> findAllEmp() {
		return this.getHibernateTemplate().loadAll(Emp.class);
	}

	public Emp findById(Integer id) {
		return (Emp) this.getHibernateTemplate()
				.get(Emp.class, id);
	}

}
