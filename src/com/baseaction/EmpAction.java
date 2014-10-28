package com.baseaction;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.po.Dept;
import com.po.Emp;
import com.service.impl.EmpServiceImpl;
import com.util.BaseAction;

public class EmpAction extends BaseAction implements ModelDriven<Emp> {
	/**
	 * implements ModelDriven<Emp>
	 */
	private static final long serialVersionUID = 1L;
	private EmpServiceImpl empService;
	private List<Emp> emps;
	private Emp emp=new Emp();
//	private Integer id;
//	private String name;
//	private Integer age;
//	private double salary;
//	private Integer deptid;
//	private List<Dept> depts;
//	private Dept dept = new Dept();

//	public Dept getDept() {
//		return dept;
//	}
//
//	public void setDept(Dept dept) {
//		this.dept = dept;
//	}

	// public Integer getId() {
	// return id;
	// }
	//
	// public void setId(Integer id) {
	// this.id = id;
	// }

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getAge() {
//		return age;
//	}
//
//	public void setAge(Integer age) {
//		this.age = age;
//	}
//
//	public double getSalary() {
//		return salary;
//	}
//
//	public void setSalary(double salary) {
//		this.salary = salary;
//	}
//
//	public Integer getDeptid() {
//		return deptid;
//	}
//
//	public void setDeptid(Integer deptid) {
//		this.deptid = deptid;
//	}
//
//	public List<Dept> getDepts() {
//		return depts;
//	}
//
//	public void setDepts(List<Dept> depts) {
//		this.depts = depts;
//	}
//
	public void setEmps(List<Emp> emps) {
		this.emps = emps;
	}

	public List<Emp> getEmps() {
		return emps;
	}

	public void setEmpService(EmpServiceImpl empService) {
		this.empService = empService;
	}

	// 查询所有显示
	public String emplist() {
		emps = empService.findAllEmp();
		return "index";
	}
//	jin ru zhu ye
	public String emp() {
		return "success";
	}

	// 删除
//	public String delete() {
//		System.out.println(id);
//		emp.setId(id);
//		empService.deleteEntity(emp);
//		return "success";
//
//	}

	// 更新
	public String updateEmp() {
//		System.out.println(age + name + salary + deptid + id);
//		emp.setId(id);
//		emp.setAge(age);
//		Dept dept = new Dept();
//		dept.setId(deptid);
//		emp.setDeptid(dept);
//		emp.setName(name);
//		emp.setSalary(salary);
		empService.updateEntity(emp);
		return "success";
	}

	// 增加
	public String addEmp() {
		System.out.println(emp.getAge() + emp.getName()+emp.getSalary());
//		emp.setAge(age);
		Dept dept = new Dept();
		dept.setId(emp.getDeptid().getId());
		emp.setDeptid(dept);
//		emp.setName(name);
//		emp.setSalary(salary);
		empService.saveEntity(emp);
		return "success";
	}

	// 查找某个员工信息
//	public String loadEmp() {
//		emp = empService.findById(id);
//		return "load";
//	}

	public Emp getModel() {
		// TODO Auto-generated method stub
		if (emp != null) {
			return emp;
		}
		return new Emp();
	}

}
