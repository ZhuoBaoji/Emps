package com.baseaction;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <b> ����ƥ��</b> <br>
 * <ul>
 * <li>���ߣ�C_Dream</li>
 * <li>ʱ�䣺 2012-11-08 19:22</li>
 * <li>�汾��1.0</li>
 * </ul>
 */
public class Config extends HttpServlet {
	private static final long serialVersionUID = 1L; // ���п�ʡ�ԣ�ֻ��Ϊ�˱�ʶ

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response); // �� �� post �����������Ĳ�����ת�� get���� ȥ����
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // ת��
		String forms = (String) request.getParameter("forms");

		if (forms.equals("if_3g")) {
			try {
				this.read_Excel(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		response.sendRedirect("index.jsp?done=true");
	}

	@SuppressWarnings("rawtypes")
	public void read_Excel(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			SQLException, ClassNotFoundException {
		request.setCharacterEncoding("UTF-8"); // ת��
		String xls_read_Address = (String) request
				.getParameter("xls_read_Address");// ��ȡ
		String xls_write_Address = (String) request
				.getParameter("xls_write_Address");// д��
		String count_rows = (String) request.getParameter("count_rows");// �Զ����

		try {
			DecimalFormat df = (DecimalFormat) NumberFormat
					.getPercentInstance();
			ArrayList<ArrayList> ls = new ArrayList<ArrayList>();

			FileInputStream input = new FileInputStream(new File(
					xls_read_Address)); // ��ȡ���ļ�·��
			XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(input));

			int sheet_numbers = wb.getNumberOfSheets();// ��ȡ�������
			String[] sheetnames = new String[sheet_numbers];

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			String s_3g = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@10.57.123.214:1521:gz13200", "gz13200",
					"12345");
			stmt = con.createStatement();

			for (int i = 0; i < sheet_numbers; i++) {// �������б�
				ArrayList<String[]> ls_a = new ArrayList<String[]>(); // �����洢ĳ����
																		// ��ȡ����������
				XSSFSheet sheet = wb.getSheetAt(i); // ��ȡ ĳ����
				sheetnames[i] = sheet.getSheetName();// ��ȡ��������������
				System.out.println("------>>>---���ڶ�ȡExcel�����ݣ���ǰ��"
						+ sheetnames[i]);

				for (int rows = 0; rows < sheet.getLastRowNum(); rows++) {// �ж�����
					XSSFRow row = sheet.getRow(rows);// ȡ��ĳһ�� ����
					String[] s = new String[5];// ��ʼ�����鳤��

					for (int columns = 0; columns < row.getLastCellNum(); columns++) {// ��ȡ������
						// s[columns] =
						// row.getCell(columns).getStringCellValue();
						// //ȡ��ĳ��Ԫ�����ݣ��ַ�������
						XSSFCell cell = row.getCell(columns);
						if (cell != null) {
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_STRING: // �ַ���
								s[columns] = cell.getStringCellValue();
								if (s[columns] == null) {
									s[columns] = " ";
								}
								break;
							case XSSFCell.CELL_TYPE_NUMERIC: // ����
								double strCell = cell.getNumericCellValue();
								if (String.valueOf(strCell) == null) {
									s[columns] = " ";
								}

								df.applyPattern("0");
								s[columns] = df.format(strCell);
								if (Double.parseDouble(s[columns]) != strCell) {
									df.applyPattern(Double.toString(strCell));
									s[columns] = df.format(strCell);
								}

								break;
							case XSSFCell.CELL_TYPE_BLANK: // ��ֵ
								s[columns] = " ";
								break;
							default:
								System.out.print("\n---��Ԫ���ʽ��֧��---");
								break;
							}
						}
					}
					if (count_rows.equals("��") && rows > 0) {
//						s[0] = dc.intToString(rows);// �Զ����
					}

					/*  ******** �������ݿ� �����ж��Ƿ�3G ******** */
					String sql = "select * from ap_t_si_cus_spec_info where cus_phone='"
							+ s[1] + "' and rownum=1";
					rs = stmt.executeQuery(sql);

					if (rs.next()) {
						if (rs.getString("busiattr1") != null) {
							s_3g = rs.getString("busiattr1").toString()
									.toUpperCase();
						} else {
							s_3g = " ";
						}
					} else {
						s_3g = " ";
					}
					/*  ******** ���ʽ��� ******** */

					if (s_3g.contains("3G")) {
						s[4] = "��";// д�� ���Ƿ�3G����һ�� ��ֵ������ ���ǡ�
					}
					if (s[4] == null) {
						s[4] = " ";
					}
					System.out.println("\n------>>>---�Ѵ�������---���룺" + s[1]
							+ "\t\t�Ƿ�3G��" + s[4]);
					ls_a.add(s);// ���ÿ�����ݵ� ls_a
				}
				ls.add(ls_a); // ��� ÿ���� �� ls
				System.out
						.println("\n------>>>---��ʼд��Excel�ļ��������ĵȴ�---<<<------");
				input.close();
				write_Excel(xls_write_Address, ls, sheetnames);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	public void write_Excel(String xls_write_Address, ArrayList<ArrayList> ls,
			String[] sheetnames) throws IOException {

		FileOutputStream output = new FileOutputStream(new File(
				xls_write_Address)); // ��ȡ���ļ�·��
		XSSFWorkbook wb = new XSSFWorkbook();// (new
												// BufferedInputStream(output));

		for (int sn = 0; sn < ls.size(); sn++) {
			XSSFSheet sheet = wb.createSheet(String.valueOf(sn));
			wb.setSheetName(sn, sheetnames[sn]);
			ArrayList<String[]> ls2 = ls.get(sn);
			for (int i = 0; i < ls2.size(); i++) {
				XSSFRow row = sheet.createRow(i);
				String[] s = ls2.get(i);
				for (int cols = 0; cols < s.length; cols++) {
					XSSFCell cell = row.createCell(cols);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);// �ı���ʽ
					cell.setCellValue(s[cols]);// д������
				}

			}
		}
		wb.write(output);
		output.close();
		System.out.println("-------�����д�롿-------");
	}
}