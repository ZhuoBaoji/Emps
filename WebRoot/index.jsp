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
	<script type="text/javascript" src="/Emps/js/@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@jquery-1.8.3.js"></script>
	<script language=JavaScript>
	$(function() {
		$@@@@@@@@@@@@@@@@@@@('#delete').click(function() {
			var ida = this.name;
			$.post("empAction_delete", {
				id : ida
			}  , function(data){
    $(this).parent().parent().remove();
		});
		});
	})
</script>     
	</head>
	<body>
		<div id="wrap">
			<div id="top_content"> 
				<div id="content">
					<p id="whereami">
					</p>
					<h1>
					</h1>
					
					<table class="table">
						<tr class="table_header">
							<td>
								ID
							</td>
							<td>
								姓名
							</td>
							<td>
								工资
							</td>
							<td>
								年龄
							</td>
							<td>
							   部门名称
							</td>
							<td>
								操作
							</td>
						</tr>
						<s:iterator value="emps" var="emp" status="i">
							
						<tr class="row<s:property value="i.index%2+1"/>">
							<td>
								<s:property value="id"/>
							</td>
							<td>
								<s:property value="name"/>
							</td>
							<td>
                              	<s:property value="salary"/>
							</td>
							<td>
                                	<s:property value="age"/>
							</td>
							<td>
							   	<s:property value="deptid.dname"/>
							</td>
							<td>
								<a id="delete" href="empAction_delete.action?id=<s:property value="id"/>">删除</a>
								<a id="update" href="empAction_loadEmp.action?id=<s:property value="id"/>">更新</a>
							</td>
							
						</tr>
						</s:iterator>
						
					</table>
					<p>
						<input type="button" class="button" value="增加员工" onclick="location='empAction_emp.action'"/>
						<input type="button" class="button" value="从Excle导入" onclick="location='xls_inxls.action'"/>
						
						<input type="button" class="button" value="导出Excle" onclick="location='xls_xls.action'"/>
							<input type="button" class="button" value="导出PDF" onclick="location='pdf_pdf.action'"/>
						
					</p>
			<c:choose>
			   <c:when test="${pageNo eq 1}">
			   </c:when>
			   <c:otherwise>
			     <a href="/struts2.2/list.action?pageNo=${pageNo-1}"></a>
			   </c:otherwise>
			</c:choose>
			<c:choose>
			   <c:when test="${pageNo eq total}">
			   </c:when>
			   <c:otherwise>
			      <a href="/struts2.2/list.action?pageNo=${pageNo+1}"></a>
			   </c:otherwise>
			</c:choose>
			<br/>
				</div>
			</div>
			<div id="footer">
				<div id="footer_bg">
				</div>
			</div>
		</div>
	</body>
</html>
