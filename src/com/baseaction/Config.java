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
 * <b> 数据匹配</b> <br>
 * <ul>
 * <li>作者：C_Dream</li>
 * <li>时间： 2012-11-08 19:22</li>
 * <li>版本：1.0</li>
 * </ul>
 */
public class Config extends HttpServlet {
	private static final long serialVersionUID = 1L; // 此行可省略，只是为了标识

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response); // 将 表单 post 方法传过来的参数，转给 get方法 去处理
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 转码
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
		request.setCharacterEncoding("UTF-8"); // 转码
		String xls_read_Address = (String) request
				.getParameter("xls_read_Address");// 读取
		String xls_write_Address = (String) request
				.getParameter("xls_write_Address");// 写入
		String count_rows = (String) request.getParameter("count_rows");// 自动编号

		try {
			DecimalFormat df = (DecimalFormat) NumberFormat
					.getPercentInstance();
			ArrayList<ArrayList> ls = new ArrayList<ArrayList>();

			FileInputStream input = new FileInputStream(new File(
					xls_read_Address)); // 读取的文件路径
			XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(input));

			int sheet_numbers = wb.getNumberOfSheets();// 获取表的总数
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

			for (int i = 0; i < sheet_numbers; i++) {// 遍历所有表
				ArrayList<String[]> ls_a = new ArrayList<String[]>(); // 用来存储某个表
																		// 读取出来的数据
				XSSFSheet sheet = wb.getSheetAt(i); // 获取 某个表
				sheetnames[i] = sheet.getSheetName();// 获取表名，存入数组
				System.out.println("------>>>---正在读取Excel表数据，当前表："
						+ sheetnames[i]);

				for (int rows = 0; rows < sheet.getLastRowNum(); rows++) {// 有多少行
					XSSFRow row = sheet.getRow(rows);// 取得某一行 对象
					String[] s = new String[5];// 初始化数组长度

					for (int columns = 0; columns < row.getLastCellNum(); columns++) {// 读取所有列
						// s[columns] =
						// row.getCell(columns).getStringCellValue();
						// //取得某单元格内容，字符串类型
						XSSFCell cell = row.getCell(columns);
						if (cell != null) {
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_STRING: // 字符串
								s[columns] = cell.getStringCellValue();
								if (s[columns] == null) {
									s[columns] = " ";
								}
								break;
							case XSSFCell.CELL_TYPE_NUMERIC: // 数字
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
							case XSSFCell.CELL_TYPE_BLANK: // 空值
								s[columns] = " ";
								break;
							default:
								System.out.print("\n---单元格格式不支持---");
								break;
							}
						}
					}
					if (count_rows.equals("是") && rows > 0) {
//						s[0] = dc.intToString(rows);// 自动编号
					}

					/*  ******** 访问数据库 ，并判断是否3G ******** */
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
					/*  ******** 访问结束 ******** */

					if (s_3g.contains("3G")) {
						s[4] = "是";// 写入 “是否3G”这一列 的值，比如 “是”
					}
					if (s[4] == null) {
						s[4] = " ";
					}
					System.out.println("\n------>>>---已处理数据---号码：" + s[1]
							+ "\t\t是否3G：" + s[4]);
					ls_a.add(s);// 添加每行数据到 ls_a
				}
				ls.add(ls_a); // 添加 每个表 到 ls
				System.out
						.println("\n------>>>---开始写入Excel文件，请耐心等待---<<<------");
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
				xls_write_Address)); // 读取的文件路径
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
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 文本格式
					cell.setCellValue(s[cols]);// 写入内容
				}

			}
		}
		wb.write(output);
		output.close();
		System.out.println("-------【完成写入】-------");
	}
}