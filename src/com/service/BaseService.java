package com.service;

public interface BaseService<T> 
{
	//Ð´²Ù×÷
	public void saveEntity(T t);
	public void updateEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void deleteEntity(T t);
}
