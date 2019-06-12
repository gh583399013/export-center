package com.ft.export.apesct;

import java.lang.reflect.Field;

/**
 * @author mask
 * @date 2019/6/12 17:53
 * @desc
 */
public class Student extends Father{
    private String name;
    private Integer age;
;
    public static void main(String[] args) {
        Student student = new Student();
        Field[] fields =Student.class.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        System.out.println("#########");

        Field[] fields1 =Student.class.getDeclaredFields();
        for (Field field : fields1) {
            System.out.println(field.getName());
        }
        System.out.println("#########");
    }
}
