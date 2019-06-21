package com.ft.export.fanxing;

/**
 * @author mask
 * @date 2019/6/15 14:18
 * @desc
 */
public class FanxingAdd {
    public static <T> T add(T t1, T t2){
        Object obj = null;

        if(t1 instanceof Integer && t2 instanceof Integer ){
            System.out.println("第一次加");
            obj = Integer.valueOf(String.valueOf(t1)) + Integer.valueOf(String.valueOf(t2));
            System.out.println("第一次打印结果A" + obj);
        }
        if(int.class.equals(t1.getClass()) && int.class.equals(t1.getClass())){
            System.out.println("第一次加");
            obj = Integer.valueOf(String.valueOf(t1)) + Integer.valueOf(String.valueOf(t2));
            System.out.println("第一次打印结果B" + obj);
        }
        if(Integer.class.equals(t1.getClass()) && Integer.class.equals(t1.getClass())){
            System.out.println("第一次加");
            obj = Integer.valueOf(String.valueOf(t1)) + Integer.valueOf(String.valueOf(t2));
            System.out.println("第一次打印结果C" + obj);
        }

        if(t1 instanceof Double && t2 instanceof Double ){
            System.out.println("第一次加");
            obj = Double.valueOf(String.valueOf(t1)) + Double.valueOf(String.valueOf(t2));
            System.out.println("第一次打印结果A" + obj);
        }
        if(double.class.equals(t1.getClass()) && double.class.equals(t1.getClass())){
            System.out.println("第一次加");
            obj = Double.valueOf(String.valueOf(t1)) + Double.valueOf(String.valueOf(t2));
            System.out.println("第一次打印结果B" + obj);
        }
        if(Double.class.equals(t1.getClass()) && Double.class.equals(t1.getClass())){
            System.out.println("第一次加");
            obj = Double.valueOf(String.valueOf(t1)) + Double.valueOf(String.valueOf(t2));
            System.out.println("第一次打印结果C" + obj);
        }

        return (T) obj;
    }

    public static void main(String[] args) {

        int a = 1;
        int b = 2;

        Integer c = Integer.valueOf(3);
        Integer d = Integer.valueOf(3);


        System.out.println(FanxingAdd.add(a,b));

        System.out.println(FanxingAdd.add(c,d));
    }
}
