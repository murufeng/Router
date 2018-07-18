package luyou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Taskdate {
	public static void main(String[] args) throws InvalidFormatException,
			IOException, ParseException {

		File file = new File("D://D4EE0724D2B1data.xls");
		FileInputStream filein = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(filein);
		int sheetpage = workbook.getNumberOfSheets();
		lyone t1 = new lyone();
		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		List<Map> listall = new ArrayList<Map>();
		TreeSet<String> treeSet = new TreeSet<String>();// 升序排列集合
		//D2B1中顾客、路人、潜在所有数据根据天数进行的遍历结果
		/*for (int j = 0; j < sheetpage; j++) {
			Sheet sheet1 = workbook.getSheetAt(j);
			for(int i=0;i<=sheet1.getLastRowNum();i++)
			{
				Row rowi = sheet1.getRow(i);
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
		}*/
		//对顾客数据根据日期进行遍历所得的结果
		Sheet sheet1 = workbook.getSheetAt(0);
		for(int i=0;i<=sheet1.getLastRowNum();i++)
		{
			Row rowi = sheet1.getRow(i);
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
		System.out.println("set"+treeSet);
		//Set<String> mapSet = map.keySet();
		//System.out.println("mapset"+mapSet);
		//初始化每天List空集合
		for(String key :map.keySet()){
			List<Map> list = new ArrayList<>();
			map.put(key,list);
			//System.out.println(key);
		}
		//System.out.println(map);
		//将数据按天来存放到对应天的List《Map>集合
		System.out.println(listall.size());
		for (int i =0; i<listall.size();i++){
			Map mapdata =listall.get(i);
			String date = mapdata.get("date").toString();
			
			List<Map> list = map.get(date); //取出date天的集合
			list.add(mapdata);
			
			map.put(date,list);	
		}
		//System.out.println(map);
		Workbook  wb1 = new HSSFWorkbook();
		
	    for(String key : treeSet){
	    	System.out.println(key+"-"+map.get(key).size());
	    	//为每一天的顾客数据创建一页sheet
	    	Sheet sheetkey = wb1.createSheet(key+"顾客");
	    	List<Map> listdate = map.get(key);
	    	for (int i = 0;i<listdate.size();i++)
	    	{
	    		Map mapdata = listdate.get(i);
	    		Row rowi = sheetkey.createRow(i);
	    		rowi.createCell(0).setCellValue(mapdata.get("router").toString());
				rowi.createCell(1).setCellValue(mapdata.get("mac").toString());
				rowi.createCell(2).setCellValue(mapdata.get("start").toString());
				rowi.createCell(3).setCellValue(mapdata.get("end").toString());
				rowi.createCell(4).setCellValue(mapdata.get("time").toString());
				rowi.createCell(5).setCellValue(mapdata.get("date").toString());
				
				long startlong = Long.parseLong(mapdata.get("start").toString());
				//把时间戳转换成年月日小时：yyyy-MM-dd-HH
				String str = t1.getDateString(startlong, "yyyy-MM-dd-HH");
				//split 对字符串进行切割
				System.out.println(str);
				String[] strsplit = str.split("-");
				String hourTime = strsplit[3]; //获取小时时间
				rowi.createCell(6).setCellValue(hourTime);	
				
	    	}	
		}
	    FileOutputStream fileout = new FileOutputStream("D://D4EE0724D2B1date.xls");
		wb1.write(fileout);
		fileout.close();
		System.out.println("OK");
   }
}
