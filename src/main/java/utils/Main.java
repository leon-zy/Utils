package main.java.utils;

import java.util.Map;
import java.util.Set;

public class Main {

	/**
	 * 打印 变量名称 和 变量值
	 * @param args
	 */
	public static void main(String[] args) {
		String str="abc";
		Map<String, String> result = De.print(str);//str=abc
		int num=789;
		Map<String, String> result1 = De.print(num);//num=789
		String[]strArray={"qqq","www","eee","rrr","ttt"};
		Map<String, String> result2 = De.print(strArray);//strArray=[qqq, www, eee, rrr, ttt]
		int[]intArray={111,222,333};
		Map<String, String> result3 = De.print(intArray);//intArray=[111, 222, 333]
		Set set = result.keySet();
	}

}
