package luyou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Rongqi {
	public static void main(String[] arg) {

		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("router", "D4EE0724D2B1");
		map1.put("mac", "88835D13F979");
		map1.put("start", "1522526534");
		map1.put("end", "1522526834");
		// Map中的Key是唯一的
		// map.put("router", "kkk");

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("router", "a");
		map2.put("mac", "b");
		map2.put("start", "c");
		map2.put("end", "d");

		/*
		 * System.out.println(map);
		 * 
		 * //获取map中的数据 String router = map.get("router");
		 * System.out.println(router);
		 */

		// List 有序集合
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(map1);
		list.add(map2);
		System.out.println(list);
		/*
		 * System.out.println(list.get(0)); int size = list.size();
		 * System.out.println(size); for(int i=0;i<size;i++){
		 * System.out.println(list.get(i));
		 */

		// Set 无序集合

		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");

		set.add("b"); // 重复会去重
		System.out.println("set:" + set);

		// List 有序集合
		List<String> list1 = new ArrayList<>();
		list1.add("a");
		list1.add("b");
		list1.add("c");

		list1.add("b"); // 重复会去重
		System.out.println("list1:" + list1);

	}
}
