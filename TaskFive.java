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

	// ����̨�������
	public static void main(String[] args) throws InvalidFormatException,
			IOException, ParseException {
		/*
		 * Scanner scan = new Scanner(System.in); System.out.println("������:");
		 * String read = scan.nextLine(); //System.out.println("��������ǣ�"+read);
		 * //�����벻Ϊ��ʱ�Ϳ���������в��� /*while (scan != null) {
		 * System.out.println("������:"); String read = scan.nextLine();
		 * System.out.println("��������ǣ�"+read); } //equals �ж��ַ����Ƿ����
		 * //������overʱ������� while (!read.equals("over")) {
		 * System.out.println("��������ǣ�"+read); System.out.println("������:"); read =
		 * scan.nextLine(); } System.out.println("�������");
		 */

		scan = new Scanner(System.in);
		System.out.println("���������ں�MAc:");
		String read = scan.nextLine();
		// System.out.println("��������ǣ�"+read);
		// �����벻Ϊ��ʱ�Ϳ���������в���
		/*
		 * while (scan != null) { System.out.println("������:"); String read =
		 * scan.nextLine(); System.out.println("��������ǣ�"+read); }
		 */
		// equals �ж��ַ����Ƿ����
		// ������overʱ�������
		while (!read.equals("exit")) {
			System.out.println("��������ǣ�" + read);
			String[] str = read.split(" ");
			if(str.length == 2){     //
				String date = str[0];
				String mac = str[1];
				System.out.println("date:" + date);
				System.out.println("mac:" + mac);

				String mag = getresult(date, mac);
				System.out.println("��ѯ���:" + mag);
			}else{
				System.out.println("���������ʽ����");
			}
			
			System.out.println("�������MAc:");
			read = scan.nextLine();
		}
		System.out.println("�������");

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
			return "�������ڸ�ʽ����";

		}
		List<String> listbefor = new ArrayList<>(); // ��ȡdate����֮ǰ�����й˿�mac
		List<String> listDate = new ArrayList<>(); // ��ȡdate���յ����й˿�mac

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
				//��һ��֮ǰ�û�ʹ��·�����Ĵ���
				if (map1.containsKey(maci)) {
					map1.put(maci, map1.get(maci) + 1);}
				else {
					map1.put(maci, 1);
				}
			} else if (startlong >= datelong && startlong <= endlong) {
				listDate.add(maci);
				//��һ���û�ʹ��·�����Ĵ���
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
				System.out.println("��"+mac+"�����ڿ�ʼ��"+date+"ʹ�ù�·�����Ĵ���Ϊ: "+num);	//��ʹ�ô���ͳ��
			} else {
				System.out.println("�˹˿�����û��ʹ�ù���·����");
			}*/
			// ��ѯ�˿���ĳһ���Ƿ�ʹ�ø�·��������������һ��ʹ�ù���·�������ж�������һ����ǰ�Ƿ�ʹ�ù���·����
		if (listDate.contains(mac)) {
			if (listbefor.contains(mac)) {
					int num = map1.get(mac)+map2.get(mac);
					System.out.println("�ù˿�֮ǰ�Լ�����ʹ��·�����Ĵ���Ϊ:" + num);
				return "��" + mac + "���Ϲ˿�" + "��:�ڽ���֮ǰ�Լ��ڽ���ʹ�ù�·����";
			} else {
					int num = map2.get(mac);
					System.out.println("����ù˿�ʹ��·�����Ĵ���Ϊ:" + num);
				return "��" + mac + "���¹˿�" + "�����ڽ����ǵ�һ��ʹ�ø�·����";
			}
		} else if (listbefor.contains(mac)) {
				int num = map1.get(mac);
				System.out.println("�ù˿�֮ǰʹ��·�����Ĵ���Ϊ:" + num);
			return "����ù˿�û��ʹ��·����,����"+date+"֮ǰ" + mac + "��λ�˿�ʹ�ù���·����";
		} else {
			return "������֮ǰ�Լ���һ���û�г��ָù˿�";
				// 2018-04-03 D02598795A11 6
			   //2018-04-03 CCB8A8260D00
			}
		}
	}
