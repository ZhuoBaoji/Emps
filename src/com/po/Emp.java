package com.po;

public class Emp {
	private Integer id;
	private String name;
	private int age;
	private Double salary;
	private Dept deptid;


	public Emp(Integer id, String name, int age, Double salary) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	public Emp() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Dept getDeptid() {
		return deptid;
	}

	public void setDeptid(Dept deptid) {
		this.deptid = deptid;
	}

}
