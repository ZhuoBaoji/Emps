package com.baseaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.po.Emp;
import com.service.impl.EmpServiceImpl;
import com.util.BaseAction;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.ServletException;

public class Xls extends BaseAction {
	/**
	 * ָ��ÿ����Ԫ������
	 * sheet.getRow(5).getCell(1).setCellValue("tomcat");(conglingkaishi)
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpServiceImpl empService;
	private Emp emp = new Emp();

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
	public void xls() throws ServletException, IOException, DocumentException {

		response.setContentType("text/html;charset=utf-8");

		// �����ݿ����ΪExcel�ļ���
		List<Emp> xls = empService.findAllEmp();
		System.out.println(xls.size());
		// int CountColumnNum = xls.size();

		// ����Excel�ĵ�
		FileInputStream fis = new FileInputStream(new File("D:\\11.xlsx"));
		XSSFWorkbook hwb = new XSSFWorkbook(fis);
		Emp xlsDto = null;
		// sheet ��Ӧһ������ҳ
		XSSFSheet sheet = hwb.getSheet(("Sheet1"));
		System.out.println(sheet); // ���ñ��Ĭ���п��Ϊ15���ֽ�
		sheet.setDefaultColumnWidth((short) 15);
//		 hwb.setSheetName(1, "tomcat");
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
		
		
		
		
		
		
		
		for (int i = 0; i < xls.size(); i++) {
			// she zhi cong di jihang kaishi
			int row = i + 5;
			// ����һ��
//			 XSSFRow row = sheet.createRow(num);
			// �õ�Ҫ�����ÿһ����¼
			xlsDto = xls.get(i);
			//she zhi qi shi lie 
			int colu=0;
//			for (int colu = 0; colu < Emp.class.getDeclaredFields().length; colu++) {
				// ��һ����ѭ��
				try {
					this.writeToCell(sheet,row,colu,xlsDto.getId());
					this.writeToCell(sheet,row,colu+1,xlsDto.getName());
					this.writeToCell(sheet,row,colu+2,xlsDto.getAge());
					this.writeToCell(sheet,row,colu+3,xlsDto.getSalary());
					this.writeToCell(sheet,row,colu+4,xlsDto.getDeptid().getDname());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				// sheet.getRow(num).getCell(colu).setCellValue(xlsDto.getId());
				// XSSFCell xm = row.createCell(1);
				// xm.setCellValue(xlsDto.getName());
//			}
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

	// mei you shi xian
	// ��Excle����Ա������
	public String inxls() throws ServletException, IOException {

		Xls db = new Xls();
		db.setColumnNum(5);
		if (Xls.connectionDB()) {
			db.creatTable(db.getColumnNum());
			db.readSheet();
		} else {
			System.out.println("������˼�����Ӳ��ɹ�����ʧ���ˣ�����");
		}
		db.readOut();
		return SUCCESS;
	}

	// ����������
	private int columnNum;

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	private static Connection conn = null;
	private static Statement stmt = null;
	static String dbUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
	private final static String driver = "oracle.jdbc.driver.OracleDriver";

	private static boolean connectionDB() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, "system", "admin");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException cnfex) {
			System.err.println("�������ݿ�����ʧ�ܣ�");
			cnfex.printStackTrace();
			return false;
		} catch (SQLException sqle) {
			System.err.println("�޷��������ݿ⣡");
			sqle.printStackTrace();
			return false;
		} catch (Exception e) {
			System.err.println("����");
			return false;
		}
		return true;
	}

	public void readSheet() {
		POIFSFileSystem fs = null;
		XSSFWorkbook wb = null;
		String sql = "", sql1 = "", sql2 = "";
		try {
			fs = new POIFSFileSystem(new FileInputStream("d:\\11.xlsx"));
			wb = new XSSFWorkbook(new FileInputStream("d:\\11.xlsx"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row = null;
		XSSFCell cell = null;
		String name = "";
		int rowNum, cellNum;
		int i, j;
		// ��ȡ������
		rowNum = sheet.getLastRowNum();
		for (i = 0; i <= rowNum; i++) {
			row = sheet.getRow(i);
			cellNum = row.getLastCellNum();
			for (j = 0; j < cellNum; j++) {
				cell = row.getCell((short) j);
				// ����Ԫ�����ó�string����
				if (row.getCell(j) != null) {
					row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
				}
				name = cell.getStringCellValue();
				sql1 = sql1 + "num" + (j + 1) + ",";
				// System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+row.getCell(1).getCellType());
				// row.getCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				sql2 = sql2 + "'" + name + "',";
			}
			sql = "insert into xls ("
					+ sql1.subSequence(0, sql1.lastIndexOf(",")) + ") values ("
					+ sql2.substring(0, sql2.lastIndexOf(",")) + ")";
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("�ڲ�������ʱ��" + (i + 1) + "ʧ�ܣ�");
			}
			sql1 = "";
			sql2 = "";
		}
	}

	public void readOut() {
		connectionDB();
		String sql = "select * from xls";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				for (int i = 1; i <= columnNum; i++)
					System.out.print(rs.getString(i) + "\t");
				System.out.println();
			}
		} catch (SQLException e) {
			System.err.println("�޷���ѯ��");
			e.printStackTrace();
		}
	}

	private void writeToCell(XSSFSheet sheet, int rowNum, int columnNum,
			Object value) throws Exception {
			
		XSSFRow row = sheet.getRow(rowNum);
		if(null==row){
			row=sheet.createRow(rowNum);
		}
		XSSFCell cell = row.getCell(columnNum);
		if (cell == null) {
			cell = row.createCell(columnNum);
		}
		cell.setCellValue(convertString(value));
		
	}

	public void deleteDB() {
		connectionDB();
		String sql = "drop table xls";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("�޷�ɾ�����ݱ�");
			e.printStackTrace();
		}
	}

	public void creatTable(int columnNum) {
		int i;
		String sql = "", sql1 = "";
		for (i = 1; i <= columnNum; i++)
			sql1 = sql1 + "`" + "num" + i + "` varchar(50),";
		sql = "create table xls(`id` int(11) NOT NULL auto_increment," + sql1
				+ " PRIMARY KEY (`id`))ENGINE=MyISAM DEFAULT CHARSET=utf8";
		try {
			stmt.executeUpdate(sql);
			System.out.println(sql);
		} catch (SQLException e) {
			System.err.println("�޷��������ݱ�");
			e.printStackTrace();
		}
	}

	private static String convertString(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

}
