package com.util;

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.InputStream;

	import org.apache.poi.ss.usermodel.Cell;
	import org.apache.poi.ss.usermodel.CellStyle;
	import org.apache.poi.ss.usermodel.Row;
	import org.apache.poi.ss.usermodel.Sheet;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.ss.usermodel.WorkbookFactory;

	/**
	 * Excel������
	 * 
	 * <pre>
	 * ����Apache��POI���
	 * </pre>
	 * 
	 * @author �·�
	 */
	public class POIExcelMakerUtil {

		private File excelFile;

		private InputStream fileInStream;

		private Workbook workBook;

		public POIExcelMakerUtil(File file) throws Exception {
			this.excelFile = file;
			this.fileInStream = new FileInputStream(this.excelFile);
			this.workBook = WorkbookFactory.create(this.fileInStream);
		}

		/**
		 * д��һ��ֵ
		 * 
		 * @param sheetNum
		 *            д���sheet�ı��
		 * @param fillRow
		 *            ��д���л���д����
		 * @param startRowNum
		 *            ��ʼ�к�
		 * @param startColumnNum
		 *            ��ʼ�к�
		 * @param contents
		 *            д�����������
		 * @throws Exception
		 */
		public void writeArrayToExcel(int sheetNum, boolean fillRow,
				int startRowNum, int startColumnNum, Object[] contents)
				throws Exception {
			Sheet sheet = this.workBook.getSheetAt(sheetNum);
			writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
		}

		/**
		 * д��һ��ֵ
		 * 
		 * @param sheetNum
		 *            д���sheet������
		 * @param fillRow
		 *            ��д���л���д����
		 * @param startRowNum
		 *            ��ʼ�к�
		 * @param startColumnNum
		 *            ��ʼ�к�
		 * @param contents
		 *            д�����������
		 * @throws Exception
		 */
		public void writeArrayToExcel(String sheetName, boolean fillRow,
				int startRowNum, int startColumnNum, Object[] contents)
				throws Exception {
			Sheet sheet = this.workBook.getSheet(sheetName);
			writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
		}

		private void writeArrayToExcel(Sheet sheet, boolean fillRow,
				int startRowNum, int startColumnNum, Object[] contents)
				throws Exception {
			for (int i = 0, length = contents.length; i < length; i++) {
				int rowNum;
				int columnNum;
				// ����Ϊ��λд��
				if (fillRow) {
					rowNum = startRowNum;
					columnNum = startColumnNum + i;
				}
				// ������Ϊ��λд��
				else {
					rowNum = startRowNum + i;
					columnNum = startColumnNum;
				}
				this.writeToCell(sheet, rowNum, columnNum,
						convertString(contents[i]));
			}
		}

		/**
		 * ��һ����Ԫ��д��ֵ
		 * 
		 * @param sheetNum
		 *            sheet�ı��
		 * @param rowNum
		 *            �к�
		 * @param columnNum
		 *            �к�
		 * @param value
		 *            д���ֵ
		 * @throws Exception
		 */
		public void writeToExcel(int sheetNum, int rowNum, int columnNum,
				Object value) throws Exception {
			Sheet sheet = this.workBook.getSheetAt(sheetNum);
			this.writeToCell(sheet, rowNum, columnNum, value);
		}

		/**
		 * ��һ����Ԫ��д��ֵ
		 * 
		 * @param sheetName
		 *            sheet������
		 * @param columnRowNum
		 *            ��Ԫ���λ��
		 * @param value
		 *            д���ֵ
		 * @throws Exception
		 */
		public void writeToExcel(String sheetName, int rowNum, int columnNum,
				Object value) throws Exception {
			Sheet sheet = this.workBook.getSheet(sheetName);
			this.writeToCell(sheet, rowNum, columnNum, value);
		}

		/**
		 * ��һ����Ԫ��д��ֵ
		 * 
		 * @param sheetNum
		 *            sheet�ı��
		 * @param columnRowNum
		 *            ��Ԫ���λ��
		 * @param value
		 *            д���ֵ
		 * @throws Exception
		 */
		public void writeToExcel(int sheetNum, String columnRowNum, Object value)
				throws Exception {
			Sheet sheet = this.workBook.getSheetAt(sheetNum);
			this.writeToCell(sheet, columnRowNum, value);
		}

		/**
		 * ��һ����Ԫ��д��ֵ
		 * 
		 * @param sheetNum
		 *            sheet������
		 * @param columnRowNum
		 *            ��Ԫ���λ��
		 * @param value
		 *            д���ֵ
		 * @throws Exception
		 */
		public void writeToExcel(String sheetName, String columnRowNum, Object value)
				throws Exception {
			Sheet sheet = this.workBook.getSheet(sheetName);
			this.writeToCell(sheet, columnRowNum, value);
		}

		private void writeToCell(Sheet sheet, String columnRowNum, Object value)
				throws Exception {
			int[] rowNumColumnNum = convertToRowNumColumnNum(columnRowNum);
			int rowNum = rowNumColumnNum[0];
			int columnNum = rowNumColumnNum[1];
			this.writeToCell(sheet, rowNum, columnNum, value);
		}

		/**
		 * ����Ԫ�������λ��ת��Ϊ�кź��к�
		 * 
		 * @param columnRowNum
		 *            ����λ��
		 * @return ����Ϊ2�����飬��1λΪ�кţ���2λΪ�к�
		 */
		private static int[] convertToRowNumColumnNum(String columnRowNum) {
			columnRowNum = columnRowNum.toUpperCase();
			char[] chars = columnRowNum.toCharArray();
			int rowNum = 0;
			int columnNum = 0;
			for (char c : chars) {
				if ((c >= 'A' && c <= 'Z')) {
					columnNum = columnNum * 26 + ((int) c - 64);
				} else {
					rowNum = rowNum * 10 + new Integer(c + "");
				}
			}
			return new int[] { rowNum - 1, columnNum - 1 };
		}

		private void writeToCell(Sheet sheet, int rowNum, int columnNum,
				Object value) throws Exception {
			Row row = sheet.getRow(rowNum);
			Cell cell = row.getCell(columnNum);
			if (cell == null) {
				cell = row.createCell(columnNum);
			}
			cell.setCellValue(convertString(value));
		}

		/**
		 * ��ȡһ����Ԫ���ֵ
		 * 
		 * @param sheetName
		 *            sheet������
		 * @param columnRowNum
		 *            ��Ԫ���λ��
		 * @return
		 * @throws Exception
		 */
		public Object readCellValue(String sheetName, String columnRowNum)
				throws Exception {
			Sheet sheet = this.workBook.getSheet(sheetName);
			int[] rowNumColumnNum = convertToRowNumColumnNum(columnRowNum);
			int rowNum = rowNumColumnNum[0];
			int columnNum = rowNumColumnNum[1];
			Row row = sheet.getRow(rowNum);
			if (row != null) {
				Cell cell = row.getCell(columnNum);
				if (cell != null) {
					return getCellValue(cell);
				}
			}
			return null;
		}

		/**
		 * ��ȡ��Ԫ���е�ֵ
		 * 
		 * @param cell ��Ԫ��
		 * @return
		 */
		private static Object getCellValue(Cell cell) {
			int type = cell.getCellType();
			switch (type) {
			case Cell.CELL_TYPE_STRING:
				return (Object) cell.getStringCellValue();
			case Cell.CELL_TYPE_NUMERIC:
				Double value = cell.getNumericCellValue();
				return (Object) (value.intValue());
			case Cell.CELL_TYPE_BOOLEAN:
				return (Object) cell.getBooleanCellValue();
			case Cell.CELL_TYPE_FORMULA:
				return (Object) cell.getArrayFormulaRange().formatAsString();
			case Cell.CELL_TYPE_BLANK:
				return (Object) "";
			default:
				return null;
			}
		}

		/**
		 * ����һ�в���������һ����ͬ�ĸ�ʽ
		 * 
		 * @param sheetNum
		 *            sheet�ı��
		 * @param rowNum
		 *            �����е�λ��
		 * @throws Exception
		 */
		public void insertRowWithFormat(int sheetNum, int rowNum) throws Exception {
			Sheet sheet = this.workBook.getSheetAt(sheetNum);
			insertRowWithFormat(sheet, rowNum);
		}

		/**
		 * ����һ�в���������һ����ͬ�ĸ�ʽ
		 * 
		 * @param sheetName
		 *            sheet������
		 * @param rowNum
		 *            �����е�λ��
		 * @throws Exception
		 */
		public void insertRowWithFormat(String sheetName, int rowNum)
				throws Exception {
			Sheet sheet = this.workBook.getSheet(sheetName);
			insertRowWithFormat(sheet, rowNum);
		}

		private void insertRowWithFormat(Sheet sheet, int rowNum) throws Exception {
			sheet.shiftRows(rowNum, rowNum + 1, 1);
			Row newRow = sheet.createRow(rowNum);
			Row oldRow = sheet.getRow(rowNum - 1);
			for (int i = oldRow.getFirstCellNum(); i < oldRow.getLastCellNum(); i++) {
				Cell oldCell = oldRow.getCell(i);
				if (oldCell != null) {
					CellStyle cellStyle = oldCell.getCellStyle();
					newRow.createCell(i).setCellStyle(cellStyle);
				}
			}
		}

		/**
		 * ������һ��sheet
		 * 
		 * @param sheetNum
		 *            sheet�ı��
		 * @param newName
		 *            �µ�����
		 */
		public void renameSheet(int sheetNum, String newName) {
			this.workBook.setSheetName(sheetNum, newName);
		}

		/**
		 * ������һ��sheet
		 * 
		 * @param oldName
		 *            �ɵ�����
		 * @param newName
		 *            �µ�����
		 */
		public void renameSheet(String oldName, String newName) {
			int sheetNum = this.workBook.getSheetIndex(oldName);
			this.renameSheet(sheetNum, newName);
		}

		/**
		 * ɾ��һ��sheet
		 * 
		 * @param sheetName
		 *            sheet������
		 */
		public void removeSheet(String sheetName) {
			this.workBook.removeSheetAt(this.workBook.getSheetIndex(sheetName));
		}

		/**
		 * д��Excel�ļ����ر�
		 */
		public void writeAndClose() {
			if (this.workBook != null) {
				try {
					FileOutputStream fileOutStream = new FileOutputStream(
							this.excelFile);
					this.workBook.write(fileOutStream);
					if (fileOutStream != null) {
						fileOutStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (this.fileInStream != null) {
				try {
					this.fileInStream.close();
				} catch (Exception e) {
				}
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
