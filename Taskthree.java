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
		System.out.println("������ͳ�Ʒ�������ڽ������:");
		String read = scan.nextLine();
		while (!read.equals("exit")) {
			// ��ȡread excel ͳ�Ʋ����read���
			getdatatxt(read);

			System.out.println("������ͳ�Ʒ�������ڽ������:");
			read = scan.nextLine();
		}
		System.out.println("ͳ�ƽ���");
	}

	public static void getdatatxt(String read) throws IOException,
			InvalidFormatException, ParseException {
		// TODO Auto-generated method stub
		File file = new File("D://Workschool//" + read + ".xls");
		FileInputStream filein = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(filein);
		int sheetpage = workbook.getNumberOfSheets();
		// ͳ��ÿһ�����ʱ�̵�ʹ���������ŵ�һ���ı��ļ���
		File filetxt = new File("D://Workschool//" + read + ".txt");
		filetxt.createNewFile();
		FileOutputStream fileout = new FileOutputStream(filetxt);
		// �������ļ�
		int[] pointTime = new int[24];

		for (int j = 0; j < sheetpage; j++) {
			Sheet sheet1 = workbook.getSheetAt(j);
			String name = sheet1.getSheetName();
			System.out.println(name);
			//ȡ������0-10�⼸λ���ַ���/����
			String date = sheet1.getSheetName().substring(0, 10);
			count(date, sheet1, fileout, pointTime);
		}

		/*
		 * // ��ȡtxt�ļ� FileInputStream fileinput = new FileInputStream(filetxt);
		 * // ����һ������������reader InputStreamReader reader = new
		 * InputStreamReader(fileinput); // ���ļ�����ת�ɼ�����ܶ��������� BufferedReader br =
		 * new BufferedReader(reader); String line = ""; line = br.readLine();
		 * // һ�ζ���һ������ while (line != null) { System.out.println(line); line =
		 * br.readLine(); } /* System.out.println("��һ��"+line); line =
		 * br.readLine(); System.out.println("�ڶ���"+line);
		 */

	}

	public static void count(String date, Sheet sheet2,
			FileOutputStream fileout, int[] pointTime) throws ParseException,
			IOException {

		// ��ȡ2018-04-01 00:00:00��ʱ���
		long timelong = dateuntil.getLongByDate(date + " 00:00:00");
		// long timelong = dateuntil.getLongByDate("2018-04-02 00:00:00");

		// ͨ��4��1����һ��ÿһʱ�̵Ĺ˿�����
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
			// time��������˶������ʱ�䣬ת����Сʱ
			int timePoint = (int) time / (3600);

			// ��Ӧ���ݵ�ʱ���ͳ��
			pointTime[timePoint]++;
			// System.out.println(startstr + "||" + time + "||" + timePoint);
		}
		String str = "����" + "  ---- " + "ʱ���" + " ---- " + "ʹ��·��������" + "\r\n";
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
