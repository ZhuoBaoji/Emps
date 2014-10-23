package com.service;

public interface BaseService<T> 
{
	//д����
	public void saveEntity(T t);
	public void updateEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void deleteEntity(T t);
}
