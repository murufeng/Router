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
		System.out.println("请输入统计用户日期数据结果文件:");
		String read = scan.nextLine();
		while (!read.equals("exit")) {
			// 读取read excel 统计并输出read结果
			classuser(read);

			System.out.println("请输入统计用户日期数据结果文件:");
			read = scan.nextLine();
		}
		System.out.println("统计结束");
	}
    // 统计每个客户使用该路由器的次数并根据使用次数的多少划分为不同的群体。
	
	public static void classuser(String read) throws IOException,
			InvalidFormatException {
		// TODO Auto-generated method stub
		File file = new File("D://Workschool//" + read + ".xls");
		FileInputStream filein = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(filein);
		Sheet sheet1 = workbook.getSheetAt(0);
		
		//取出表中从前往后数10位以后的字符串
		String name = sheet1.getSheetName().substring(10);

		Map<String, Integer> map = new HashMap<>();

		// 统计mac出现的次数 即统计客户端或者用户使用该路由器的时间（哪一天那一时刻）
		for (int i = 0; i <= sheet1.getLastRowNum(); i++) {
			Row rowi = sheet1.getRow(i);

			String mac = t1.getCellValue(rowi.getCell(1));

			// containsKey 判断map集合Key是否包含此mac
			if (map.containsKey(mac)) {
				map.put(mac, map.get(mac) + 1);
			} else {
				map.put(mac, 1);
			}
		}
		for (String key : map.keySet()) {
			System.out.println(key + "-" + map.get(key));
		}
		// 新客户：只出现一次 老客户：出现多次
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
		Sheet sheet2 = wb1.createSheet("新"+name);
		int i = 0;
		for (String key : mapNew.keySet()) {
			Row row = sheet2.createRow(i);
			row.createCell(0).setCellValue(key);
			row.createCell(1).setCellValue(map.get(key));
			i++;
		}
		Sheet sheet3 = wb1.createSheet("老"+name);
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
