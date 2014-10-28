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

	// ����Excle���
	public void  xls() throws ServletException, IOException, DocumentException {
		response.setContentType("text/html;charset=utf-8");
		// �����ݿ����ΪExcel�ļ���
		List<Emp> xls = empService.findAllEmp();
		// ����Excel�ĵ�
		FileInputStream fis = new FileInputStream(new File(xlsPath));
		XSSFWorkbook hwb = new XSSFWorkbook(fis);
		Emp xlsDto = null;
		// sheet ��Ӧһ������ҳ
		XSSFSheet sheet = hwb.getSheet(("Sheet1"));
		sheet.setDefaultColumnWidth((short) 15);
		// ������������
		XSSFRow firstrow = sheet.createRow(1); // �±�Ϊ0���п�ʼ
		// �����������
		XSSFCell[] firstcell = new XSSFCell[Emp.class.getDeclaredFields().length];
		String[] names = new String[Emp.class.getDeclaredFields().length];
		names[0] = "ID";
		names[1] = "����";
		names[2] = "����";
		names[3] = "����";
		names[4] = "����";
		// she zhi lie ming zi
		for (int j = 0; j < Emp.class.getDeclaredFields().length; j++) {
			firstcell[j] = firstrow.createCell(j);
			firstcell[j].setCellValue(new XSSFRichTextString(names[j]));
		}
		// ��������
		XlsUtil.insertImg(hwb,img);
		
		for (int i = 0; i < xls.size(); i++) {
			// she zhi cong di jihang kaishi
			int row = i + startrow;
			// �õ�Ҫ�����ÿһ����¼
			xlsDto = xls.get(i);
			// she zhi qi shi lie
			int colu = startcolu;
			// ��һ����ѭ��
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
			// �����ļ��������׼��������ӱ��
			response.setCharacterEncoding("utf-8");
			response.setContentType("octets/stream");
			response.addHeader("Content-Disposition",
					"attachment;filename=EmpInfo.xlsx");
			OutputStream out = response.getOutputStream();
			hwb.write(out);
			out.close();
			System.out.println("���ݿ⵼���ɹ�");
	}
}
