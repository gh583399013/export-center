package com.ft.export.temp;

import com.ft.business.resp.MyOrderPageResp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author mask
 * @date 2019/6/12 20:22
 * @desc
 */
public class Main {

    public static void func1(){
        Son son = new Son();
        List<Field> fieldList = new ArrayList<>();
        Field[] sonField = son.getClass().getDeclaredFields();
        for (Field field : sonField) {
            System.out.println(field.getName());
        }
        System.out.println("#################");
        Field[] fatherField = son.getClass().getSuperclass().getDeclaredFields();

        for (Field field : fatherField) {
            System.out.println(field.getName());
        }
    }

    public static void func2(){
        Father father = new Father();
        Field[] fatherField = father.getClass().getSuperclass().getDeclaredFields();

        System.out.println(father.getClass().getSuperclass().getName());
        for (Field field : fatherField) {
            System.out.println(field.getName());
        }
        System.out.println(father.getClass().getSuperclass().equals(Object.class));

        if(fatherField == null){
            System.out.println("!!!!!!!!!!!!!");
        }
        if(fatherField.length == 0){
            System.out.println("222222222222");
        }
    }

    public static void func3(){
        Class clazz = Son.class;
        Map<String, String> fieldMap = new HashMap<>();
        //可能继承了
        while (!Object.class.equals(clazz)){
            Field[] parentFields = clazz.getDeclaredFields();
            for (Field field : parentFields) {
                fieldMap.put(clazz.getName() + "#" + field.getName(), field.getName());
            }
            clazz = clazz.getSuperclass();
        }

        for (String s : fieldMap.keySet()) {
            System.out.println("key " + s + " value " + fieldMap.get(s));
        }
    }

    public static <T>void func41(List<T> dataList,List<String> fieldList){
        Map<String, Method> methodMap = new HashMap<>();
        Method[] methods = dataList.get(0).getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().startsWith("get")){
                System.out.println(method.getName());
                methodMap.put(method.getName(), method);
            }
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (T t : dataList) {

            for (Method method : methodMap.values()) {
                Object object = null;
                try {
                    object = method.invoke(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                System.out.println(object);
            }
        }
    }

    public static void func4(List<MyOrderPageResp> dataList,List<String> fieldList){
        Map<String, Method> methodMap = new HashMap<>();
        Method[] methods = dataList.get(0).getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().startsWith("get")){
                System.out.println(method.getName());
                methodMap.put(method.getName(), method);
            }
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (MyOrderPageResp t : dataList) {

            System.out.println("############");

            for (Method method : methodMap.values()) {
                Object object = null;
                try {
                    object = method.invoke(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                System.out.println(object);
            }
        }
    }

    public static void func6(List<MyOrderPageResp> dataList,List<String> fieldList){
        Map<String, Method> methodMap = new HashMap<>();

        MyOrderPageResp myOrderPageResp = new MyOrderPageResp();
        myOrderPageResp.setOrderNo("SO19921226");
        myOrderPageResp.setOrderTime(new Date());
        myOrderPageResp.setOrderStatus(2);
        myOrderPageResp.setTotalPrice(new BigDecimal(100.22));
        myOrderPageResp.setDoublePride(399.12);

        Method[] methods = dataList.get(0).getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().startsWith("getOrderNo")){
                System.out.println(method.getName());
                methodMap.put(method.getName(), method);
            }
        }
    }

    public static  void func5(){
        MyOrderPageResp myOrderPageResp = new MyOrderPageResp();
        myOrderPageResp.setOrderNo("SO19921226");
        myOrderPageResp.setOrderTime(new Date());
        myOrderPageResp.setOrderStatus(2);
        myOrderPageResp.setTotalPrice(new BigDecimal(100.22));
        myOrderPageResp.setDoublePride(399.12);

        func41(Arrays.asList(myOrderPageResp), null);
    }

    public static void main(String[] args) {
        func5();
    }
}
