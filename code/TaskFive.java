package luyou;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TaskFive {
	public static lyone t1 = new lyone();
	private static Dateuntil dateuntil = new Dateuntil();
	private static Scanner scan;

	// 控制台输入参数
	public static void main(String[] args) throws InvalidFormatException,
			IOException, ParseException {
		/*
		 * Scanner scan = new Scanner(System.in); System.out.println("请输入:");
		 * String read = scan.nextLine(); //System.out.println("输入参数是："+read);
		 * //当输入不为空时就可以输入多行参数 /*while (scan != null) {
		 * System.out.println("请输入:"); String read = scan.nextLine();
		 * System.out.println("输入参数是："+read); } //equals 判断字符串是否相等
		 * //当输入over时输入结束 while (!read.equals("over")) {
		 * System.out.println("输入参数是："+read); System.out.println("请输入:"); read =
		 * scan.nextLine(); } System.out.println("输入结束");
		 */

		scan = new Scanner(System.in);
		System.out.println("请输入日期和MAc:");
		String read = scan.nextLine();
		// System.out.println("输入参数是："+read);
		// 当输入不为空时就可以输入多行参数
		/*
		 * while (scan != null) { System.out.println("请输入:"); String read =
		 * scan.nextLine(); System.out.println("输入参数是："+read); }
		 */
		// equals 判断字符串是否相等
		// 当输入over时输入结束
		while (!read.equals("exit")) {
			System.out.println("输入参数是：" + read);
			String[] str = read.split(" ");
			if(str.length == 2){     //
				String date = str[0];
				String mac = str[1];
				System.out.println("date:" + date);
				System.out.println("mac:" + mac);

				String mag = getresult(date, mac);
				System.out.println("查询结果:" + mag);
			}else{
				System.out.println("输入参数格式有误");
			}
			
			System.out.println("请输入和MAc:");
			read = scan.nextLine();
		}
		System.out.println("输入结束");

	}

	public static String getresult(String date, String mac) throws InvalidFormatException, IOException, ParseException {
		File file = new File("D://Workschool//D4EE0724D2B1data.xls");
		FileInputStream filein = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(filein);
		Sheet sheet = workbook.getSheetAt(0);
		
		long datelong = 01;
		long endlong = 01;
		try {
			datelong = dateuntil.getLongByDate(date + " 00:00:00");
			endlong = dateuntil.getLongByDate(date + " 23:59:59");
		} catch (Exception e) {
			return "输入日期格式有误";

		}
		List<String> listbefor = new ArrayList<>(); // 获取date日期之前的所有顾客mac
		List<String> listDate = new ArrayList<>(); // 获取date当日的所有顾客mac

		Map<String, Integer> map1 = new HashMap<>();
		Map<String, Integer> map2 = new HashMap<>();

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			Row rowi = sheet.getRow(i);

			String maci = t1.getCellValue(rowi.getCell(1));
			String start = t1.getCellValue(rowi.getCell(2));

			long startlong = Long.parseLong(start);

			if (startlong < datelong) 
			{
				listbefor.add(maci);
				//这一天之前用户使用路由器的次数
				if (map1.containsKey(maci)) {
					map1.put(maci, map1.get(maci) + 1);}
				else {
					map1.put(maci, 1);
				}
			} else if (startlong >= datelong && startlong <= endlong) {
				listDate.add(maci);
				//这一天用户使用路由器的次数
				if (map2.containsKey(maci)) {
					map2.put(maci, map2.get(maci) + 1);}
				else {
					map2.put(maci, 1);
				}
			}
		}
		/*if (map.containsKey(maci)) {
			map.put(maci, map.get(maci) + 1);
		} else {
			map.put(maci, 1);}
		}*/
			/*if (map.containsKey(mac)) {
				int num = map.get(mac);
				System.out.println("此"+mac+"从日期开始到"+date+"使用过路由器的次数为: "+num);	//按使用次数统计
			} else {
				System.out.println("此顾客至今没有使用过该路由器");
			}*/
			// 查询顾客在某一天是否使用该路由器，若他在这一天使用过该路由器再判断他在这一天以前是否使用过该路由器
		if (listDate.contains(mac)) {
			if (listbefor.contains(mac)) {
					int num = map1.get(mac)+map2.get(mac);
					System.out.println("该顾客之前以及今天使用路由器的次数为:" + num);
				return "此" + mac + "是老顾客" + "即:在今天之前以及在今天使用过路由器";
			} else {
					int num = map2.get(mac);
					System.out.println("今天该顾客使用路由器的次数为:" + num);
				return "此" + mac + "是新顾客" + "并且在今天是第一次使用该路由器";
			}
		} else if (listbefor.contains(mac)) {
				int num = map1.get(mac);
				System.out.println("该顾客之前使用路由器的次数为:" + num);
			return "今天该顾客没有使用路由器,但在"+date+"之前" + mac + "这位顾客使用过该路由器";
		} else {
			return "在日期之前以及这一天均没有出现该顾客";
				// 2018-04-03 D02598795A11 6
			   //2018-04-03 CCB8A8260D00
			}
		}
	}
