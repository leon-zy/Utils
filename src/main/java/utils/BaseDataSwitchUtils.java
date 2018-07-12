package main.java.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * BaseDataSwitchUtils 数据库查询赋值
 * Created by leon_yan on 2018/5/30
 */
public final class BaseDataSwitchUtils {

    //下划线
    private static final String UNDERLINE_STR = "_";

    /**
     * 查询数据库的值转换到对应类的对应属性名上
     * @param resultMaps 查询结果
     * @param clazz 赋值的类
     * @param <T> 泛型
     * @return list
     * @throws IllegalAccessException 异常
     * @throws InstantiationException 异常
     */
    public static <T> List<T> getDataResult(List<Map<String, Object>> resultMaps, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        List<T> results = new ArrayList<>();
        if(null != clazz){
            for(Map<String, Object> resultMap : resultMaps){
                T obj = clazz.newInstance();
                getDataResult(resultMap, obj);
                results.add(obj);
            }
        }
        return results;
    }

    /**
     * 转换属性值到对应类的对应属性名上
     * @param resultMaps 属性值
     * @param obj 类
     * @throws IllegalAccessException 异常
     */
    private static void getDataResult(Map<String, Object> resultMaps, Object obj) throws IllegalAccessException {
        if(null != obj && null != resultMaps && !resultMaps.isEmpty()){
            Object val;
            for (Field field : Reflections.getAllFields(obj.getClass())) {
                val = resultMaps.get(underscoreName(field.getName()));
                if(!isBlankOrNull(val)){
                    field.setAccessible(true);
                    field.set(obj, replaceObjType(field.getGenericType().toString(), val));
                }
            }
        }
    }

    /**
     * java驼峰字段转成数据库下划线字段
     * @param name 字段名称
     * @return str
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if(StringUtil.isNotBlank(name)){
            //将第一个字符处理成大写
            result.append(name.substring(0,1).toUpperCase());
            //循环处理其余字符
            for(int i =1;i < name.length(); i++) {
                String s = name.substring(i, i +1);
                //在大写字母前添加下划线
                if(s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                //其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return  result.toString();
    }

    /**
     * 数据库字段转成java驼峰式
     * @param name 字段名称
     * @return str
     */
    public static String convertToHump(String name) {
        StringBuffer buffer = new StringBuffer();
        if(StringUtil.isNotBlank(name)){
            if(name.contains(UNDERLINE_STR)){
                String[] words = name.toLowerCase().split(UNDERLINE_STR);
                for(int i = 0; i < words.length; i++){
                    String word = words[i];
                    String firstLetter = word.substring(0, 1);
                    String others = word.substring(1);
                    String upperLetter = i != 0 ? firstLetter.toUpperCase() : firstLetter;
                    buffer.append(upperLetter).append(others);
                }
            }else{
                buffer.append(name.toLowerCase());
            }
        }
        return buffer.toString();
    }

    /**
     * 判断对象是否为空
     * @param obj 对象
     * @return boolean
     */
    public static Boolean isBlankOrNull(Object obj){
        return null == obj || "".equals(obj);
    }

    /**
     * 获取属性类型
     * @param type 属性类型
     * @param objVal 属性值
     * @return Object
     */
    public static Object replaceObjType(String type, Object objVal){
        Object val = null;
        switch (type) {
            case "class java.lang.String":
                val = isBlankOrNull(objVal) ? null : String.valueOf(objVal);
                break;
            case "class java.lang.Long":
                val = isBlankOrNull(objVal) ? null : Long.valueOf(String.valueOf(objVal));
                break;
            case "class java.lang.Integer":
                val = isBlankOrNull(objVal) ? null : Integer.valueOf(String.valueOf(objVal));
                break;
            case "class java.lang.Short":
                val = isBlankOrNull(objVal) ? null : Short.valueOf(String.valueOf(objVal));
                break;
            case "class java.lang.Double":
                val = isBlankOrNull(objVal) ? null : Double.valueOf(String.valueOf(objVal));
                break;
            case "class java.lang.Boolean":
                val = isBlankOrNull(objVal) ? null : Boolean.valueOf(String.valueOf(objVal));
                break;
            case "class java.util.Date":
                val = isBlankOrNull(objVal) ? null : DateUtil.stringToDate(String.valueOf(objVal), "yyyy-MM-dd HH:mm:ss");
                break;
            case "class java.math.BigDecimal":
                val = isBlankOrNull(objVal) ? null : new BigDecimal(String.valueOf(objVal));
                break;
        }
        return val;
    }
}