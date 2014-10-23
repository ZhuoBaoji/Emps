package com.baseaction;


import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import javax.swing.JOptionPane;

import org.apache.pdfbox.ExportFDF;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import com.po.Book;
import com.po.Dept;
import com.po.Emp;
import com.po.Student;
import com.util.PdfParagraph;
import com.util.StrHelp;



/**

 * ���ÿ�Դ���IText2.0.7��̬����PDF�ĵ� ת��ʱ�뱣��������Ϣ��ע��������

 *

 * @author leno

 * @version v1.0

 * @param <T>

 *            Ӧ�÷��ͣ���������һ������javabean������

 *            ע������Ϊ�˼������boolean�͵�����xxx��get����ʽΪgetXxx(),������isXxx()

 *            byte[]��ͼƬ����,ע����ʵĴ�С

 */

public class Pdf2<T> {

   public void exportPdf(Collection<T> dataset, OutputStream out) {

      exportPdf("����iText����PDF�ĵ�", null, dataset, out, "yyyy-MM-dd");

   }

 

   public void exportPdf(String[] headers, Collection<T> dataset,

         OutputStream out) {

      exportPdf("����iText����PDF�ĵ�", headers, dataset, out, "yyyy-MM-dd");

   }

 

   public void exportPdf(String[] headers, Collection<T> dataset,

         OutputStream out, String pattern) {

      exportPdf("����iText����PDF�ĵ�", headers, dataset, out, pattern);

   }

 

   /**

    * ����һ��ͨ�õķ�����������JAVA�ķ�����ƣ����Խ�������JAVA�����в��ҷ���һ��������������PDF ����ʽ�����ָ��IO�豸��

    *

    * @param title

    *            ��������

    * @param headers

    *            ���������������

    * @param dataset

    *            ��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�

    *            javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)

    * @param out

    *            ������豸�����������󣬿��Խ�PDF�ĵ������������ļ�����������

    * @param pattern

    *            �����ʱ�����ݣ��趨�����ʽ��Ĭ��Ϊ"yyy-MM-dd"

    */

   @SuppressWarnings("unchecked")

   public void exportPdf(String title, String[] headers,

         Collection<T> dataset, OutputStream out, String pattern) {

      // ��Ϊ�����PDF�ļ���һ��Ҫ�ʺϴ�ӡ���������ӡ

      Rectangle rectPageSize = new Rectangle(PageSize.A4);// ����A4ҳ���С

      // rectPageSize = rectPageSize.rotate();// ����������ʵ��A4ҳ��ĺ���

      Document document = new Document(rectPageSize, 50, 50, 50, 50);// ����4��������������ҳ���4���߾�

      try {

         // ��PDF�ĵ�д����out������IO�豸�ϵ���д����

         PdfWriter.getInstance(document, out);

         // ����ĵ�Ԫ������Ϣ

         document.addTitle(StrHelp.getChinese(title));

         document.addSubject("export information");

         document.addAuthor("leno");

         document.addCreator("leno");

         document.addKeywords("pdf itext");

         // ����ҳͷ��ҳβ

         HeaderFooter header = new HeaderFooter(new PdfParagraph(title, 20,

                true), false);

         header.setAlignment(Element.ALIGN_CENTER);

         HeaderFooter footer = new HeaderFooter(new Phrase(

                "This   is   page   "), new Phrase("."));

         footer.setAlignment(Element.ALIGN_CENTER);

         document.setHeader(header);

         document.setFooter(footer);

         // ��PDF�ĵ�

         document.open();

         // ���һ�ű��ʹ��Table����PdfPTable

         // Table table = new Table(headers.length);

         // table.setWidth(16*headers.length);

         // //table.setWidths(new float[]{20,20,20,30});

         // table.setCellsFitPage(true);

         // table.setAutoFillEmptyCells(true);

         // table.setAlignment(Table.ALIGN_CENTER);

         // table.setBackgroundColor(Color.yellow);

         // table.setBorderColor(Color.green);

         PdfPTable table = new PdfPTable(headers.length);

         // table.setHorizontalAlignment(Element.ALIGN_CENTER);

         table.setWidthPercentage(16 * headers.length);

         // ������������

         for (int i = 0; i < headers.length; i++) {

            PdfPCell cell = new PdfPCell(new PdfParagraph(headers[i], 14,

                   true));

            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);

            cell.setBackgroundColor(Color.cyan);

            cell.setBorderColor(Color.green);

            table.addCell(cell);

         }

         // �����������ݣ�����������

         Iterator<T> it = dataset.iterator();

         int index = 0;

         while (it.hasNext()) {

            index++;

            T t = (T) it.next();

            // ���÷��䣬����javabean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ

            Field[] fields = t.getClass().getDeclaredFields();

            for (short i = 0; i < fields.length; i++) {

                PdfPCell cell = null;

                Field field = fields[i];

                String fieldName = field.getName();

                String getMethodName = "get"

                      + fieldName.substring(0, 1).toUpperCase()

                      + fieldName.substring(1);

                try {

                   Class tCls = t.getClass();

                   Method getMethod = tCls.getMethod(getMethodName,

                         new Class[] {});

                   Object value  = getMethod.invoke(t, new Object[] {});

                   // �ж�ֵ�����ͺ����ǿ������ת��

                   String textValue = null;

                   if (value instanceof Boolean) {

                      boolean bValue = (Boolean) value;

                      textValue = "��";

                      if (!bValue) {

                         textValue = "Ů";

                      }

                   } else if (value instanceof Date) {

                      Date date = (Date) value;

                      SimpleDateFormat sdf = new SimpleDateFormat(pattern);

                      textValue = sdf.format(date);

                   } else if (value instanceof byte[]) {

                      byte[] bsValue = (byte[]) value;

                      Image img = Image.getInstance(bsValue);

                      cell = new PdfPCell(img);

                   } else {

                      textValue = value.toString();

                   }

                   // �������ͼƬ����,�͵����ı�����

                   if (textValue != null) {

                      cell = new PdfPCell(new PdfParagraph(textValue));

                   }

                   cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

                   cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);

                   cell.setBorderColor(Color.green);

                   table.addCell(cell);

                } catch (SecurityException e) {

                   // TODO Auto-generated catch block

                   e.printStackTrace();

                } catch (NoSuchMethodException e) {

                   // TODO Auto-generated catch block

                   e.printStackTrace();

                } catch (IllegalArgumentException e) {

                   // TODO Auto-generated catch block

                   e.printStackTrace();

                } catch (IllegalAccessException e) {

                   // TODO Auto-generated catch block

                   e.printStackTrace();

                } catch (InvocationTargetException e) {

                   // TODO Auto-generated catch block

                   e.printStackTrace();

                } catch (MalformedURLException e) {

                   // TODO Auto-generated catch block

                   e.printStackTrace();

                } catch (IOException e) {

                   // TODO Auto-generated catch block

                   e.printStackTrace();

                } finally {

                   // ������Դ

                }

            }

 

         }

         document.add(table);

         document.close();

 

      } catch (DocumentException e) {

         // TODO Auto-generated catch block

         e.printStackTrace();

      }

   }

 

   public static void main(String[] args) {

	   // ����ѧ��

	   Pdf2<Student> ex = new Pdf2<Student>();

	      String[] headers = { "id", "name", "age", "�Ա�", "��������" };

	      java.util.List<Student> dataset = new ArrayList<Student>();

	      dataset.add(new Student(10000001, "����", 20, true, new Date()));

//	      dataset.add(new Student(20000002, "����", 24, false, new Date()));
//
//	      dataset.add(new Student(30000003, "����", 22, true, new Date()));
/*
 * 	   Pdf2<Emp> ex = new Pdf2<Emp>();
	   
	   	      String[] headers = { "ѧ��", "����", "����", "�Ա�", "��������" };
	   
	   	      java.util.List<Emp> dataset = new ArrayList<Emp>();
//	   Integer id, String name, int age, Double salary
	   	      dataset.add(new Emp(10000001, "����",34, 20.3));
	   	      dataset.add(new Emp(10000002, "��4",32, 20.3));
	   	      dataset.add(new Emp(10000003, "��5",34, 20.3));
	   
 * 
 * 
 * */

	      // ����ͼ��

	      Pdf2<Book> ex2 = new Pdf2<Book>();

	      String[] headers2 = { "ͼ����", "ͼ������", "ͼ������", "ͼ��۸�", "ͼ��ISBN",

	            "ͼ�������", "����ͼƬ" };

	      java.util.List<Book> dataset2 = new ArrayList<Book>();

	      try {

	         BufferedInputStream bis = new BufferedInputStream(

	                new FileInputStream("D:\\book.jpg"));

	         byte[] buf = new byte[bis.available()];

	         while ((bis.read(buf)) != -1) {

	            //

	         }

	         dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",

	                "�廪������", buf));

	         dataset2.add(new Book(2, "java���˼��", "brucl", 300.33f, "1234567",

	                "���������", buf));

	         dataset2.add(new Book(3, "DOM����", "lenotang", 300.33f, "1234567",

	                "�廪������", buf));

	         dataset2.add(new Book(4, "c++����", "leno", 400.33f, "1234567",

	                "�廪������", buf));

	         dataset2.add(new Book(5, "c#����", "leno", 300.33f, "1234567",

	                "�����������", buf));

	 

	         OutputStream out = new FileOutputStream("D://a.pdf");

	         OutputStream out2 = new FileOutputStream("D://b.pdf");

	         ex.exportPdf(headers, dataset, out);

	         ex2.exportPdf(headers2, dataset2, out2);

	         out.close();

	         out2.close();

	         JOptionPane.showMessageDialog(null, "pdf�����ɹ�!");

	         System.out.println("pdf�����ɹ���");

	      } catch (FileNotFoundException e) {

	         // TODO Auto-generated catch block

	         e.printStackTrace();

	      } catch (IOException e) {

	         // TODO Auto-generated catch block

	         e.printStackTrace();

	      }

	   }

}