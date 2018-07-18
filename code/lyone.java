package luyou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class lyone {
	public static void main(String[] args) throws InvalidFormatException,
			IOException {

		Scanner scan = new Scanner(System.in);
		System.out.println("������·����mac:");
		String read = scan.nextLine();
		while (!read.equals("exit")) {
			// ��ȡread excel ͳ�Ʋ����read���
			getdata(read);

			System.out.println("������·����mac:");
			read = scan.nextLine();
		}
		System.out.println("ͳ�ƽ���");
	}

	public static void getdata(String read) throws InvalidFormatException,
			IOException {
		// TODO Auto-generated method stub
		File file = new File("D://Workschool//" + read + ".xlsx");
		FileInputStream filein = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(filein);

		Sheet sheet = workbook.getSheetAt(0);
		// ��ȡ�������
		int sheetLength = sheet.getLastRowNum();
		// ��ȡ��һ��λ��
		int sheetfirt = sheet.getFirstRowNum();
		// ��ȡsheet������
		String name = sheet.getSheetName();
		int passby = 0;
		int loss = 0;
		int into = 0;

		List<Map> passbylist = new ArrayList<Map>();
		List<Map> losslist = new ArrayList<Map>();
		List<Map> intolist = new ArrayList<Map>();

		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			Row rowi = sheet.getRow(i);
			Cell router = rowi.getCell(5);
			String a5 = getCellValue(router);
			Cell mac = rowi.getCell(6);
			String a6 = getCellValue(mac);
			Cell timestart = rowi.getCell(8);
			String a8 = getCellValue(timestart);
			Cell timeend = rowi.getCell(9);
			String a9 = getCellValue(timeend);

			long time = Long.parseLong(a9) - Long.parseLong(a8);

			Map map = new HashMap<>();
			map.put("router", a5);
			map.put("mac", a6);
			map.put("start", a8);
			map.put("end", a9);
			map.put("time", time);

			String type = null;
			if (time <= 6 * 60) {
				type = "·��";
				passby++;
				passbylist.add(map);
			} else if (time > 6 * 60 && time < 10 * 60) {
				type = "Ǳ��";
				loss++;
				losslist.add(map);
			} else {
				type = "�˿�";
				into++;
				intolist.add(map);
			}
		}
		// ��·�ˡ��˿͡�Ǳ���û������ݷ���һ��Map������
		Map<String, List<Map>> maplist = new HashMap<>();
		maplist.put("Ǳ��", losslist);
		maplist.put("·��", passbylist);
		maplist.put("�˿�", intolist);

		// �����µĹ�����
		Workbook wb1 = new HSSFWorkbook();
		// ����Map����
		for (String key : maplist.keySet()) {
			System.out.println(key + "-" + maplist.get(key).size());
			Sheet sheet1 = wb1.createSheet(key);
			List<Map> listDate = maplist.get(key);

			for (int i = 0; i < listDate.size(); i++) {
				Map map = listDate.get(i);
				Row rowi = sheet1.createRow(i);
				rowi.createCell(0).setCellValue(map.get("router").toString());
				rowi.createCell(1).setCellValue(map.get("mac").toString());
				rowi.createCell(2).setCellValue(map.get("start").toString());
				rowi.createCell(3).setCellValue(map.get("end").toString());
				rowi.createCell(4).setCellValue(map.get("time").toString());
				long startlong = Long.parseLong(map.get("start").toString());
				rowi.createCell(5).setCellValue(
						getDateString(startlong, "yyyy-MM-dd"));
			}

		}

		FileOutputStream fileout = new FileOutputStream("D://Workschool//" + read
				+ "data.xls");
		wb1.write(fileout);
		fileout.close();
		System.out.println("OK");

	}

	public static String getCellValue(Cell cell) {
		String var = null;
		if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			var = cell.getStringCellValue();
			/* System.out.println("1---"+a8); */
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			DecimalFormat df = new DecimalFormat("0");
			var = df.format(cell.getNumericCellValue());
			/* System.out.println("2---"+a8); */
		}
		return var;
	}

	public static String getDateString(long time, String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		String d = df.format(time * 1000);
		return d;

	}

}
