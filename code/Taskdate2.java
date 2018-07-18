package luyou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Taskdate2 {
	/*
	 * 
	 */
	static lyone t1 = new lyone();

	public static void main(String[] args) throws InvalidFormatException,
			IOException, ParseException {

		Scanner scan = new Scanner(System.in);
		System.out.println("请输入统计数据结果文件:");
		String read = scan.nextLine();
		while (!read.equals("exit")) {
			// 读取read excel 统计并输出read结果
			getclassdata(read);

			System.out.println("请输入统计数据结果文件:");
			read = scan.nextLine();
		}
		System.out.println("统计结束");
	}

	public static void getclassdata(String read) throws InvalidFormatException,
			IOException {
		// TODO Auto-generated method stub
		File file = new File("D://Workschool//" + read + ".xls");
		FileInputStream filein = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(filein);
		int sheetpage = workbook.getNumberOfSheets();

		// 将所得的数据表根据时间划分，分别获得顾客、路人、潜在顾客的数据表
		// 即每一时间后面加上相应的信息 获得顾客、路人、潜在顾客在这几天时间里的具体使用情况
		// 并将它们分别写入到不同的文件中去
		for (int j = 0; j < sheetpage; j++) {

			Sheet sheet1 = workbook.getSheetAt(j);
			String name = sheet1.getSheetName();

			Map m = sheft(sheet1);

			classdata((Map<String, List<Map>>) m.get("map"),
					(TreeSet<String>) m.get("set"), name, "date" + name
							+ ".xls");

		}

		// System.out.println(map);
	}

	public static Map sheft(Sheet sheet) {
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		List<Map> listall = new ArrayList<Map>();
		TreeSet<String> treeSet = new TreeSet<String>();// 升序排列集合

		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row rowi = sheet.getRow(i);
			String date = t1.getCellValue(rowi.getCell(5));
			map.put(date, null);
			treeSet.add(date);

			String router = t1.getCellValue(rowi.getCell(0));
			String mac = t1.getCellValue(rowi.getCell(1));
			String start = t1.getCellValue(rowi.getCell(2));
			String end = t1.getCellValue(rowi.getCell(3));
			String time = t1.getCellValue(rowi.getCell(4));

			Map mapdata = new HashMap<>();
			mapdata.put("mac", mac);
			mapdata.put("router", router);
			mapdata.put("start", start);
			mapdata.put("end", end);
			mapdata.put("time", time);
			mapdata.put("date", date);
			listall.add(mapdata);
		}
		System.out.println("set" + treeSet);
		// Set<String> mapSet = map.keySet();
		// System.out.println("mapset"+mapSet);
		// 初始化每天List空集合
		for (String key : map.keySet()) {
			List<Map> list = new ArrayList<>();
			map.put(key, list);

			// System.out.println(key);
		}
		// System.out.println(map);
		// 将数据按天来存放到对应天的List《Map>集合
		System.out.println(listall.size());
		for (int i = 0; i < listall.size(); i++) {
			Map mapdata = listall.get(i);
			String date = mapdata.get("date").toString();

			List<Map> list = map.get(date); // 取出date天的集合
			list.add(mapdata);
			map.put(date, list);
		}

		Map result = new HashMap();
		result.put("map", map);
		result.put("set", treeSet);
		return result;
	}

	public static void classdata(Map<String, List<Map>> map,
			TreeSet<String> treeSet, String type, String filename)
			throws IOException {
		Workbook wb1 = new HSSFWorkbook();
		for (String key : treeSet) {
			System.out.println(key + "-" + map.get(key).size());
			// 为每一天的顾客数据创建一页sheet
			Sheet sheetkey = wb1.createSheet(key + type);
			List<Map> listdate = map.get(key);
			for (int i = 0; i < listdate.size(); i++) {
				Map mapdata = listdate.get(i);
				Row rowi = sheetkey.createRow(i);
				rowi.createCell(0).setCellValue(
						mapdata.get("router").toString());
				rowi.createCell(1).setCellValue(mapdata.get("mac").toString());
				rowi.createCell(2)
						.setCellValue(mapdata.get("start").toString());
				rowi.createCell(3).setCellValue(mapdata.get("end").toString());
				rowi.createCell(4).setCellValue(mapdata.get("time").toString());
				rowi.createCell(5).setCellValue(mapdata.get("date").toString());

				long startlong = Long
						.parseLong(mapdata.get("start").toString());
				// 把时间戳转换成年月日小时：yyyy-MM-dd-HH
				String str = t1.getDateString(startlong, "yyyy-MM-dd-HH");
				// split 对字符串进行切割
				System.out.println(str);
				String[] strsplit = str.split("-");
				String hourTime = strsplit[3]; // 获取小时时间
				rowi.createCell(6).setCellValue(hourTime);

			}
		}
		FileOutputStream fileout = new FileOutputStream("d://Workschool//" + filename);
		wb1.write(fileout);
		fileout.close();
		System.out.println("OK");
	}

}
