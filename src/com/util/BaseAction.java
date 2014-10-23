package com.util;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware,ServletRequestAware,
ServletResponseAware,ServletContextAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//以后我们创建的Action实例  都会继承自BaseAction  
	/*
	 *    我们将属性的访问修饰符为protected  就是为了继承他的子类 可以
	 *    拿来就用这个session  当我们的struts2框架替换成其他框架后 
	 *    我们只需要修改BaseAction即可  
	 */
    protected Map<String,Object> session;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ServletContext application;
	
	
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}


	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}


	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		
	}


	public void setServletContext(ServletContext application) {
		this.application=application;
		
	}

}
