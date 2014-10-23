package com.baseaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.po.Emp;
import com.service.impl.EmpServiceImpl;
import com.util.BaseAction;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.ServletException;
import java.awt.Color;
import com.lowagie.text.Chapter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.ListItem;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Section;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfWriter;

public class CopyOfXls extends BaseAction {
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
		XSSFSheet sheet = hwb.getSheet(("sheet1"));
		System.out.println(sheet); // ���ñ��Ĭ���п��Ϊ15���ֽ�
		sheet.setDefaultColumnWidth((short) 15);
		// hwb.setSheetName(1, "tomcat");
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
			int num = i + 5;
			// ����һ��
			XSSFRow row = sheet.createRow(num);
			// �õ�Ҫ�����ÿһ����¼
			xlsDto = xls.get(i);
			for (int colu = 0; colu < Emp.class.getDeclaredFields().length; colu++) {
				// ��һ����ѭ��
				// sheet.getRow(i).getCell(colu).setCellValue("lllllllllllllllllll");//de
				// dao di wu hang
				XSSFCell xh = row.createCell(0);
				xh.setCellValue(xlsDto.getId());

				XSSFCell xm = row.createCell(1);
				xm.setCellValue(xlsDto.getName());

				XSSFCell yxsmc = row.createCell(2);
				yxsmc.setCellValue(xlsDto.getAge());

				XSSFCell kcm = row.createCell(3);
				kcm.setCellValue(xlsDto.getSalary());

				XSSFCell kcmc = row.createCell(4);
				kcmc.setCellValue(xlsDto.getDeptid().getDname());

				sheet.getRow(3).getCell(1).setCellValue("tomc");
				System.out.println();
			}
		}

		// �����ļ��������׼��������ӱ��
		response.setCharacterEncoding("utf-8");
		// // response.setContentType("application/vnd.ms-excel;charset=utf-8");
		// FileOutputStream out = new FileOutputStream(new
		// File("D:\\EmpInfo.xlsx"));
		//xlsx--------
//		response.setContentType("octets/stream");
//		response.addHeader("Content-Disposition",
//				"attachment;filename=EmpInfo.xlsx");
//		OutputStream out = response.getOutputStream();
//		hwb.write(out);
	
		
		//pdf===========
		   Rectangle rectPageSize = new Rectangle(PageSize.A4);// ����A4ҳ���С
//	        if (true == flag) {
//	            rectPageSize = rectPageSize.rotate();// ����������ʵ��A4ҳ��ĺ���
//	        }
	        // step a: creation of a document-object
	        Document    document = new Document(rectPageSize, 0,0, 0, 0);// 4��������������ҳ���4���߾�
	        try {
	            response.setContentType("application/pdf");
	            response.addHeader("Content-Disposition",
	    				"attachment;filename=EmpInfo.pdf");
	            // PdfWriter.getInstance(document, response.getOutputStream());
	            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
	            OutputStream out =response.getOutputStream();
	            writer.setPageEvent((PdfPageEvent) this);
	            // HeaderFooter footer = getHeaderFooter();
	            // document.setFooter(footer);
	            document.open();
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		
		
		
//		out.close();
		System.out.println("���ݿ⵼���ɹ�");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// mei you shi xian
	// ��Excle����Ա������
	public String inxls() throws ServletException, IOException {

		CopyOfXls db = new CopyOfXls();
		db.setColumnNum(5);
		if (CopyOfXls.connectionDB()) {
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

}
