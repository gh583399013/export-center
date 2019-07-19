package com.ft.export.exception;

import com.ft.export.enums.ExceptionTypeEnum;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author mask
 * @date 2019/6/12 20:13
 * @desc
 */
@Data
public class ExportException extends RuntimeException{
    public static final Integer FAIL = -1;
    private Integer code;
    private String msg;

    public ExportException(ExceptionTypeEnum exceptionTypeEnum) {
        super(exceptionTypeEnum.getErrorMsg());
        this.code = exceptionTypeEnum.getCode();
        this.msg = exceptionTypeEnum.getErrorMsg();
    }

    public ExportException(String msg) {
        super(msg);
        this.code = FAIL;
        this.msg = msg;
    }

    public static ExportException getExportException(String className, String methodName) {
        String msg = className + "服务异常,调用" + methodName + "失败";
        return new ExportException(msg);
    }

    public static ExportException getExportException(Class clazz, Method method) {
        return getExportException(clazz.getSimpleName(), method.getName());
    }

    public static String func2(){
        return null;
    }

    public static void fuc(){
        try {
            func2().substring(10);
        } catch (Exception e) {
            //e.printStackTrace();
            throw ExportException.getExportException("asd","asd");
        }
    }

    public static void main(String[] args) {

        try {
            fuc();
        } catch (ExportException e1) {
            System.out.println("@@@@@@@@@@1");
        }
        catch (NullPointerException e2) {
            System.out.println("@@@@@@@@@@2");
        }
        catch (Exception e) {
            System.out.println("@@@@@@@@@@@@@3");
        }


//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    fuc();
//                } catch (ExportException e1) {
//                    System.out.println("@@@@@@@@@@1");
//                }
//                catch (NullPointerException e2) {
//                    System.out.println("@@@@@@@@@@2");
//                }
//                catch (Exception e) {
//                    System.out.println("@@@@@@@@@@@@@3");
//                }
//            }
//        });
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
