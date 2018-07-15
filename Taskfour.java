package luyou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Taskfour {
	public static lyone t1 = new lyone();

	public static void main(String[] args) throws InvalidFormatException,
			IOException, ParseException {
		Scanner scan = new Scanner(System.in);
		System.out.println("������ͳ���û��������ݽ���ļ�:");
		String read = scan.nextLine();
		while (!read.equals("exit")) {
			// ��ȡread excel ͳ�Ʋ����read���
			classuser(read);

			System.out.println("������ͳ���û��������ݽ���ļ�:");
			read = scan.nextLine();
		}
		System.out.println("ͳ�ƽ���");
	}
    // ͳ��ÿ���ͻ�ʹ�ø�·�����Ĵ���������ʹ�ô����Ķ��ٻ���Ϊ��ͬ��Ⱥ�塣
	
	public static void classuser(String read) throws IOException,
			InvalidFormatException {
		// TODO Auto-generated method stub
		File file = new File("D://Workschool//" + read + ".xls");
		FileInputStream filein = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(filein);
		Sheet sheet1 = workbook.getSheetAt(0);
		
		//ȡ�����д�ǰ������10λ�Ժ���ַ���
		String name = sheet1.getSheetName().substring(10);

		Map<String, Integer> map = new HashMap<>();

		// ͳ��mac���ֵĴ��� ��ͳ�ƿͻ��˻����û�ʹ�ø�·������ʱ�䣨��һ����һʱ�̣�
		for (int i = 0; i <= sheet1.getLastRowNum(); i++) {
			Row rowi = sheet1.getRow(i);

			String mac = t1.getCellValue(rowi.getCell(1));

			// containsKey �ж�map����Key�Ƿ������mac
			if (map.containsKey(mac)) {
				map.put(mac, map.get(mac) + 1);
			} else {
				map.put(mac, 1);
			}
		}
		for (String key : map.keySet()) {
			System.out.println(key + "-" + map.get(key));
		}
		// �¿ͻ���ֻ����һ�� �Ͽͻ������ֶ��
		Map<String, Integer> mapNew = new HashMap<>();
		Map<String, Integer> mapOld = new HashMap<>();

		for (String key : map.keySet()) {
			int num = map.get(key);
			if (num == 1) {
				mapNew.put(key, num);
			} else {
				mapOld.put(key, num);
			}
			// System.out.println(key + "-" + map.get(key));
		}
		Workbook wb1 = new HSSFWorkbook();
		Sheet sheet2 = wb1.createSheet("��"+name);
		int i = 0;
		for (String key : mapNew.keySet()) {
			Row row = sheet2.createRow(i);
			row.createCell(0).setCellValue(key);
			row.createCell(1).setCellValue(map.get(key));
			i++;
		}
		Sheet sheet3 = wb1.createSheet("��"+name);
		i = 0;
		for (String key : mapOld.keySet()) {
			Row row = sheet3.createRow(i);
			row.createCell(0).setCellValue(key);
			row.createCell(1).setCellValue(map.get(key));
			i++;
		}
		FileOutputStream fileout = new FileOutputStream("D://Workschool//" +name+ "Old and New.xls");
		wb1.write(fileout);
		fileout.close();
		System.out.println("OK");
	}
}
