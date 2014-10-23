<%@ page language="java" import="java.util.*,com.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="s" uri="/struts-tags"%> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>员工列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/style.css" />
	</head>
	<body>
		<form action="empAction_updateEmp.action"  method="post">
		ID  ${id }  <input value="${emp.id }" name="id" hidden="true"><br>
		姓名:<input value="${emp.name}" name="name" ><br>
		年龄:<input  value="${emp.age}" name="age"><br>
		薪水:<input name="salary"  value="${emp.salary}"><br>
		部门:<select name="deptid" >
              <option value="1">人事部</option>
              <option value="2">财务部</option>
              <option value="3">技术部</option>
            </select><br>
		<input type="submit"  value="提交">
		</form>
<s:debug></s:debug>
	</body>
</html>
