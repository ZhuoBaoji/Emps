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
	 * 指定每个单元格数据
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

	// 导出Excle表格
	public void xls() throws ServletException, IOException, DocumentException {

		response.setContentType("text/html;charset=utf-8");

		// 把数据库表导出为Excel文件；
		List<Emp> xls = empService.findAllEmp();
		System.out.println(xls.size());
		// int CountColumnNum = xls.size();

		// 创建Excel文档
		FileInputStream fis = new FileInputStream(new File("D:\\11.xlsx"));
		XSSFWorkbook hwb = new XSSFWorkbook(fis);
		Emp xlsDto = null;
		// sheet 对应一个工作页
		XSSFSheet sheet = hwb.getSheet(("Sheet1"));
		System.out.println(sheet); // 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
//		 hwb.setSheetName(1, "tomcat");
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
		
		
		
		
		
		
		
		for (int i = 0; i < xls.size(); i++) {
			// she zhi cong di jihang kaishi
			int row = i + 5;
			// 创建一行
//			 XSSFRow row = sheet.createRow(num);
			// 得到要插入的每一条记录
			xlsDto = xls.get(i);
			//she zhi qi shi lie 
			int colu=0;
//			for (int colu = 0; colu < Emp.class.getDeclaredFields().length; colu++) {
				// 在一行内循环
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

	// mei you shi xian
	// 从Excle导入员工数据
	public String inxls() throws ServletException, IOException {

		Xls db = new Xls();
		db.setColumnNum(5);
		if (Xls.connectionDB()) {
			db.creatTable(db.getColumnNum());
			db.readSheet();
		} else {
			System.out.println("不好意思，连接不成功！你失败了！！！");
		}
		db.readOut();
		return SUCCESS;
	}

	// 定义总列数
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
			System.err.println("加载数据库驱动失败！");
			cnfex.printStackTrace();
			return false;
		} catch (SQLException sqle) {
			System.err.println("无法连接数据库！");
			sqle.printStackTrace();
			return false;
		} catch (Exception e) {
			System.err.println("错误");
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
		// 获取总行数
		rowNum = sheet.getLastRowNum();
		for (i = 0; i <= rowNum; i++) {
			row = sheet.getRow(i);
			cellNum = row.getLastCellNum();
			for (j = 0; j < cellNum; j++) {
				cell = row.getCell((short) j);
				// 将单元格设置成string类型
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
				System.err.println("在插入数据时第" + (i + 1) + "失败！");
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
			System.err.println("无法查询！");
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
			System.err.println("无法删除数据表！");
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
			System.err.println("无法创建数据表！");
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
