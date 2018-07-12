package main.java.utils;

import java.lang.reflect.Field;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Reflections
 * Created by leon_yan on 2018/4/16
 */
public class Reflections {
    /**
     * 获取class所有属性和父类属性
     * @param c 校验的类
     * @return boolean
     */
    public static Field[] getAllFields(Class<?> c) {
        Field[] fs = c.getDeclaredFields();
        if(c.getSuperclass() != Object.class) {
            fs = (Field[])((Field[])ArrayUtils.addAll(fs, getAllFields(c.getSuperclass())));
        }
        return fs;
    }
}