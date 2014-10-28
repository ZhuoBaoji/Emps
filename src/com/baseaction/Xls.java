package com.baseaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.itextpdf.text.DocumentException;
import com.po.Emp;
import com.service.impl.EmpServiceImpl;
import com.util.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.ServletException;

public class Xls extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpServiceImpl empService;
	private Emp emp = new Emp();
	private int startrow;
	private int startcolu;
	private String xlsPath;
	private String img;
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getXlsPath() {
		return xlsPath;
	}

	public void setXlsPath(String xlsPath) {
		this.xlsPath = xlsPath;
	}

	public int getStartrow() {
		return startrow;
	}

	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	public int getStartcolu() {
		return startcolu;
	}

	public void setStartcolu(int startcolu) {
		this.startcolu = startcolu;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public EmpServiceImpl getEmpService() {
		return empService;
	}

	public void setEmpService(EmpServiceImpl empService) {
		this.empService = empService;
	}

	// 导出Excle表格
	public void  xls() throws ServletException, IOException, DocumentException {
		response.setContentType("text/html;charset=utf-8");
		// 把数据库表导出为Excel文件；
		List<Emp> xls = empService.findAllEmp();
		// 创建Excel文档
		FileInputStream fis = new FileInputStream(new File(xlsPath));
		XSSFWorkbook hwb = new XSSFWorkbook(fis);
		Emp xlsDto = null;
		// sheet 对应一个工作页
		XSSFSheet sheet = hwb.getSheet(("Sheet1"));
		sheet.setDefaultColumnWidth((short) 15);
		// 产生表格标题行
		XSSFRow firstrow = sheet.createRow(1); // 下标为0的行开始
		// 创建表格列数
		XSSFCell[] firstcell = new XSSFCell[Emp.class.getDeclaredFields().length];
		String[] names = new String[Emp.class.getDeclaredFields().length];
		names[0] = "ID";
		names[1] = "姓名";
		names[2] = "年龄";
		names[3] = "工资";
		names[4] = "部门";
		// she zhi lie ming zi
		for (int j = 0; j < Emp.class.getDeclaredFields().length; j++) {
			firstcell[j] = firstrow.createCell(j);
			firstcell[j].setCellValue(new XSSFRichTextString(names[j]));
		}
		// 插入数据
		XlsUtil.insertImg(hwb,img);
		
		for (int i = 0; i < xls.size(); i++) {
			// she zhi cong di jihang kaishi
			int row = i + startrow;
			// 得到要插入的每一条记录
			xlsDto = xls.get(i);
			// she zhi qi shi lie
			int colu = startcolu;
			// 在一行内循环
			try {
				XlsUtil.writeToCell(sheet, row, colu, xlsDto.getId());
				XlsUtil.writeToCell(sheet, row, colu + 1, xlsDto.getName());
				XlsUtil.writeToCell(sheet, row, colu + 2, xlsDto.getAge());
				XlsUtil.writeToCell(sheet, row, colu + 3, xlsDto.getSalary());
				XlsUtil.writeToCell(sheet, row, colu + 4, xlsDto.getDeptid()
						.getDname());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			// 创建文件输出流，准备输出电子表格
			response.setCharacterEncoding("utf-8");
			response.setContentType("octets/stream");
			response.addHeader("Content-Disposition",
					"attachment;filename=EmpInfo.xlsx");
			OutputStream out = response.getOutputStream();
			hwb.write(out);
			out.close();
			System.out.println("数据库导出成功");
	}
}
