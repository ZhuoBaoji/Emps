package com.util;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsUtil {

	public static String getChinese(String s) {

		try {
			return new String(s.getBytes("gb2312"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}
	// xie dao X hang X lie
	public static void writeToCell(XSSFSheet sheet, int rowNum, int columnNum,
			Object value) throws Exception {
		XSSFRow row = sheet.getRow(rowNum);
		if (null == row) {
			row = sheet.createRow(rowNum);
		}
		XSSFCell cell = row.getCell(columnNum);
		if (cell == null) {
			cell = row.createCell(columnNum);
		}
		cell.setCellValue(convertString(value));

	}

	// zhuanghuan
	private static String convertString(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}
//    
	public static void insertImg(XSSFWorkbook hwb,String img) {
		FileOutputStream fileOut = null;
		BufferedImage bufferImg = null;
		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();
		try {
			bufferImg = ImageIO.read(new File(img));
			ImageIO.write(bufferImg, "jpg", byteArrayOut);
			XSSFSheet sheet = hwb.getSheet("Sheet1");
			XSSFDrawing patriarch = sheet.createDrawingPatriarch();
			XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 512, 255,
					(short) 0, 0, (short) 2,2);
			patriarch.createPicture(anchor, hwb.addPicture(byteArrayOut
					.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));

		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("io erorr :  " + io.getMessage());
		} finally {
			if (fileOut != null) {

				try {
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
