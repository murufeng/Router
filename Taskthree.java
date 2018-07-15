package luyou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Taskthree {
	private static lyone t2 = new lyone();
	private static Dateuntil dateuntil = new Dateuntil();

	public static void main(String[] args) throws InvalidFormatException,
			IOException, ParseException {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入统计分类后日期结果数据:");
		String read = scan.nextLine();
		while (!read.equals("exit")) {
			// 读取read excel 统计并输出read结果
			getdatatxt(read);

			System.out.println("请输入统计分类后日期结果数据:");
			read = scan.nextLine();
		}
		System.out.println("统计结束");
	}

	public static void getdatatxt(String read) throws IOException,
			InvalidFormatException, ParseException {
		// TODO Auto-generated method stub
		File file = new File("D://Workschool//" + read + ".xls");
		FileInputStream filein = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(filein);
		int sheetpage = workbook.getNumberOfSheets();
		// 统计每一天具体时刻的使用情况并存放到一个文本文件中
		File filetxt = new File("D://Workschool//" + read + ".txt");
		filetxt.createNewFile();
		FileOutputStream fileout = new FileOutputStream(filetxt);
		// 创建新文件
		int[] pointTime = new int[24];

		for (int j = 0; j < sheetpage; j++) {
			Sheet sheet1 = workbook.getSheetAt(j);
			String name = sheet1.getSheetName();
			System.out.println(name);
			//取出表中0-10这几位的字符串/名称
			String date = sheet1.getSheetName().substring(0, 10);
			count(date, sheet1, fileout, pointTime);
		}

		/*
		 * // 读取txt文件 FileInputStream fileinput = new FileInputStream(filetxt);
		 * // 建立一个输入流对象reader InputStreamReader reader = new
		 * InputStreamReader(fileinput); // 把文件内容转成计算机能读懂的语言 BufferedReader br =
		 * new BufferedReader(reader); String line = ""; line = br.readLine();
		 * // 一次读入一行数据 while (line != null) { System.out.println(line); line =
		 * br.readLine(); } /* System.out.println("第一行"+line); line =
		 * br.readLine(); System.out.println("第二行"+line);
		 */

	}

	public static void count(String date, Sheet sheet2,
			FileOutputStream fileout, int[] pointTime) throws ParseException,
			IOException {

		// 获取2018-04-01 00:00:00的时间戳
		long timelong = dateuntil.getLongByDate(date + " 00:00:00");
		// long timelong = dateuntil.getLongByDate("2018-04-02 00:00:00");

		// 通缉4月1日这一天每一时刻的顾客人数
		// int []pointTime1 = new int[24];

		for (int i = 0; i <= sheet2.getLastRowNum(); i++) {
			Row rowi = sheet2.getRow(i);
			String start = t2.getCellValue(rowi.getCell(2));

			// map.put(start, null);
			// treeSet.add(start);
			long startlong = Long.parseLong(start);
			long time = startlong - timelong;

			String startstr = t2
					.getDateString(startlong, "yyyy-MM-dd HH:mm:ss");
			// time即这天过了多少秒的时间，转换成小时
			int timePoint = (int) time / (3600);

			// 对应数据的时间点统计
			pointTime[timePoint]++;
			// System.out.println(startstr + "||" + time + "||" + timePoint);
		}
		String str = "日期" + "  ---- " + "时间点" + " ---- " + "使用路由器人数" + "\r\n";
		byte[] b = str.getBytes();
		fileout.write(b);
		for (int i = 0; i < pointTime.length; i++) {
			String str1 = date + ":" + i + "   ----    " + pointTime[i]
					+ "\r\n";
			// String str2 = i +"-"+pointTime1[i]+"\r\n";
			byte[] b1 = str1.getBytes();
			fileout.write(b1);
		}
		// return pointTime;
	}
	/*
	 * public static void point(FileOutputStream fileout,int[] pointTime) throws
	 * IOException { for (int i = 0; i < pointTime.length; i++) { String str1 =
	 * timelong + i + "-" + pointTime[i] + "\r\n"; // String str2 = i
	 * +"-"+pointTime1[i]+"\r\n"; byte[] b = str1.getBytes(); fileout.write(b);
	 * } }
	 */

}
