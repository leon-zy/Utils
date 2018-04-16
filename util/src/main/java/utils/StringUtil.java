package main.java.utils;

/**
 * StringUtil
 * Created by leon_yan on 2018/4/16
 */
public class StringUtil {

    public static String replaceBlank(String str, String replaceStr) {
        return isBlank(str) ? replaceStr : str;
    }

    public static boolean isBlank(String value) {
        return null == value || value.length() == 0 || "".equals(value) || "".equals(value.trim());
    }
}