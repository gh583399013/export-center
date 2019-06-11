package com.ft.start;

import com.alibaba.dubbo.container.Main;

public class ExportCenterStart {

    public static void main(String[] args) {
        Main.main(args);
    }

//    public static void main(String[] args) throws IOException {
//
//        System.out.println("###############1");
//
//        //原文：https://blog.csdn.net/ko0491/article/details/85331029
//        // com.alibaba.dubbo.container.Main.main(args);
//        //
//        // ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//        // new String[]{ "META-INF/spring/service-provider.xml" });
//        /**
//         * 使用spring文件
//         */
//        /*
//         * ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( new String[]{
//         * "META-INF/spring/spring.xml" }); context.start();
//         */
////        com.alibaba.dubbo.container.Main.main(args);
////        System.out.println("provider启动了");
//
//
//          ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( new String[]{"META-INF/spring/applicationContext-dubbo.xml" });
//          context.start();
////                IExportService demoService = (IExportService)context.getBean("exportService"); // 获取远程服务代理
////        demoService.asd();
//
//        System.out.println("@@@@@@3");
//        IExportService demoService = (IExportService)context.getBean("exportService"); // 获取远程服务代理
//        demoService.asd();
//        System.out.println( "end" ); //
//    }

}